/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Alteration。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_alterations")
public class Alteration extends IDEntity {
	private Date startDate; // 变更开始日期
	private Date endDate; // 变更结束日期

	public Date getEndDate() {
		return endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
}