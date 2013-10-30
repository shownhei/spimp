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
 * EmergencyPlan。 应急预案
 */
@Entity
@Table(name = "ercs_emergency_plans")
public class EmergencyPlan extends IDEntity {
	/**
	 * 预案名称
	 */
	private String planName;
	/**
	 * 预案种类
	 */
	private Dictionary planType;
	/**
	 * 附件
	 */
	private UploadedFile attachment;
	/**
	 * 上传时间
	 */
	private Timestamp addTime;

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public String getPlanName() {
		return planName;
	}

	@ManyToOne
	public Dictionary getPlanType() {
		return planType;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public void setPlanType(Dictionary planType) {
		this.planType = planType;
	}
}