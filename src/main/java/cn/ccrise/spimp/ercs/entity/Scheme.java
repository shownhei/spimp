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
 * Scheme。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_schemes")
public class Scheme extends IDEntity {
	/**
	 * 事故发生时间
	 */
	private Timestamp startTime;
	/**
	 * 事故类型
	 */
	private Dictionary type;
	/**
	 * 事故发生地点
	 */
	private String address;
	/**
	 * 处置方案
	 */
	private String attachment;
	/**
	 * 方案制定人
	 */
	private String decide;
	/**
	 * 事故上传时间
	 */
	private Timestamp uploadTime;

	public String getAddress() {
		return address;
	}

	public String getDecide() {
		return decide;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getStartTime() {
		return startTime;
	}

	@ManyToOne
	public Dictionary getType() {
		return type;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setDecide(String decide) {
		this.decide = decide;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setType(Dictionary type) {
		this.type = type;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
}