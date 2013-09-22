/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.AlarmDAO;
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.util.AlarmMessage;
import cn.ccrise.spimp.util.ErcsDeferredResult;

import com.google.common.collect.Queues;

/**
 * Alarm Service。
 * 
 */
@Service
public class AlarmService extends HibernateDataServiceImpl<Alarm, Long> {
	/**
	 * 等待新报警的队列
	 */
	public final Vector<ErcsDeferredResult<AlarmMessage>> waitAlarmQueue = new Vector<ErcsDeferredResult<AlarmMessage>>();

	/**
	 * 等待关闭的队列
	 */
	public final Queue<ErcsDeferredResult<AlarmMessage>> waitCloseQueue = Queues.newConcurrentLinkedQueue();

	/**
	 * 客户端已读取报警事件的记录
	 */
	private HashMap<String, ArrayList<Long>> readRecord = new HashMap<String, ArrayList<Long>>();

	@Autowired
	private AlarmDAO alarmDAO;

	@Autowired
	private AlarmReadRecordService alarmReadRecordService;

	@Override
	public HibernateDAO<Alarm, Long> getDAO() {
		return alarmDAO;
	}

	/**
	 * 通知客户端，事故已经处理
	 * 
	 * @param getterInstance
	 */
	public void notifyAlarmProcessed(Long alarmId) {
		ErcsDeferredResult<AlarmMessage> deferredResult = null;
		AlarmMessage message = new AlarmMessage();
		ArrayList<Long> closeId = new ArrayList<Long>();
		closeId.add(alarmId);
		message.setAlarmList(closeId);
		synchronized (waitCloseQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitCloseQueue.iterator();
			while (it.hasNext()) {
				deferredResult = it.next();
				Long timePassed = deferredResult.getTimePassed();
				try {

					if (timePassed > 1000 * 60 * 1) {
						deferredResult.setErrorResult("be disconnected ...");
					} else {
						deferredResult.setResult(message);
						closeAlarm(alarmId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					it.remove();
				}
			}
		}
	}

	/**
	 * 报警
	 * 
	 * @param httpServletRequest
	 */
	public void putAlarm(HttpServletRequest request) {
		// 1:记录到数据库先
		Long currentTime = System.currentTimeMillis();
		Alarm alarm = new Alarm();
		alarm.setDealFlag(Alarm.DEAL_FLAG_UNDEALED);
		alarm.setAlarmTime(new Timestamp(currentTime));
		save(alarm);
		// 2:处理请求
		notifyAlarmWaiters(request);
	}

	/**
	 * 把请求的已读序列暂存到缓存中
	 * 
	 * @param sessionId
	 * @param ids
	 */
	public void setRequestRecord(String sessionId, Long ids[]) {
		ArrayList<Long> arrayList = null;
		synchronized (readRecord) {
			if (!readRecord.keySet().contains(sessionId)) {
				arrayList = new ArrayList<Long>();
				readRecord.put(sessionId, arrayList);
			}
			arrayList = readRecord.get(sessionId);
		}
		arrayList.clear();
		if (ids != null && ids.length > 0) {
			for (Long id : ids) {
				arrayList.add(id);
			}
		}
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public boolean updateAlarm(Alarm entity) {
		update(entity);
		notifyAlarmProcessed(entity.getId());
		return true;
	}

	/**
	 * 等待接警
	 * 
	 * @param getterInstance
	 */
	public void waitAlarm(ErcsDeferredResult<AlarmMessage> getterInstance, HttpServletRequest request,
			AlarmMessage message, Long ids[]) {
		final String sessionId = request.getSession().getId();
		getterInstance.setSessionId(sessionId);
		reasePreRequest(sessionId);
		synchronized (waitAlarmQueue) {
			waitAlarmQueue.add(getterInstance);
		}
		setRequestRecord(request.getSession().getId(), ids);
		getterInstance.onTimeout(new Runnable() {
			@Override
			public void run() {
				synchronized (readRecord) {
					readRecord.remove(sessionId);
				}
			}
		});
		notifyAlarmWaiters(request);
	}

	/**
	 * 等待关闭
	 * 
	 * @param request
	 */
	public void waitCloseAlarm(HttpServletRequest request, ErcsDeferredResult<AlarmMessage> deferredResult) {
		synchronized (waitAlarmQueue) {
			waitCloseQueue.add(deferredResult);
		}
	}

	private void closeAlarm(Long alarmId) {
		findUniqueBy("id", alarmId).setDealFlag(Alarm.DEAL_FLAG_DEALED);
		getDAO().getSession().createQuery("delete from AlarmReadRecord raw where raw.alarmId=" + alarmId)
				.executeUpdate();
	}

	private List<Alarm> getNoRecordAlarmList(String sessionId) {
		final int maxAlarmValiteTime = 1;// 天
		ArrayList<Long> alarmIdOfRequest = new ArrayList<Long>();
		synchronized (readRecord) {
			if (readRecord.containsKey(sessionId)) {
				alarmIdOfRequest = readRecord.get(sessionId);
			}
		}
		StringBuffer buff = new StringBuffer();
		Date currentDate = new Date(System.currentTimeMillis());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(currentDate);
		rightNow.add(Calendar.DAY_OF_YEAR, -maxAlarmValiteTime);
		if (alarmIdOfRequest.size() == 0) {
			/**
			 * 请求中附带的事件id为空 表明可能是第一次进入 或者浏览器刷新过
			 */
			/**
			 * 因此要把没有处理过的再重新加载一次，所以不能使用数据库的参考
			 */
			buff.append("  from Alarm alarm where alarm.dealFlag=" + Alarm.DEAL_FLAG_UNDEALED);
			buff.append("  and alarm.alarmTime>:maxTime");

		} else {
			buff.append("  from Alarm alarm where not exists  (select record.id from AlarmReadRecord record ");
			buff.append(" where alarm.id=record.alarmId ");
			buff.append(" and record.sessionId='" + sessionId);
			buff.append("') and alarm.alarmTime>:maxTime and alarm.dealFlag=");
			buff.append(Alarm.DEAL_FLAG_UNDEALED);
		}
		Query query = alarmReadRecordService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("maxTime", rightNow.getTime());
		List<?> currentList = query.list();
		List<Alarm> returnList = new ArrayList<Alarm>();
		if (currentList != null && currentList.size() > 0) {
			Iterator<?> it = currentList.iterator();
			while (it.hasNext()) {
				returnList.add((Alarm) it.next());
			}
		}
		logger.debug("{}", returnList);
		return returnList;
	}

	/**
	 * 通知所有的客户端进行接警处理:弹出对话框
	 */
	private void notifyAlarmWaiters(HttpServletRequest request) {
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();
				logger.debug(new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(waiter.getRecordTime()));
				String sessionId = waiter.getSessionId();
				// 最新事件列表
				List<Alarm> newAlarmList = getNoRecordAlarmList(sessionId);
				List<Long> newAlarmIdList = new ArrayList<Long>();
				if (newAlarmList.size() == 0) {
					logger.debug("{}", waiter.getTimePassed());
					if (waiter.getTimePassed() > 200000) {
						waiter.setErrorResult("TimePassed:" + waiter.getTimePassed());
						it.remove();
					}
					continue;
				}
				ArrayList<Long> newAlarmIdCopy = new ArrayList<Long>();
				Iterator<Alarm> idIt = newAlarmList.iterator();
				while (idIt.hasNext()) {
					Alarm tAlarm = idIt.next();
					newAlarmIdList.add(tAlarm.getId());
					newAlarmIdCopy.add(tAlarm.getId());
				}
				if (newAlarmIdCopy.size() > 0) {
					AlarmMessage message = new AlarmMessage();
					message.setAlarmList(newAlarmIdCopy);
					waiter.setResult(message);
					it.remove();
					alarmReadRecordService.addReadRecord(sessionId, newAlarmIdCopy.toArray(new Long[0]));
				}

			}
		}
	}

	private void reasePreRequest(String sessionId) {
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();
				if (waiter.getSessionId().equals(sessionId)) {
					waiter.setErrorResult("refreshed....");
					logger.debug("{}", "刷新了一个");
					it.remove();
				}
			}
		}
	}
}