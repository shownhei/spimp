/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * 事故记录。
 * 
 */
@Entity
@Table(name = "electr_accident_records")
public class AccidentRecord extends IDEntity {
	/**
	 * 事故地点
	 */
	@PageFields(describtion = "事故地点", allowedNull = true, search = false)
	private String accidentAddress;
	/**
	 * 事故描述
	 */
	@PageFields(describtion = "事故描述", allowedNull = true, search = false)
	private String accidentDesc;
	/**
	 * 上报人
	 */
	@PageFields(describtion = "上报人", allowedNull = true, search = false)
	private String reporter;
	/**
	 * 事故类型
	 */
	@PageFields(describtion = "事故类型", allowedNull = true, search = false)
	private String accident;
	/**
	 * 事故日期
	 */
	@PageFields(describtion = "事故日期", allowedNull = true, search = true)
	private Date accedentDate;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", allowedNull = true, search = false)
	private Timestamp recordTime;

	public String getAccidentAddress() {
		return accidentAddress;
	}

	public void setAccidentAddress(String accidentAddress) {
		this.accidentAddress = accidentAddress;
	}

	public String getAccidentDesc() {
		return accidentDesc;
	}

	public void setAccidentDesc(String accidentDesc) {
		this.accidentDesc = accidentDesc;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getAccident() {
		return accident;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

}