/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
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
	private Timestamp startTime;// 事故发生时间
	private String type;// 事故类型
	private String address;// 事故发生地点
	private String file;// 处置方案
	private String decide;// 方案制定人
	private Timestamp uploadTime;// 事故上传时间

	public String getAddress() {
		return address;
	}

	public String getDecide() {
		return decide;
	}

	public String getFile() {
		return file;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getStartTime() {
		return startTime;
	}

	public String getType() {
		return type;
	}

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

	public void setFile(String file) {
		this.file = file;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}