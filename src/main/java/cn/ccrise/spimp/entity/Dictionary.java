/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Dictionary。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_dictionaries")
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
	private Integer sortIndex;

	public String getItemName() {
		return itemName;
	}

	public Integer getSortIndex() {
		return sortIndex;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setSortIndex(Integer sortIndex) {
		this.sortIndex = sortIndex;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Override
	public String toString() {
		return itemName;
	}
}