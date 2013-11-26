/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.lang.reflect.Field;
import java.sql.Date;
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
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.entity.Dictionary;

/**
 * 应急救援人员。总指挥 副总指挥 成员 等都存在此表之内
 * 
 */
@Entity
@Table(name = "ercs_rescuers")
public class Rescuers extends IDEntity {
	public static void main(String a[]) {
		Rescuers t = new Rescuers();

		Field[] fs = t.getClass().getDeclaredFields();
		for (Field element : fs) {
			System.out.print("'" + element.getName() + "',");
		}
	}

	/**
	 * 姓名
	 */
	private String staffName;
	/**
	 * 出生日期
	 */
	private Date birthDay;
	/**
	 * 文化程度
	 */
	private Dictionary education;
	/**
	 * 政治面貌
	 */
	private String policitalStatus;
	/**
	 * 开始工作时间
	 */
	private Date workDate;
	/**
	 * 入队时间
	 */
	private Date enqueueDate;
	/**
	 * 住址
	 */
	private String address;
	/**
	 * 电话
	 */
	private String telephone;
	/**
	 * 身份证号
	 */
	private String iDNumber;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 部门
	 */
	private GroupEntity department;
	/**
	 * 专业领域
	 */
	private Dictionary expertiseArea;
	/**
	 * 事故响应级别
	 */
	private Dictionary responseLevel;
	/**
	 * 关联账户
	 */
	private Account account;

	/**
	 * 添加时间
	 */
	private Timestamp addTime;

	/**
	 * 关联账号
	 * 
	 * @return
	 */
	@ManyToOne
	public Account getAccount() {
		return account;
	}

	public String getAddress() {
		return address;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	@ManyToOne
	public GroupEntity getDepartment() {
		return department;
	}

	@ManyToOne
	public Dictionary getEducation() {
		return education;
	}

	public Date getEnqueueDate() {
		return enqueueDate;
	}

	@ManyToOne
	public Dictionary getExpertiseArea() {
		return expertiseArea;
	}

	public String getiDNumber() {
		return iDNumber;
	}

	public String getPolicitalStatus() {
		return policitalStatus;
	}

	public String getRemark() {
		return remark;
	}

	@ManyToOne
	public Dictionary getResponseLevel() {
		return responseLevel;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getTelephone() {
		return telephone;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public void setDepartment(GroupEntity department) {
		this.department = department;
	}

	public void setEducation(Dictionary education) {
		this.education = education;
	}

	public void setEnqueueDate(Date enqueueDate) {
		this.enqueueDate = enqueueDate;
	}

	public void setExpertiseArea(Dictionary expertiseArea) {
		this.expertiseArea = expertiseArea;
	}

	public void setiDNumber(String iDNumber) {
		this.iDNumber = iDNumber;
	}

	public void setPolicitalStatus(String policitalStatus) {
		this.policitalStatus = policitalStatus;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setResponseLevel(Dictionary responseLevel) {
		this.responseLevel = responseLevel;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

}