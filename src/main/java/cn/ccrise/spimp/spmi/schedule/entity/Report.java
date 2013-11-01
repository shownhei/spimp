/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * Report。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_schedule_reports")
public class Report extends IDEntity {

	// 日期
	@PageFields(describtion = "日期", allowedNull = false, search = true)
	private Date reportDate;

	// 班次
	@PageFields(describtion = "班次", columnWidth = 60, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField = "itemName")
	private Dictionary duty;

	// 姓名
	@PageFields(describtion = "姓名", search = true, allowedNull = false)
	private String name;

	// 职务
	@PageFields(describtion = "职务", columnShow = false)
	private String business;

	@PageFields(describtion = "汇报时间")
	private Date reportDoDate;

	@PageFields(describtion = "汇报地点", columnShow = false)
	private String reportPositon;

	@PageFields(describtion = "班前汇报", type = "textarea")
	private String beforeDutyReprot;

	@PageFields(describtion = "班中汇报", type = "textarea")
	private String onDutyReport;

	@PageFields(describtion = "班后汇报", type = "textarea")
	private String afterDutyReport;

	public String getAfterDutyReport() {
		return afterDutyReport;
	}

	public String getBeforeDutyReprot() {
		return beforeDutyReprot;
	}

	public String getBusiness() {
		return business;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public String getName() {
		return name;
	}

	public String getOnDutyReport() {
		return onDutyReport;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public Date getReportDoDate() {
		return reportDoDate;
	}

	public String getReportPositon() {
		return reportPositon;
	}

	public void setAfterDutyReport(String afterDutyReport) {
		this.afterDutyReport = afterDutyReport;
	}

	public void setBeforeDutyReprot(String beforeDutyReprot) {
		this.beforeDutyReprot = beforeDutyReprot;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnDutyReport(String onDutyReport) {
		this.onDutyReport = onDutyReport;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setReportDoDate(Date reportDoDate) {
		this.reportDoDate = reportDoDate;
	}

	public void setReportPositon(String reportPositon) {
		this.reportPositon = reportPositon;
	}
}