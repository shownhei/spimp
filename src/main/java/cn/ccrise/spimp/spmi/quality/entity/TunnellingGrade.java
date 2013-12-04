/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TunnellingGrade。
 * <p>
 * 掘进专业评分。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_tunnelling_grades")
public class TunnellingGrade extends Grade {
	/**
	 * 掘进工作面名称。
	 */
	private String name;
	/**
	 * 掘进方法。
	 */
	private String way;

	public String getName() {
		return name;
	}

	public String getWay() {
		return way;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setWay(String way) {
		this.way = way;
	}
}