/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.entity.Dictionary;

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

	/**
	 * 对应的事故案例
	 * 
	 * @return
	 */
	@ManyToOne
	public Alarm getAlarm() {
		return alarm;
	}

	/**
	 * 事故类型
	 */
	@ManyToOne
	public Dictionary getEmergencyCategory() {
		return emergencyCategory;
	}

	/**
	 * 事故严重程度
	 */
	@ManyToOne
	public Dictionary getEmergencyLevel() {
		return emergencyLevel;
	}

	/**
	 * 具体工作安排
	 * 
	 * @return
	 */
	public String getTaskContent() {
		return taskContent;
	}

	@ManyToOne
	public ResponseTeam getTeam() {
		return team;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public void setEmergencyCategory(Dictionary emergencyCategory) {
		this.emergencyCategory = emergencyCategory;
	}

	public void setEmergencyLevel(Dictionary emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public void setTeam(ResponseTeam team) {
		this.team = team;
	}

}