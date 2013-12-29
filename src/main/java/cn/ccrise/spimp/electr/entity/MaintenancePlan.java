/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 设备检修计划。
 * 
 */
@Entity
@Table(name = "electr_maintenance_plans")
public class MaintenancePlan extends IDEntity {
	/**
	 * 项目
	 */
	@PageFields(describtion = "检修内容", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary project;
	/**
	 * 检修内容
	 */
	@PageFields(describtion = "检修内容", allowedNull = false, search = false)
	private String content;
	/**
	 * 检修标准
	 */
	@PageFields(describtion = "检修标准", allowedNull = false, search = false)
	private String standard;
	/**
	 * 检查时间
	 */
	@PageFields(describtion = "检查时间", allowedNull = false, search = true)
	private Date workDate;
	/**
	 * 负责人
	 */
	@PageFields(describtion = "负责人", allowedNull = false, search = false)
	private String chargePerson;

	public String getChargePerson() {
		return chargePerson;
	}

	@Lob
	public String getContent() {
		return content;
	}

	@ManyToOne
	public Dictionary getProject() {
		return project;
	}

	@Lob
	public String getStandard() {
		return standard;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setProject(Dictionary project) {
		this.project = project;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

}