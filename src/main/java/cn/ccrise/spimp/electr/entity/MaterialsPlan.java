/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 材料计划。
 * 
 */
@Entity
@Table(name = "car_materials_plans")
public class MaterialsPlan extends IDEntity {
	// 材料名称 规格型号或备件号 单位 数量 单价（元） 金额（元） 备注

	/**
	 * 计划的年月
	 */
	@PageFields(describtion = "计划日期", allowedNull = false, search = true)
	private String planDate;
	/**
	 * 计划单位
	 */
	@PageFields(describtion = "单位", allowedNull = false, search = true)
	private GroupEntity planGroup;
	/**
	 * 计划名称
	 */
	@PageFields(describtion = "计划名称", allowedNull = false, search = true)
	private String planTitle;

	public String getPlanDate() {
		return planDate;
	}

	@ManyToOne
	public GroupEntity getPlanGroup() {
		return planGroup;
	}

	public String getPlanTitle() {
		return planTitle;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	public void setPlanGroup(GroupEntity planGroup) {
		this.planGroup = planGroup;
	}

	public void setPlanTitle(String planTitle) {
		this.planTitle = planTitle;
	}

}