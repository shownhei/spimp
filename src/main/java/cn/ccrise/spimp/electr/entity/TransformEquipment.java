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
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 运输设备台账。 关联配件 : (1):减速机-ReducerDevice; (2):电动机-ElectromotorDevice;
 * (3):制动器-BrakeDevice; (4):拉紧装置-TensioningDevice;
 */
@Entity
@Table(name = "electr_transform_equipments")
public class TransformEquipment extends IDEntity {
	/**
	 * 设备分类
	 */
	@PageFields(describtion = "设备分类", allowedNull = false, search = true, type = "select", selectDataUri = "/system/dictionaries?list=true&typeCode=transform_device_class", selectShowField = "itemName")
	private Dictionary deviceClass;
	/**
	 * 设备名称
	 */
	@PageFields(describtion = "设备名称", allowedNull = false, search = true)
	private String deviceName;
	/**
	 * 设备型号
	 */
	@PageFields(describtion = "设备型号", allowedNull = false, search = true)
	private String deviceModel;
	@PageFields(describtion = "速度(m/s)", allowedNull = false, search = false)
	private String speed;
	@PageFields(describtion = "输送量(T/h)", allowedNull = false, search = false)
	private String conveyingCapacity;
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
	@PageFields(describtion = "设备编号", allowedNull = true, search = false)
	private String equipmentNumber;
	@PageFields(describtion = "使用地点", allowedNull = true, search = false)
	private String location;
	/**
	 * 生产厂家
	 */
	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;
	/**
	 * 布置长度(运输设备有效)
	 */
	@PageFields(describtion = "布置长度(m)", allowedNull = true, search = false)
	private Integer layoutLength;
	/**
	 * 单位
	 */
	@PageFields(describtion = "单位", allowedNull = false, search = true)
	private GroupEntity recordGroup;

	public String getConveyingCapacity() {
		return conveyingCapacity;
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

	public String getEquipmentNumber() {
		return equipmentNumber;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public Integer getLayoutLength() {
		return layoutLength;
	}

	public String getLocation() {
		return location;
	}

	public String getProducer() {
		return producer;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	@ManyToOne
	public GroupEntity getRecordGroup() {
		return recordGroup;
	}

	public String getSpeed() {
		return speed;
	}

	public void setConveyingCapacity(String conveyingCapacity) {
		this.conveyingCapacity = conveyingCapacity;
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

	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public void setLayoutLength(Integer layoutLength) {
		this.layoutLength = layoutLength;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setRecordGroup(GroupEntity recordGroup) {
		this.recordGroup = recordGroup;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

}