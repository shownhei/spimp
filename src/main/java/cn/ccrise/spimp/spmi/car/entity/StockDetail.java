/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 入库出库库存明细。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "car_stock_details")
public class StockDetail extends IDEntity {
	/**
	 * 材料名称
	 */
	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String materialName1;
	/**
	 * 度量单位
	 */
	@PageFields(describtion = "度量单位", columnShow = true, search = false, allowedNull = false)
	private String measureUnit1;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", columnShow = true, search = false, allowedNull = false)
	private Integer quantity1;

	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String materialName2;
	/**
	 * 度量单位
	 */
	@PageFields(describtion = "度量单位", columnShow = true, search = false, allowedNull = false)
	private String measureUnit2;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", columnShow = true, search = false, allowedNull = false)
	private Integer quantity2;

	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String materialName3;
	/**
	 * 度量单位
	 */
	@PageFields(describtion = "度量单位", columnShow = true, search = false, allowedNull = false)
	private String measureUnit3;
	/**
	 * 数量
	 */
	@PageFields(describtion = "数量", columnShow = true, search = false, allowedNull = false)
	private Integer quantity3;

	private Integer opertionType;
	private Date yearMonth;

	/**
	 * 材料的原始id
	 */
	private Long materialId;

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName1() {
		return materialName1;
	}

	public void setMaterialName1(String materialName1) {
		this.materialName1 = materialName1;
	}

	public String getMeasureUnit1() {
		return measureUnit1;
	}

	public void setMeasureUnit1(String measureUnit1) {
		this.measureUnit1 = measureUnit1;
	}

	public Integer getQuantity1() {
		return quantity1;
	}

	public void setQuantity1(Integer quantity1) {
		this.quantity1 = quantity1;
	}

	public String getMaterialName2() {
		return materialName2;
	}

	public void setMaterialName2(String materialName2) {
		this.materialName2 = materialName2;
	}

	public String getMeasureUnit2() {
		return measureUnit2;
	}

	public void setMeasureUnit2(String measureUnit2) {
		this.measureUnit2 = measureUnit2;
	}

	public Integer getQuantity2() {
		return quantity2;
	}

	public void setQuantity2(Integer quantity2) {
		this.quantity2 = quantity2;
	}

	public String getMaterialName3() {
		return materialName3;
	}

	public void setMaterialName3(String materialName3) {
		this.materialName3 = materialName3;
	}

	public String getMeasureUnit3() {
		return measureUnit3;
	}

	public void setMeasureUnit3(String measureUnit3) {
		this.measureUnit3 = measureUnit3;
	}

	public Integer getQuantity3() {
		return quantity3;
	}

	public void setQuantity3(Integer quantity3) {
		this.quantity3 = quantity3;
	}

	public Integer getOpertionType() {
		return opertionType;
	}

	public void setOpertionType(Integer opertionType) {
		this.opertionType = opertionType;
	}

	@Column(name = "yearmonth")
	public Date getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(Date yearMonth) {
		this.yearMonth = yearMonth;
	}

}