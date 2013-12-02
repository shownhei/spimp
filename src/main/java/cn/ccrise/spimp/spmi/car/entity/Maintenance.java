/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 保养的主记录。主要包括 日常保养 和定期保养
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "car_maintenances")
public class Maintenance extends IDEntity {
	// 车牌号、保养类别、日期、保养人员、验收人
	/**
	 * 保养车辆
	 */
	@PageFields(describtion = "车牌号", allowedNull = false, search = true, type = "select", selectDataUri = "/spmi/car/carslist", selectShowField = "carNo")
	private Car car;
	/**
	 * 保养类别:日常保养 、一级保养、二级保养、三级保养
	 */
	@PageFields(describtion = "保养类别", allowedNull = false, search = true)
	private Integer maintenanceLevel;
	/**
	 * 保养日期
	 */
	@PageFields(describtion = "保养日期", allowedNull = false, search = true)
	private Date maintenanceDate;
	/**
	 * 保养人
	 */
	@PageFields(describtion = "保养人", allowedNull = false, search = true)
	private String maintenancePeople;
	/**
	 * 验收人
	 */
	@PageFields(describtion = "验收人", allowedNull = true, search = false)
	private String accepter;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Integer getMaintenanceLevel() {
		return maintenanceLevel;
	}

	public void setMaintenanceLevel(Integer maintenanceLevel) {
		this.maintenanceLevel = maintenanceLevel;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public String getMaintenancePeople() {
		return maintenancePeople;
	}

	public void setMaintenancePeople(String maintenancePeople) {
		this.maintenancePeople = maintenancePeople;
	}

	public String getAccepter() {
		return accepter;
	}

	public void setAccepter(String accepter) {
		this.accepter = accepter;
	}

}