/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 保养的明细记录。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "electr_maintenance_details")
public class MaintenanceDetail extends IDEntity {
	/**
	 * 保养维修单子
	 */
	private Maintenance maintenance;
	/**
	 * 检查维修项目
	 */
	@PageFields(describtion = "检查维修项目", allowedNull = false, search = false)
	private String checkItem;
	/**
	 * 保养方式
	 */
	@PageFields(describtion = "保养方式", allowedNull = false, search = false)
	private String maintenanceWay;
	/**
	 * 检修处理情况
	 */
	@PageFields(describtion = "检修处理情况", allowedNull = false, search = false)
	private String treatment;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注", allowedNull = false, search = false)
	private String remark;

	public String getCheckItem() {
		return checkItem;
	}

	/**
	 * 保养维修单子
	 * 
	 * @return
	 */
	@ManyToOne
	public Maintenance getMaintenance() {
		return maintenance;
	}

	public String getMaintenanceWay() {
		return maintenanceWay;
	}

	public String getRemark() {
		return remark;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setCheckItem(String checkItem) {
		this.checkItem = checkItem;
	}

	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}

	public void setMaintenanceWay(String maintenanceWay) {
		this.maintenanceWay = maintenanceWay;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

}