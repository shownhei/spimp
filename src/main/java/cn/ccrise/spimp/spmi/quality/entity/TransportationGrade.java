/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TransportationGrade。
 * <p>
 * 运输专业评分。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_transportation_grades")
public class TransportationGrade extends Grade {
	/**
	 * 运输方式。
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
	 * 矿车完好率。
	 */
	private String tramcar;
	/**
	 * 专用车辆完好率。
	 */
	private String specialCar;
	/**
	 * 完全生产。
	 */
	private String production;

	public String getCheckout() {
		return checkout;
	}

	public String getProduction() {
		return production;
	}

	public String getSpecialCar() {
		return specialCar;
	}

	public String getSynthesize() {
		return synthesize;
	}

	public String getTramcar() {
		return tramcar;
	}

	public String getWay() {
		return way;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}

	public void setProduction(String production) {
		this.production = production;
	}

	public void setSpecialCar(String specialCar) {
		this.specialCar = specialCar;
	}

	public void setSynthesize(String synthesize) {
		this.synthesize = synthesize;
	}

	public void setTramcar(String tramcar) {
		this.tramcar = tramcar;
	}

	public void setWay(String way) {
		this.way = way;
	}
}