/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@MappedSuperclass
public class MineIdAndNameWithId extends IDEntity {
	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}
}
