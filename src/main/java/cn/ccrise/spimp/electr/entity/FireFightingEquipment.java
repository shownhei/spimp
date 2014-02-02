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
 * 井下消防器材统计表
 * 
 */
@Entity
@Table(name = "electr_fire_fighting_equipments")
public class FireFightingEquipment extends IDEntity {

	/**
	 * 存在地点
	 */
	@PageFields(describtion = "存在地点", allowedNull = false, search = true)
	private String location;
	/**
	 * 编号
	 */
	@PageFields(describtion = "编号", allowedNull = false, search = true)
	private String equipmentCode;
	/**
	 * 0.5m³ 沙箱容积
	 */
	@PageFields(describtion = "沙箱容积", allowedNull = true, search = false)
	private String sandBoxCapacity;
	/**
	 * 数量-co2
	 */
	@PageFields(describtion = "数量", allowedNull = true, search = false)
	private String amount1;
	/**
	 * 维修时间-co2
	 */
	@PageFields(describtion = "维修时间", allowedNull = true, search = false)
	private String maintenanceDate1;
	/**
	 * 干粉-数量
	 */
	@PageFields(describtion = "数量", allowedNull = true, search = false)
	private Integer amount2;
	/**
	 * 干粉-维修时间
	 */
	@PageFields(describtion = "维修时间", allowedNull = false, search = false)
	private String maintenance2;
	/**
	 * 消防斧
	 */
	@PageFields(describtion = "消防斧", allowedNull = false, search = false)
	private Integer fireAxe;
	/**
	 * 消防钩
	 */
	@PageFields(describtion = "消防钩", allowedNull = true, search = false)
	private Integer fireHook;
	/**
	 * 消防桶
	 */
	@PageFields(describtion = "消防桶", allowedNull = true, search = false)
	private Integer fireBucket;
	/**
	 * 消防锹
	 */
	@PageFields(describtion = "消防锹", allowedNull = true, search = false)
	private Integer fireShovel;
	/**
	 * 其他
	 */
	@PageFields(describtion = "其他", allowedNull = true, search = false)
	private String others;
	/**
	 * 记录日期
	 */
	@PageFields(describtion = "记录日期", allowedNull = true, search = false)
	private Date recordDate;

	public String getAmount1() {
		return amount1;
	}

	public Integer getAmount2() {
		return amount2;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public Integer getFireAxe() {
		return fireAxe;
	}

	public Integer getFireBucket() {
		return fireBucket;
	}

	public Integer getFireHook() {
		return fireHook;
	}

	public Integer getFireShovel() {
		return fireShovel;
	}

	public String getLocation() {
		return location;
	}

	public String getMaintenance2() {
		return maintenance2;
	}

	public String getMaintenanceDate1() {
		return maintenanceDate1;
	}

	public String getOthers() {
		return others;
	}

	@Column(updatable = false)
	public Date getRecordDate() {
		return recordDate;
	}

	public String getSandBoxCapacity() {
		return sandBoxCapacity;
	}

	public void setAmount1(String amount1) {
		this.amount1 = amount1;
	}

	public void setAmount2(Integer amount2) {
		this.amount2 = amount2;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public void setFireAxe(Integer fireAxe) {
		this.fireAxe = fireAxe;
	}

	public void setFireBucket(Integer fireBucket) {
		this.fireBucket = fireBucket;
	}

	public void setFireHook(Integer fireHook) {
		this.fireHook = fireHook;
	}

	public void setFireShovel(Integer fireShovel) {
		this.fireShovel = fireShovel;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMaintenance2(String maintenance2) {
		this.maintenance2 = maintenance2;
	}

	public void setMaintenanceDate1(String maintenanceDate1) {
		this.maintenanceDate1 = maintenanceDate1;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setSandBoxCapacity(String sandBoxCapacity) {
		this.sandBoxCapacity = sandBoxCapacity;
	}

}