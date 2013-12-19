/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 定期保养提醒设置。
 * 
 */
@Entity
@Table(name = "electr_regular_maintenance_configs")
public class RegularMaintenanceConfig extends IDEntity {
	/**
	 * 保养类别:日常保养 、一级保养、二级保养、三级保养
	 */
	@PageFields(describtion = "保养类别", allowedNull = false, search = true)
	private Integer maintenanceLevel;
	/**
	 * 最大行驶公里数
	 */
	@PageFields(describtion = "公里数", allowedNull = false, search = false)
	private Long kilometres;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", allowedNull = true, search = false)
	private Date recordDate;
	/**
	 * 修改时间
	 */
	@PageFields(describtion = "修改时间", allowedNull = true, search = false)
	private Date updateDate;
	/**
	 * 创建单位
	 */
	@PageFields(describtion = "创建单位", allowedNull = true, search = true)
	private GroupEntity planGroup;

	public Integer getMaintenanceLevel() {
		return maintenanceLevel;
	}

	public void setMaintenanceLevel(Integer maintenanceLevel) {
		this.maintenanceLevel = maintenanceLevel;
	}

	public Long getKilometres() {
		return kilometres;
	}

	public void setKilometres(Long kilometres) {
		this.kilometres = kilometres;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@ManyToOne
	public GroupEntity getPlanGroup() {
		return planGroup;
	}

	public void setPlanGroup(GroupEntity planGroup) {
		this.planGroup = planGroup;
	}

}