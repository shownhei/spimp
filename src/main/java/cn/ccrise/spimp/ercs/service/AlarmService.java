/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.AlarmDAO;
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanTemplate;
import cn.ccrise.spimp.util.AlarmMessage;
import cn.ccrise.spimp.util.AlarmWaiterTimeOutHandler;
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
	private HashMap<String, HashSet<Long>> readRecord = new HashMap<String, HashSet<Long>>();

	@Autowired
	private AlarmDAO alarmDAO;

	@Autowired
	private AlarmReadRecordService alarmReadRecordService;
	/**
	 * 任务模板
	 */
	@Autowired
	private EmergencyPlanTemplateService emergencyPlanTemplateService;
	/**
	 * 任务实例
	 */
	@Autowired
	private EmergencyPlanInstanceService emergencyPlanInstanceService;
	private HashMap<String, ArrayList<String>> requestMap = new HashMap<String, ArrayList<String>>();

	/**
	 * 删除一条报警记录 会级联删除 该报警记录对应的救援措施
	 * 
	 * @param alarmId
	 * @return
	 */
	public boolean deleteAlarm(Long alarmId) {
		Alarm alarm = findUniqueBy("id", alarmId);
		List<EmergencyPlanInstance> planList = emergencyPlanInstanceService.findBy("alarm", alarm);
		Iterator<EmergencyPlanInstance> it = planList.iterator();
		while (it.hasNext()) {
			emergencyPlanInstanceService.delete(it.next());
		}
		delete(alarmId);
		return true;
	}

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

					if (timePassed > ErcsDeferredResult.TIME_LIMIT) {
						deferredResult.setErrorResult("{'messge':'be disconnected ...'}");
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
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();
				waiter.setErrorResult("{'messge':'reconnect'}");
				it.remove();
			}
		}
		refreshResult();
	}

	private void refreshResult() {
		synchronized (waitAlarmQueue) {
			synchronized (waitAlarmQueue) {
				Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
				ErcsDeferredResult<AlarmMessage> waiter = null;
				while (it.hasNext()) {
					waiter = it.next();
					logger.debug(waiter.getRequestKey());
					waiter.setErrorResult("{'message':'refreshed'}");
					it.remove();
				}
			}
		}

		// synchronized (requestMap) {
		// Iterator<String> it = requestMap.keySet().iterator();
		// String sessionId = null;
		// ArrayList<String> sessionKeyList = null;
		// while (it.hasNext()) {
		// sessionId = it.next();
		// sessionKeyList = requestMap.get(sessionId);
		// for (int i = 0; i < sessionKeyList.size(); i++) {
		// notifyAlarmWaiters(sessionId, sessionKeyList.get(i));
		// }
		// }
		// }
	}

	/**
	 * 把请求的已读序列暂存到缓存中
	 * 
	 * @param sessionId
	 * @param ids
	 */
	public void setRequestRecord(String requestKey, Long ids[]) {
		HashSet<Long> arrayList = null;
		synchronized (readRecord) {
			this.logger.debug("设置读取记录");
			if (!readRecord.keySet().contains(requestKey)) {
				this.logger.debug("首次设置记录");
				arrayList = new HashSet<Long>();
				readRecord.put(requestKey, arrayList);
			}
			arrayList = readRecord.get(requestKey);
			arrayList.clear();
		}
		if (ids != null && ids.length > 0) {
			for (Long id : ids) {
				arrayList.add(id);
			}
			this.logger.debug("{}", arrayList, "设置的读取记录");
		}
	}

	/**
	 * 更新
	 * 
	 * @param entity
	 * @return
	 */
	public boolean updateAlarm(Alarm entity) {
		if (entity.getDealFlag() == 0) {// 未处理
			// 发短信
			// 根据应急救援模板，创建救援任务实例
			List<EmergencyPlanTemplate> templateList = emergencyPlanTemplateService.find(
					Restrictions.eq("emergencyCategory", entity.getAccidentType()),
					Restrictions.eq("emergencyLevel", entity.getAccidentLevel()));
			if (templateList != null && templateList.size() > 0) {
				EmergencyPlanTemplate template = null;
				EmergencyPlanInstance instance = null;
				Iterator<EmergencyPlanTemplate> it = templateList.iterator();
				while (it.hasNext()) {
					template = it.next();
					instance = new EmergencyPlanInstance();
					instance.setAlarm(entity);
					instance.setEmergencyCategory(template.getEmergencyCategory());
					instance.setEmergencyLevel(template.getEmergencyLevel());
					instance.setTeam(template.getTeam());
					instance.setTaskContent(template.getTaskContent());

					emergencyPlanInstanceService.save(instance);// 保存实例
				}
			}
			// 报警状态,修改为已经处理
			entity.setDealFlag(Alarm.DEAL_FLAG_DEALED);
		}
		update(entity);
		notifyAlarmProcessed(entity.getId());
		refreshResult();
		return true;
	}

	/**
	 * 等待接警
	 * 
	 * @param getterInstance
	 */
	public void waitAlarm(ErcsDeferredResult<AlarmMessage> getterInstance, HttpServletRequest request,
			AlarmMessage message, Long ids[]) {
		String requestKey = getRequestKey(request);
		final String sessionId = request.getSession().getId();
		synchronized (requestMap) {
			if (!requestMap.containsKey(sessionId)) {
				requestMap.put(sessionId, new ArrayList<String>());
			}
			requestMap.get(sessionId).add(requestKey);
		}
		getterInstance.setSessionId(sessionId);
		getterInstance.setRequestKey(requestKey);
		reasePreRequest(request, sessionId);
		this.logger.debug("进入等待队列之前：个数：{}", waitAlarmQueue.size());
		synchronized (waitAlarmQueue) {
			waitAlarmQueue.add(getterInstance);
		}
		setRequestRecord(requestKey, ids);
		getterInstance.onTimeout(new AlarmWaiterTimeOutHandler(requestKey, waitAlarmQueue));
		notifyAlarmWaiters(sessionId, requestKey);
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
		getDAO().getSession().createQuery("delete from AlarmReadRecord raw where raw.alarmId=" + alarmId)
				.executeUpdate();
	}

	private List<Alarm> getNoRecordAlarmList(String requestKey) {
		final int maxAlarmValiteTime = 1;// 天
		HashSet<Long> alarmIdOfRequest = new HashSet<Long>();
		synchronized (readRecord) {
			if (readRecord.containsKey(requestKey)) {
				alarmIdOfRequest = readRecord.get(requestKey);
			}
		}
		StringBuffer buff = new StringBuffer();
		Date currentDate = new Date(System.currentTimeMillis());
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(currentDate);
		rightNow.add(Calendar.DAY_OF_YEAR, -maxAlarmValiteTime);
		logger.debug("{}", "从数据库加载未处理的报警信息");
		if (alarmIdOfRequest.size() == 0) {
			logger.debug("{}", "请求中附带的读取列表为空,从数据库读取");
			/**
			 * 请求中附带的事件id为空 表明可能是第一次进入 或者浏览器刷新过
			 */
			/**
			 * 因此要把没有处理过的再重新加载一次，所以不能使用数据库的参考
			 */
			buff.append("  from Alarm alarm where alarm.dealFlag=" + Alarm.DEAL_FLAG_UNDEALED);
			buff.append("  and alarm.alarmTime>:maxTime");

		} else {
			logger.debug("{}", "请求中附带的读取列表非空，参考读取列表");
			buff.append("  from Alarm alarm where not exists  (select record.id from AlarmReadRecord record ");
			buff.append(" where alarm.id=record.alarmId ");
			buff.append(" and record.sessionKey='" + requestKey);
			buff.append("') and alarm.alarmTime>:maxTime and alarm.dealFlag=");
			buff.append(Alarm.DEAL_FLAG_UNDEALED);
		}
		Query query = alarmReadRecordService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("maxTime", rightNow.getTime());
		List<?> currentList = query.list();
		List<Alarm> returnList = new ArrayList<Alarm>();
		logger.debug("读取到列表");
		logger.debug("{}", currentList);
		if (currentList != null && currentList.size() > 0) {
			Iterator<?> it = currentList.iterator();
			while (it.hasNext()) {
				returnList.add((Alarm) it.next());
			}
		}
		return returnList;
	}

	/**
	 * 通知所有的客户端进行接警处理:弹出对话框
	 */
	private void notifyAlarmWaiters(String sessionId, String sessionKey) {
		printCurrentStatus();
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();

				// logger.debug(new
				// SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(waiter.getRecordTime()));
				// 最新事件列表
				List<Alarm> newAlarmList = getNoRecordAlarmList(sessionKey);
				List<Long> newAlarmIdList = new ArrayList<Long>();
				if (newAlarmList.size() == 0) {
					// logger.debug("{}", waiter.getTimePassed());
					if (waiter.getTimePassed() > ErcsDeferredResult.TIME_LIMIT.longValue()) {
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
					alarmReadRecordService.addReadRecord(sessionId, sessionKey, newAlarmIdCopy.toArray(new Long[0]));
				}

			}
		}
	}

	private void printCurrentStatus() {
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			int i = 0;
			logger.debug("===========current status===============");
			while (it.hasNext()) {
				waiter = it.next();
				logger.debug("sessionId:" + waiter.getSessionId());
				logger.debug("SetOrExpired:" + waiter.isSetOrExpired());
				logger.debug("SetOrExpired:" + waiter.isSetOrExpired());
				logger.debug("RecordTime:" + waiter.getRecordTime());
				logger.debug("TimePassed:" + waiter.getTimePassed());
				i++;
			}
			logger.debug("当前队列共有:" + i + "个连接");
		}
	}

	/**
	 * 主动释放上一个连接
	 * 
	 * @param sessionId
	 */
	private void reasePreRequest(HttpServletRequest request, String sessionId) {
		synchronized (waitAlarmQueue) {
			Iterator<ErcsDeferredResult<AlarmMessage>> it = waitAlarmQueue.iterator();
			ErcsDeferredResult<AlarmMessage> waiter = null;
			while (it.hasNext()) {
				waiter = it.next();
				if (waiter.getRequestKey().equals(this.getRequestKey(request))) {
					waiter.setErrorResult("{'message':'refreshed....'}");
					logger.debug("{}", "释放", waiter.getRequestKey(), "连接");
					logger.debug("{}", waiter);
					it.remove();
				}
			}
		}
	}

	public String getRequestKey(HttpServletRequest request) {
		return request.getRequestURI() + request.getSession().getId();
	}
}