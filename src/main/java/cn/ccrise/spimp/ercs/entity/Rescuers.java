/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * 应急救援人员。
 * 
 */
@Entity
@Table(name = "ercs_rescuers")
public class Rescuers extends IDEntity {
	/**
	 * 员工类型
	 */
	private String staffType;
	/**
	 * 姓名
	 */
	private String staffName;
	/**
	 * 部门
	 */
	private GroupEntity department;
	/**
	 * 专业领域
	 */
	private Dictionary expertiseArea;
	/**
	 * 职称
	 */
	private String title;
	/**
	 * 经验
	 */
	private String experience;
	/**
	 * 联系方式
	 */
	private String phone;
	/**
	 * 事故响应级别
	 */
	private Dictionary responseLevel;
	/**
	 * 添加时间
	 */
	private Timestamp addTime;

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	@ManyToOne
	public GroupEntity getDepartment() {
		return department;
	}

	public String getExperience() {
		return experience;
	}

	@ManyToOne
	public Dictionary getExpertiseArea() {
		return expertiseArea;
	}

	public String getPhone() {
		return phone;
	}

	@ManyToOne
	public Dictionary getResponseLevel() {
		return responseLevel;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getStaffType() {
		return staffType;
	}

	public String getTitle() {
		return title;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setDepartment(GroupEntity department) {
		this.department = department;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public void setExpertiseArea(Dictionary expertiseArea) {
		this.expertiseArea = expertiseArea;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setResponseLevel(Dictionary responseLevel) {
		this.responseLevel = responseLevel;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}