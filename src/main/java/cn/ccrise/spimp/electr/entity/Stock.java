/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * 库存
 * 
 */
@Entity
@Table(name = "electr_stocks")
public class Stock extends IDEntity {
	/**
	 * 材料名称
	 */
	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String materialName;
	/**
	 * 规格型号、设备号
	 */
	@PageFields(describtion = "规格型号/设备号", columnShow = true, search = true, allowedNull = false)
	private String model;
	/**
	 * 度量单位
	 */
	@PageFields(describtion = "度量单位", columnShow = true, search = false, allowedNull = false)
	private String measureUnit;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", columnShow = true, search = false, allowedNull = false)
	private Integer amount;
	/**
	 * 单价（元）
	 */
	@PageFields(describtion = "单价（元）", columnShow = true, search = false, allowedNull = false)
	private String price;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注")
	private String remark;
	/**
	 * 更新时间
	 */
	@PageFields(describtion = "更新时间")
	private Timestamp updateTime;
	/**
	 * 记录组织
	 */
	private GroupEntity recordGroup;

	public Integer getAmount() {
		return amount;
	}

	public String getMaterialName() {
		return materialName;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public String getModel() {
		return model;
	}

	public String getPrice() {
		return price;
	}

	@ManyToOne
	public GroupEntity getRecordGroup() {
		return recordGroup;
	}

	public String getRemark() {
		return remark;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRecordGroup(GroupEntity recordGroup) {
		this.recordGroup = recordGroup;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}