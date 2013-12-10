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
 * 设备台账-配件管理。
 * <p>
 * 配件。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "electr_accessories")
public class Accessory extends IDEntity {
	private Long equipmentId;
	/**
	 * 配件型号
	 */
	@PageFields(describtion = "配件型号", allowedNull = false, search = false)
	private String accessoryModel;
	/**
	 * 配件编号
	 */
	@PageFields(describtion = "配件编号", allowedNull = false, search = false)
	private String accessoryNumber;
	/**
	 * 出厂日期
	 */
	@PageFields(describtion = "出厂日期", allowedNull = false, search = false)
	private Date productionDate;
	/**
	 * 生产厂家
	 */
	@PageFields(describtion = "生产厂家", allowedNull = false, search = false)
	private String producer;
	/**
	 * 运输功率(运输设备有效)
	 */
	@PageFields(describtion = "运输功率", allowedNull = true, search = false)
	private Integer serviceRating;
	/**
	 * 传动比(运输设备有效)
	 */
	@PageFields(describtion = "传动比", allowedNull = true, search = false)
	private Double transmissionRatio;
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

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public String getAccessoryModel() {
		return accessoryModel;
	}

	public void setAccessoryModel(String accessoryModel) {
		this.accessoryModel = accessoryModel;
	}

	public String getAccessoryNumber() {
		return accessoryNumber;
	}

	public void setAccessoryNumber(String accessoryNumber) {
		this.accessoryNumber = accessoryNumber;
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

	public Integer getServiceRating() {
		return serviceRating;
	}

	public void setServiceRating(Integer serviceRating) {
		this.serviceRating = serviceRating;
	}

	public Double getTransmissionRatio() {
		return transmissionRatio;
	}

	public void setTransmissionRatio(Double transmissionRatio) {
		this.transmissionRatio = transmissionRatio;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

}