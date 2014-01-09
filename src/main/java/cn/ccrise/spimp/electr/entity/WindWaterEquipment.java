/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 井下压风、供水自救装置统计表。
 * 
 */
@Entity
@Table(name = "electr_wind_water_equipments")
public class WindWaterEquipment extends IDEntity {
	/**
	 * 安装位置
	 */
	@PageFields(describtion = "安装位置", allowedNull = false, search = true)
	private String location;
	/**
	 * 编号
	 */
	@PageFields(describtion = "编号", allowedNull = false, search = true)
	private String equipmentCode;
	/**
	 * 压风安装套数
	 */
	@PageFields(describtion = "安装套数", allowedNull = true, search = false)
	private String windAmount;
	/**
	 * 压风维护周期
	 */
	@PageFields(describtion = "维护周期", allowedNull = true, search = false)
	private String windCycle;
	/**
	 * 供水安装套数
	 */
	@PageFields(describtion = "安装套数", allowedNull = true, search = false)
	private String waterAmount;
	/**
	 * 供水维护周期
	 */
	@PageFields(describtion = "维护周期", allowedNull = true, search = false)
	private String waterCycle;
	/**
	 * 负责人
	 */
	@PageFields(describtion = "负责人", allowedNull = false, search = false)
	private String chargePerson;
	/**
	 * 电话号码
	 */
	@PageFields(describtion = "电话号码", allowedNull = false, search = false)
	private String phoneNumber;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注", allowedNull = true, search = false)
	private String remark;
	/**
	 * 记录日期
	 */
	@PageFields(describtion = "记录日期", allowedNull = true, search = false)
	private Date recordDate;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getWindAmount() {
		return windAmount;
	}
	public void setWindAmount(String windAmount) {
		this.windAmount = windAmount;
	}
	public String getWindCycle() {
		return windCycle;
	}
	public void setWindCycle(String windCycle) {
		this.windCycle = windCycle;
	}
	public String getWaterAmount() {
		return waterAmount;
	}
	public void setWaterAmount(String waterAmount) {
		this.waterAmount = waterAmount;
	}
	public String getWaterCycle() {
		return waterCycle;
	}
	public void setWaterCycle(String waterCycle) {
		this.waterCycle = waterCycle;
	}
	public String getChargePerson() {
		return chargePerson;
	}
	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(updatable=false)
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
}