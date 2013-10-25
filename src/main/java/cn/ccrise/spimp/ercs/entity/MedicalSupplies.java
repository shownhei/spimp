/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * MedicalSupplies。 医疗救护器材、药品
 */
@Entity
@Table(name = "ercs_medical_supply")
public class MedicalSupplies extends IDEntity {
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 数量
	 */
	private Integer amount;

	/**
	 * 型号
	 */
	private String model;

	/**
	 * 产地
	 */
	private String origin;

	/**
	 * 购置时间
	 */
	private Date buyTime;

	/**
	 * 有效使用期限
	 */
	private String expiration;

	/**
	 * 使用、更换、报废情况
	 */
	private String situation;

	/**
	 * 备注
	 */
	private String remark;

	public Integer getAmount() {
		return amount;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public String getExpiration() {
		return expiration;
	}

	public String getModel() {
		return model;
	}

	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public String getRemark() {
		return remark;
	}

	public String getSituation() {
		return situation;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSituation(String situation) {
		this.situation = situation;
	}

}