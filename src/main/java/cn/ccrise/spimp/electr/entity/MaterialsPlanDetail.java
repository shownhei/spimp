/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 材料计划明细
 * 
 */
@Entity
@Table(name = "electr_materials_plan_details")
public class MaterialsPlanDetail extends IDEntity {
	/**
	 * 材料计划
	 */
	@PageFields(describtion = "材料计划", allowedNull = false, search = true, type = "select", selectDataUri = "/spmi/schedule/teams?list=true", selectShowField = "planTitle")
	private MaterialsPlan plan;
	/**
	 * 材料名称
	 */
	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String materialName;
	/**
	 * 规格型号、设备号
	 */
	@PageFields(describtion = "规格型号、设备号", columnShow = true, search = true, allowedNull = false)
	private String model;
	/**
	 * 度量单位
	 */
	@PageFields(describtion = "度量单位", columnShow = true, search = false, allowedNull = false)
	private String measureUnit;
	/**
	 * 单价（元）
	 */
	@PageFields(describtion = "单价（元）", columnShow = true, search = false, allowedNull = false)
	private Double price;
	/**
	 * 数量
	 */
	private Integer quantity;
	/**
	 * 金额
	 */
	private Double sumMoney;

	/**
	 * 备注
	 */
	@PageFields(describtion = "备注")
	private String remark;

	public String getMaterialName() {
		return materialName;
	}

	public String getMeasureUnit() {
		return measureUnit;
	}

	public String getModel() {
		return model;
	}

	@ManyToOne
	@JoinColumn(name = "materials_plan")
	public MaterialsPlan getPlan() {
		return plan;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public String getRemark() {
		return remark;
	}

	public Double getSumMoney() {
		return sumMoney;
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

	public void setPlan(MaterialsPlan plan) {
		this.plan = plan;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}
}