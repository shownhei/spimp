/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * EmergencyPlanInstance。 救援预案实例
 */
@Entity
@Table(name = "ercs_emergency_plan_instances")
public class EmergencyPlanInstance extends IDEntity {
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
	 * 对应的事故
	 */
	private Alarm alarm;
	/**
	 * 具体工作安排
	 */
	private String taskContent;

	@ManyToOne
	public ResponseTeam getTeam() {
		return team;
	}

	public void setTeam(ResponseTeam team) {
		this.team = team;
	}

	/**
	 * 事故类型
	 */
	@ManyToOne
	public Dictionary getEmergencyCategory() {
		return emergencyCategory;
	}

	public void setEmergencyCategory(Dictionary emergencyCategory) {
		this.emergencyCategory = emergencyCategory;
	}

	/**
	 * 事故严重程度
	 */
	@ManyToOne
	public Dictionary getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(Dictionary emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	/**
	 * 对应的事故案例
	 * 
	 * @return
	 */
	@ManyToOne
	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	/**
	 * 具体工作安排
	 * 
	 * @return
	 */
	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

}