/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 拉紧装置-TensioningDevice
 * 
 * 关联运输设备-TransformEquipment
 */
@Entity
@Table(name = "electr_tensioning_devices")
public class TensioningDevice extends IDEntity {
	// 拉紧方式
	@PageFields(describtion = "拉紧方式", allowedNull = false, search = false)
	private String takeUp;
	// 装置名称
	@PageFields(describtion = "装置名称", allowedNull = false, search = false)
	private String deviceName;
	// 型号
	@PageFields(describtion = "型号", allowedNull = false, search = false)
	private String deviceModel;
	// 编号
	@PageFields(describtion = "编号", allowedNull = false, search = false)
	private String deviceNumber;
	// 出厂日期
	@PageFields(describtion = "出厂日期", allowedNull = false, search = false)
	private Date productionDate;
	// 生厂产家
	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;
	// 技术参数
	@PageFields(describtion = "技术参数", allowedNull = true, search = false)
	private String techParameters;

	private Long transformEquipmentId;

	@Lob
	public String getDeviceModel() {
		return deviceModel;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public String getDeviceNumber() {
		return deviceNumber;
	}

	public String getProducer() {
		return producer;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public String getTakeUp() {
		return takeUp;
	}

	@Lob
	public String getTechParameters() {
		return techParameters;
	}

	public Long getTransformEquipmentId() {
		return transformEquipmentId;
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

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setTakeUp(String takeUp) {
		this.takeUp = takeUp;
	}

	public void setTechParameters(String techParameters) {
		this.techParameters = techParameters;
	}

	public void setTransformEquipmentId(Long transformEquipmentId) {
		this.transformEquipmentId = transformEquipmentId;
	}

}