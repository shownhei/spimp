/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.entity.Account;
import cn.ccrise.spimp.entity.Dictionary;

/**
 * SpeciaList。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_specia_lists")
public class SpeciaList extends IDEntity {
	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 人员名称
	 */
	private String name;

	/**
	 * 出生日期
	 */
	private Date birthDay;

	/**
	 * 专业类型
	 */
	private Dictionary specialty;

	/**
	 * 健康状况
	 */
	private String physicalCondition;

	/**
	 * 手机
	 */
	private String mobile;

	/**
	 * 住址
	 */
	private String address;

	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 关联账户
	 */
	private Account account;

	@ManyToOne
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getAddress() {
		return address;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public Integer getGender() {
		return gender;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public String getPhysicalCondition() {
		return physicalCondition;
	}

	@Lob
	public String getRemark() {
		return remark;
	}

	@ManyToOne
	public Dictionary getSpecialty() {
		return specialty;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhysicalCondition(String physicalCondition) {
		this.physicalCondition = physicalCondition;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSpecialty(Dictionary specialty) {
		this.specialty = specialty;
	}

}