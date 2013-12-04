/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ElectroGrade。
 * <p>
 * 机电专业评分。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_electro_grades")
public class ElectroGrade extends Grade {
	/**
	 * 双回路供电。
	 */
	private String way;
	/**
	 * 设备检测检验。
	 */
	private String checkout;
	/**
	 * 设备综合完好率。
	 */
	private String synthesize;
	/**
	 * 防爆率。
	 */
	private String antiexplosion;
	/**
	 * 带修率。
	 */
	private String maintain;
	/**
	 * 事故率。
	 */
	private String accident;

	public String getAccident() {
		return accident;
	}

	public String getAntiexplosion() {
		return antiexplosion;
	}

	public String getCheckout() {
		return checkout;
	}

	public String getMaintain() {
		return maintain;
	}

	public String getSynthesize() {
		return synthesize;
	}

	public String getWay() {
		return way;
	}

	public void setAccident(String accident) {
		this.accident = accident;
	}

	public void setAntiexplosion(String antiexplosion) {
		this.antiexplosion = antiexplosion;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public void setMaintain(String maintain) {
		this.maintain = maintain;
	}

	public void setSynthesize(String synthesize) {
		this.synthesize = synthesize;
	}

	public void setWay(String way) {
		this.way = way;
	}
}