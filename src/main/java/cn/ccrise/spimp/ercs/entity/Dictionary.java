/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Dictionary。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_dictionaries")
public class Dictionary extends IDEntity {
	/**
	 * 字典类型
	 */
	private String typeCode;
	/**
	 * 字典名称
	 */
	private String itemName;
	/**
	 * 字典值，权重
	 */
	private String itemValue;

	public String getItemName() {
		return itemName;
	}

	public String getItemValue() {
		return itemValue;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
}