/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 故障管理
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "car_problems")
public class Problem extends IDEntity {
	/**
	 * 故障上报日期
	 */

	@PageFields(describtion = "上报日期", allowedNull = false, search = true)
	private Date reportDate;
	/**
	 * 故障车辆
	 */
	@PageFields(describtion = "故障车辆", allowedNull = false, search = true)
	private Car car;
	/**
	 * 故障说明
	 */
	@PageFields(describtion = "故障说明", allowedNull = false, search = false)
	private String problem;
	/**
	 * 上报人
	 */
	@PageFields(describtion = "上报人", allowedNull = false, search = false)
	private String reporter;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public String getProblem() {
		return problem;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public String getReporter() {
		return reporter;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
}