/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;
/**
 * 设备管理-设备台账。
 * <p>
 * 设备。
 * 
 */
@Entity
@Table(name = "electr_equipments")
public class Equipment extends IDEntity {
	/**
	 * 设备分类
	 */
	@PageFields(describtion = "设备分类", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary deviceClass;

	/**
	 * 设备种类
	 */
	@PageFields(describtion = "设备种类", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary deviceCategory;
	/**
	 * 设备类型
	 */
	@PageFields(describtion = "设备类型", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary deviceType;
	/**
	 * 设备名称
	 */
	@PageFields(describtion = "设备名称", allowedNull = false, search = false)
	private String deviceName;
	/**
	 * 设备型号
	 */
	@PageFields(describtion = "设备型号", allowedNull = false, search = false)
	private String deviceModel;
	/**
	 * 使用环境
	 */
	@PageFields(describtion = "使用环境", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary serviceEnvironment;
	/**
	 * 所属区域
	 */
	@PageFields(describtion = "所属区域", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary deviceArea;
	/**
	 * 存放地点
	 */
	@PageFields(describtion = "存放地点", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=", selectShowField = "itemName")
	private Dictionary stowedPosition;
	/**
	 * 用途
	 */
	@PageFields(describtion = "用途", allowedNull = true, search = false)
	private String usage;
	/**
	 * 生产厂家
	 */
	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;
	/**
	 * 设备编号
	 */
	@PageFields(describtion = "设备编号", allowedNull = false, search = false)
	private String deviceNumber;
	/**
	 * 出厂编号
	 */
	@PageFields(describtion = "出厂编号", allowedNull = true, search = false)
	private String factoryNumber;
	/**
	 * 出厂日期
	 */
	@PageFields(describtion = "出厂日期", allowedNull = false, search = false)
	private Date productionDate;
	/**
	 * 包机人
	 */
	@PageFields(describtion = "包机人", allowedNull = true, search = false)
	private String chargePerson;
	/**
	 * 班长/组长
	 */
	@PageFields(describtion = "班长/组长", allowedNull = true, search = false)
	private String monitor;
	/**
	 * 三开一防锁
	 */
	@PageFields(describtion = "三开一防锁", allowedNull = true, search = false)
	private String openLocker;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", allowedNull = true, search = false)
	private String lockerNumber;
	/**
	 * 图片路径
	 */
	@PageFields(describtion = "图片路径", allowedNull = true, search = false)
	private String pictureURL; // 图片路径
	/**
	 * 说明书路径
	 */
	@PageFields(describtion = "说明书路径", allowedNull = false, search = false)
	private String specificationURL; // 说明书路径

	public String getChargePerson() {
		return chargePerson;
	}

	@ManyToOne
	public Dictionary getDeviceArea() {
		return deviceArea;
	}

	@ManyToOne
	public Dictionary getDeviceCategory() {
		return deviceCategory;
	}

	@ManyToOne
	public Dictionary getDeviceClass() {
		return deviceClass;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	@ManyToOne
	public Dictionary getDeviceType() {
		return deviceType;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public String getLockerNumber() {
		return lockerNumber;
	}

	public String getMonitor() {
		return monitor;
	}

	public String getOpenLocker() {
		return openLocker;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public String getProducer() {
		return producer;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	@ManyToOne
	public Dictionary getServiceEnvironment() {
		return serviceEnvironment;
	}

	public String getSpecificationURL() {
		return specificationURL;
	}

	@ManyToOne
	public Dictionary getStowedPosition() {
		return stowedPosition;
	}

	@Column(name = "equipment_usage")
	public String getUsage() {
		return usage;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public void setDeviceArea(Dictionary deviceArea) {
		this.deviceArea = deviceArea;
	}

	public void setDeviceCategory(Dictionary deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public void setDeviceClass(Dictionary deviceClass) {
		this.deviceClass = deviceClass;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}

	public void setDeviceType(Dictionary deviceType) {
		this.deviceType = deviceType;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public void setLockerNumber(String lockerNumber) {
		this.lockerNumber = lockerNumber;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public void setOpenLocker(String openLocker) {
		this.openLocker = openLocker;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setServiceEnvironment(Dictionary serviceEnvironment) {
		this.serviceEnvironment = serviceEnvironment;
	}

	public void setSpecificationURL(String specificationURL) {
		this.specificationURL = specificationURL;
	}

	public void setStowedPosition(Dictionary stowedPosition) {
		this.stowedPosition = stowedPosition;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

}