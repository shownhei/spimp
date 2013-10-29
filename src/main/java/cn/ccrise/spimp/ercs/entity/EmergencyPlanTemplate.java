/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * EmergencyPlanTemplate。 应急预案模板
 */
@Entity
@Table(name = "ercs_emergency_plan_templates")
public class EmergencyPlanTemplate extends IDEntity {
	/**
	 * 事故类型
	 */
	private Dictionary emergencyCategory;
	/**
	 * 事故严重程度
	 */
	private Dictionary emergencyLevel;
	/**
	 * 专业组
	 */
	private ResponseTeam team;
	/**
	 * 救援措施内容
	 */
	private String taskContent;
	/**
	 * 添加时间
	 */
	private Timestamp addTime;

	@ManyToOne
	public Dictionary getEmergencyCategory() {
		return emergencyCategory;
	}

	public void setEmergencyCategory(Dictionary emergencyCategory) {
		this.emergencyCategory = emergencyCategory;
	}

	@ManyToOne
	public Dictionary getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(Dictionary emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	@ManyToOne
	public ResponseTeam getTeam() {
		return team;
	}

	public void setTeam(ResponseTeam team) {
		this.team = team;
	}

	@Lob
	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

}