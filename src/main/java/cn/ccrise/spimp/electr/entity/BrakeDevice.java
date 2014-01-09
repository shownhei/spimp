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
 * 制动器-BrakeDevice。 关联运输设备-TransformEquipment
 */
@Entity
@Table(name = "electr_brake_devices")
public class BrakeDevice extends IDEntity {

	@PageFields(describtion = "型号", allowedNull = false, search = false)
	private String deviceModel;

	@PageFields(describtion = "编号", allowedNull = true, search = false)
	private String factoryNumber;

	@PageFields(describtion = "出厂日期", allowedNull = false, search = false)
	private Date productionDate;

	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;

	private Long transformEquipmentId;

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getFactoryNumber() {
		return factoryNumber;
	}

	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public Long getTransformEquipmentId() {
		return transformEquipmentId;
	}

	public void setTransformEquipmentId(Long transformEquipmentId) {
		this.transformEquipmentId = transformEquipmentId;
	}

}