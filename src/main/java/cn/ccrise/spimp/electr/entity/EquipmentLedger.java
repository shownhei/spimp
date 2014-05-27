/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * EquipmentLedger。 源自机电科的 台账模板，用以替代原来自机电机运队的台账
 * 
 */
@Entity
@Table(name = "electr_equipment_ledgers")
public class EquipmentLedger extends IDEntity {
	/**
	 * 设备名称
	 */
	@PageFields(describtion = "设备名称", allowedNull = false, search = true)
	private String deviceName;
	/**
	 * 设备编号
	 */
	@PageFields(describtion = "设备编号", allowedNull = true, search = true)
	private String equipmentID;

	/**
	 * 规格型号
	 */
	@PageFields(describtion = "规格型号", allowedNull = false, search = false)
	private String deviceModel;
	/**
	 * 生产厂家
	 */
	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;
	/**
	 * 技术特征
	 */
	@PageFields(describtion = "技术特征", allowedNull = true, search = false)
	private String technology;
	/**
	 * 单位
	 */
	@PageFields(describtion = "单位", search = false, allowedNull = false)
	private String measureUnit;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", search = false, allowedNull = false)
	private Integer amount;
	/**
	 * 出厂日期
	 */
	@PageFields(describtion = "出厂日期", allowedNull = false, search = false)
	private Date productionDate;
	/**
	 * 出厂编号
	 */
	@PageFields(describtion = "出厂编号", allowedNull = true, search = true)
	private String factoryNumber;
	/**
	 * 购买日期
	 */
	@PageFields(describtion = "购买日期", allowedNull = false, search = false)
	private Date buyDate;
	/**
	 * 使用日期
	 */
	@PageFields(describtion = "使用日期", allowedNull = false, search = false)
	private Date useDate;
	/**
	 * 使用年限
	 */
	@PageFields(describtion = "使用年限", allowedNull = false, search = false)
	private Integer serviceLife;

	/**
	 * 在籍
	 */
	@PageFields(describtion = "在籍", allowedNull = true, search = true)
	private String inMembership;

	/**
	 * 使用
	 */
	@PageFields(describtion = "使用", allowedNull = true, search = false)
	private String inUse;

	/**
	 * 备用
	 */
	@PageFields(describtion = "备用", allowedNull = true, search = false)
	private String isSpare;

	/**
	 * 待修
	 */
	@PageFields(describtion = "待修", allowedNull = true, search = false)
	private String needsRepair;

	/**
	 * 待报废
	 */
	@PageFields(describtion = "待报废", allowedNull = true, search = false)
	private String prepareScrapped;

	/**
	 * 已报废
	 */
	@PageFields(describtion = "已报废", allowedNull = true, search = false)
	private String scrapped;

	/**
	 * 借入
	 */
	@PageFields(describtion = "借入", allowedNull = true, search = false)
	private String borrowed;
	/**
	 * 借出
	 */
	@PageFields(describtion = "借出", allowedNull = true, search = false)
	private String isLoan;

	/**
	 * 主要附机
	 */
	@PageFields(describtion = "主要附机", allowedNull = true, search = false)
	private String attachedDevice;

	/**
	 * 原值
	 */
	@PageFields(describtion = "原值", allowedNull = true, search = false)
	private String originalValue;

	/**
	 * 净值
	 */
	@PageFields(describtion = "净值", allowedNull = true, search = false)
	private String netWorth;

	/**
	 * 使用地点
	 */
	@PageFields(describtion = "使用地点", allowedNull = true, search = false)
	private String usePlace;
	/**
	 * 是否防爆
	 */
	@PageFields(describtion = "是否防爆", allowedNull = true, search = false)
	private String explosionProof;

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getEquipmentID() {
		return equipmentID;
	}

	public void setEquipmentID(String equipmentID) {
		this.equipmentID = equipmentID;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getTechnology() {
		return technology;
	}

	public void setTechnology(String technology) {
		this.technology = technology;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public Integer getServiceLife() {
		return serviceLife;
	}

	public void setServiceLife(Integer serviceLife) {
		this.serviceLife = serviceLife;
	}

	public String getInMembership() {
		return inMembership;
	}

	public void setInMembership(String inMembership) {
		this.inMembership = inMembership;
	}

	public String getInUse() {
		return inUse;
	}

	public void setInUse(String inUse) {
		this.inUse = inUse;
	}

	public String getIsSpare() {
		return isSpare;
	}

	public void setIsSpare(String isSpare) {
		this.isSpare = isSpare;
	}

	public String getNeedsRepair() {
		return needsRepair;
	}

	public void setNeedsRepair(String needsRepair) {
		this.needsRepair = needsRepair;
	}

	public String getPrepareScrapped() {
		return prepareScrapped;
	}

	public void setPrepareScrapped(String prepareScrapped) {
		this.prepareScrapped = prepareScrapped;
	}

	public String getScrapped() {
		return scrapped;
	}

	public void setScrapped(String scrapped) {
		this.scrapped = scrapped;
	}

	public String getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(String borrowed) {
		this.borrowed = borrowed;
	}

	public String getIsLoan() {
		return isLoan;
	}

	public void setIsLoan(String isLoan) {
		this.isLoan = isLoan;
	}

	public String getAttachedDevice() {
		return attachedDevice;
	}

	public void setAttachedDevice(String attachedDevice) {
		this.attachedDevice = attachedDevice;
	}

	public String getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}

	public String getNetWorth() {
		return netWorth;
	}

	public void setNetWorth(String netWorth) {
		this.netWorth = netWorth;
	}

	public String getUsePlace() {
		return usePlace;
	}

	public void setUsePlace(String usePlace) {
		this.usePlace = usePlace;
	}

	public String getExplosionProof() {
		return explosionProof;
	}

	public void setExplosionProof(String explosionProof) {
		this.explosionProof = explosionProof;
	}

}