/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * 接警表
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_alarms")
public class Alarm extends IDEntity {
	/**
	 * 流水号
	 */
	private Long serialNumber;
	/**
	 * 事故地点
	 */
	private String accidentLocation;
	/**
	 * 事故类型
	 */
	private Dictionary accidentType;
	/**
	 * 严重程度
	 */
	private String severity;
	/**
	 * 报警人
	 */
	private String alarmPeople;
	/**
	 * 报警时间
	 */
	private Timestamp alarmTime;

	public String getAccidentLocation() {
		return accidentLocation;
	}

	@ManyToOne
	public Dictionary getAccidentType() {
		return accidentType;
	}

	public String getAlarmPeople() {
		return alarmPeople;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAlarmTime() {
		return alarmTime;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public String getSeverity() {
		return severity;
	}

	public void setAccidentLocation(String accidentLocation) {
		this.accidentLocation = accidentLocation;
	}

	public void setAccidentType(Dictionary accidentType) {
		this.accidentType = accidentType;
	}

	public void setAlarmPeople(String alarmPeople) {
		this.alarmPeople = alarmPeople;
	}

	public void setAlarmTime(Timestamp alarmTime) {
		this.alarmTime = alarmTime;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}
}