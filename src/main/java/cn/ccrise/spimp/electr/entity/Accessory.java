/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.ercs.entity.UploadedFile;
import cn.ccrise.spimp.util.PageFields;

/**
 * 设备台账-配件管理。
 * <p>
 * 配件。
 * 
 */
@Entity
@Table(name = "electr_accessories")
public class Accessory extends IDEntity {
	private Long equipmentId;
	@PageFields(describtion = "配件名称", allowedNull = false, search = false)
	private String accessoryName;
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
	 * 存放位置
	 */
	private String accessoryLocation;
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

	/**
	 * 图片
	 */
	private String pictureURL;

	/**
	 * 说明书
	 */
	private UploadedFile instructions ;
	@ManyToOne
	public UploadedFile getInstructions() {
		return instructions;
	}

	public void setInstructions(UploadedFile instructions) {
		this.instructions = instructions;
	}

	public String getAccessoryName() {
		return accessoryName;
	}

	public void setAccessoryName(String accessoryName) {
		this.accessoryName = accessoryName;
	}

	public String getAccessoryLocation() {
		return accessoryLocation;
	}

	public void setAccessoryLocation(String accessoryLocation) {
		this.accessoryLocation = accessoryLocation;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getAccessoryModel() {
		return accessoryModel;
	}

	public String getAccessoryNumber() {
		return accessoryNumber;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public String getProducer() {
		return producer;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setAccessoryModel(String accessoryModel) {
		this.accessoryModel = accessoryModel;
	}

	public void setAccessoryNumber(String accessoryNumber) {
		this.accessoryNumber = accessoryNumber;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}