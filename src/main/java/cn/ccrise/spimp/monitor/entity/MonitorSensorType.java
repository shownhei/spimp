/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * MonitorSensorType.
 * <p>
 * 监测监控传感器类型.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_sensortype")
public class MonitorSensorType extends IDEntity {
	/**
	 * 传感器类型编号
	 */
	private Integer sensorTypeId;
	/**
	 * 传感器名称
	 */
	private String sensorTypeName;
	/**
	 * 传感器监测数据单位
	 */
	private String sensorUnit;

	@Column(name = "sensortypeid")
	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	@Column(name = "sensortypename")
	public String getSensorTypeName() {
		return sensorTypeName;
	}

	@Column(name = "sensorunit")
	public String getSensorUnit() {
		return sensorUnit;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public void setSensorTypeName(String sensorTypeName) {
		this.sensorTypeName = sensorTypeName;
	}

	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
	}
}
