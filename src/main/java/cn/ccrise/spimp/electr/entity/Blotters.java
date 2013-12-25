/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
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
 * 流水账。
 * 
 */
@Entity
@Table(name = "electr_blotters")
public class Blotters extends IDEntity {
	/**
	 * 入库操作
	 */
	public static final Integer OPERTION_TYPE_PUTIN = 1;
	/**
	 * 出库操作
	 */
	public static final Integer OPERTION_TYPE_SENDOUT = 0;
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
	 * 操作类型 :1 入库；0 出库操作
	 */
	@PageFields(describtion = "操作类型", columnShow = false, search = false, allowedNull = false)
	private Integer opertionType;
	/**
	 * 经办人
	 */
	@PageFields(describtion = "经办人", columnShow = true, search = false, allowedNull = false)
	private String operator;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注")
	private String remark;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间")
	private Timestamp recordTime;
	/**
	 * 操作成败标志 0 处理中，1 成功 2 失败
	 */
	private Integer status;

	private Long originalId;
	/**
	 * 记录组织
	 */
	private GroupEntity recordGroup;

	public void setRecordGroup(GroupEntity recordGroup) {
		this.recordGroup = recordGroup;
	}

	@ManyToOne
	public GroupEntity getRecordGroup() {
		return recordGroup;
	}

	/**
	 * 记录组织
	 */

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

	public String getOperator() {
		return operator;
	}

	public Integer getOpertionType() {
		return opertionType;
	}

	public Long getOriginalId() {
		return originalId;
	}

	public String getPrice() {
		return price;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getStatus() {
		return status;
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

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setOpertionType(Integer opertionType) {
		this.opertionType = opertionType;
	}

	public void setOriginalId(Long originalId) {
		this.originalId = originalId;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}