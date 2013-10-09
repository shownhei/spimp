/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.AlarmReadRecordDAO;
import cn.ccrise.spimp.ercs.entity.AlarmReadRecord;

/**
 * AlarmReadRecord Service。
 */
@Service
public class AlarmReadRecordService extends HibernateDataServiceImpl<AlarmReadRecord, Long> {
	@Autowired
	private AlarmReadRecordDAO alarmReadRecordDAO;

	/**
	 * 添加已读取
	 */

	public void addReadRecord(String sessionId, Long alarmId[]) {
		if (alarmId != null && alarmId.length > 0) {
			for (Long element : alarmId) {
				AlarmReadRecord record = new AlarmReadRecord();
				record.setAlarmId(element);
				record.setRecordTime(System.currentTimeMillis());
				record.setSessionId(sessionId);
				save(record);
			}
		}
	}

	@Override
	public HibernateDAO<AlarmReadRecord, Long> getDAO() {
		return alarmReadRecordDAO;
	}

	/**
	 * 删除已经处理过的事件记录
	 * 
	 * @param alarmId
	 */
	public void removeAlarm(Long alarmId) {
		getDAO().getSession().createQuery("delete from AlarmReadRecord a where a.alarmId=" + alarmId).executeUpdate();
	}
}