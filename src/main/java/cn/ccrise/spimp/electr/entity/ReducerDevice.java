/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 减速机-ReducerDevice
 * 关联运输设备-TransformEquipment
 */
@Entity
@Table(name = "electr_reducer_devices")
public class ReducerDevice extends IDEntity {
	@PageFields(describtion = "型号", allowedNull = false, search = false)
	private String deviceModel;
	
	@PageFields(describtion = "运行功率", allowedNull = true, search = false)
	private String runningPower;
	
	@PageFields(describtion = "传动比", allowedNull = true, search = false)
	private String transmissionRatio;
	
	@PageFields(describtion = "出厂编号", allowedNull = true, search = false)
	private String factoryNumber;
	
	@PageFields(describtion = "生产厂家", allowedNull = true, search = false)
	private String producer;
	/**
	 * 主记录的id
	 */
	private Long transformEquipmentId;
	@Lob
	public String getDeviceModel() {
		return deviceModel;
	}
	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	public String getRunningPower() {
		return runningPower;
	}
	public void setRunningPower(String runningPower) {
		this.runningPower = runningPower;
	}
	public String getTransmissionRatio() {
		return transmissionRatio;
	}
	public void setTransmissionRatio(String transmissionRatio) {
		this.transmissionRatio = transmissionRatio;
	}
	public String getFactoryNumber() {
		return factoryNumber;
	}
	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
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