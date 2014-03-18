/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Troop。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "m_troop")
public class Troop extends IDEntity {
	private String name;
	private String startTime;
	private String endTime;

	@Column(name = "endtime")
	public String getEndTime() {
		return endTime;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "starttime")
	public String getStartTime() {
		return startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

}