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
@Table(name = "schedule_reports")
public class Report extends IDEntity {
	
	// 日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date reportDate;
	
	// 班次
	@PageFields(describtion="班次", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField="itemName")
	private Dictionary duty;
	
	// 姓名
	@PageFields(describtion="姓名", search=true, allowedNull=false)
	private String name;
	
	// 职务
	@PageFields(describtion="职务")
	private String business;
	
	@PageFields(describtion="汇报时间")
	private Date reportDoDate;
	
	@PageFields(describtion="汇报地点")
	private String reportPositon;
	
	@PageFields(describtion="班前汇报", type="textarea")
	private String beforeDutyReprot;
	
	@PageFields(describtion="班中汇报", type="textarea")
	private String onDutyReport;
	
	@PageFields(describtion="班后汇报", type="textarea")
	private String afterDutyReport;

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public Date getReportDoDate() {
		return reportDoDate;
	}

	public void setReportDoDate(Date reportDoDate) {
		this.reportDoDate = reportDoDate;
	}

	public String getReportPositon() {
		return reportPositon;
	}

	public void setReportPositon(String reportPositon) {
		this.reportPositon = reportPositon;
	}

	public String getBeforeDutyReprot() {
		return beforeDutyReprot;
	}

	public void setBeforeDutyReprot(String beforeDutyReprot) {
		this.beforeDutyReprot = beforeDutyReprot;
	}

	public String getOnDutyReport() {
		return onDutyReport;
	}

	public void setOnDutyReport(String onDutyReport) {
		this.onDutyReport = onDutyReport;
	}

	public String getAfterDutyReport() {
		return afterDutyReport;
	}

	public void setAfterDutyReport(String afterDutyReport) {
		this.afterDutyReport = afterDutyReport;
	}
}