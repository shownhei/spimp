/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * DicitonaryType。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "system_dicitonary_types")
public class DicitonaryType extends IDEntity {
	/**
	 * 字典分组
	 */
	private String dicGroup;
	/**
	 * 字典类型
	 */
	private String dicType;
	/**
	 * 中文提示
	 */
	private String typeTitle;

	public String getDicGroup() {
		return dicGroup;
	}

	public String getDicType() {
		return dicType;
	}

	public String getTypeTitle() {
		return typeTitle;
	}

	public void setDicGroup(String dicGroup) {
		this.dicGroup = dicGroup;
	}

	public void setDicType(String dicType) {
		this.dicType = dicType;
	}

	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}

}