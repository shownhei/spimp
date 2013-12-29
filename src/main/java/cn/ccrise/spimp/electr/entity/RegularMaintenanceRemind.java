/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 定期保养到期提醒。
 * 
 */
@Entity
@Table(name = "electr_regular_maintenance_reminds")
public class RegularMaintenanceRemind extends IDEntity {
	/**
	 * 车号
	 */
	@PageFields(describtion = "车号", allowedNull = true, search = true, type = "select", selectDataUri = "/spmi/car/carslist", selectShowField = "carNo")
	private Car car;
	/**
	 * 保养类别:日常保养 、一级保养、二级保养、三级保养
	 */
	@PageFields(describtion = "保养类别", allowedNull = false, search = true)
	private Integer maintenanceLevel;
	/**
	 * 最大行驶公里数
	 */
	@PageFields(describtion = "已行驶公里数", allowedNull = false, search = false)
	private Long kilometres;
	/**
	 * 保养日期
	 */
	@PageFields(describtion = "保养日期", allowedNull = false, search = false)
	private Date maintenanceDate;
	/**
	 * 维修状态:是否维修,1 ：已保养,0:未保养
	 */
	@PageFields(describtion = "保养状态", allowedNull = true, search = false)
	private Integer maintenanceStatus;

	/**
	 * 已保养
	 */
	public static final Integer MAINTENANCE_STATUS_YES = 1;
	/**
	 * 未保养
	 */
	public static final Integer MAINTENANCE_STATUS_NO = 0;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public Long getKilometres() {
		return kilometres;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	public Integer getMaintenanceLevel() {
		return maintenanceLevel;
	}

	public Integer getMaintenanceStatus() {
		return maintenanceStatus;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setKilometres(Long kilometres) {
		this.kilometres = kilometres;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public void setMaintenanceLevel(Integer maintenanceLevel) {
		this.maintenanceLevel = maintenanceLevel;
	}

	public void setMaintenanceStatus(Integer maintenanceStatus) {
		this.maintenanceStatus = maintenanceStatus;
	}

}