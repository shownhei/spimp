/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * AlarmReadRecord。 已经读取过事件的记录表
 */
@Entity
@Table(name = "ercs_alarm_read_records")
public class AlarmReadRecord extends IDEntity {
	private String sessionKey;
	private String sessionId;
	private Long alarmId;
	private Long recordTime;

	public Long getAlarmId() {
		return alarmId;
	}

	public Long getRecordTime() {
		return recordTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setAlarmId(Long alarmId) {
		this.alarmId = alarmId;
	}

	public void setRecordTime(Long recordTime) {
		this.recordTime = recordTime;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}