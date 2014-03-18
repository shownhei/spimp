/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import javax.persistence.MappedSuperclass;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 用于包含位置信息的实体.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@MappedSuperclass
public abstract class PositionEntity extends IDEntity {
	/**
	 * x坐标.
	 */
	private Double x;
	/**
	 * y坐标.
	 */
	private Double y;

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}
}
