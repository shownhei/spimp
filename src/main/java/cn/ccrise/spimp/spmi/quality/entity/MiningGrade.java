/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * MiningGrade。
 * <p>
 * 采煤专业评分。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_mining_grades")
public class MiningGrade extends Grade {
	/**
	 * 采煤工作面名称。
	 */
	private String name;
	/**
	 * 采煤方法。
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