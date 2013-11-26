/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * Circumstance。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_schedule_circumstances")
public class Circumstance extends IDEntity {
	// 日期
	@PageFields(describtion = "日期", allowedNull = false, search = true)
	private Date workDate;

	// 班次
	@PageFields(describtion = "班次", columnWidth = 60, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField = "itemName")
	private Dictionary duty;

	// 姓名
	@PageFields(describtion = "姓名", search = true, allowedNull = false)
	private String name;

	// 职务
	@PageFields(describtion = "职务", columnWidth = 90)
	private String business;

	// 下井时间
	@PageFields(describtion = "下井时间")
	private Date downWellTime;

	// 升井时间
	@PageFields(describtion = "升井时间")
	private Date upWellTime;

	// 汇报地点
	@PageFields(describtion = "汇报地点", columnShow = false)
	private String reportPosition;

	// 内容
	@PageFields(describtion = "内容", type = "textarea")
	private String contents;

	// 发现问题
	@PageFields(describtion = "发现问题", type = "textarea")
	private String problems;

	// 处理意见
	@PageFields(describtion = "处理意见", type = "textarea")
	private String problemsDeal;

	public String getBusiness() {
		return business;
	}

	public String getContents() {
		return contents;
	}

	public Date getDownWellTime() {
		return downWellTime;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public String getName() {
		return name;
	}

	public String getProblems() {
		return problems;
	}

	public String getProblemsDeal() {
		return problemsDeal;
	}

	public String getReportPosition() {
		return reportPosition;
	}

	public Date getUpWellTime() {
		return upWellTime;
	}

	public Date getWorkDate() {
		return workDate;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public void setDownWellTime(Date downWellTime) {
		this.downWellTime = downWellTime;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public void setProblemsDeal(String problemsDeal) {
		this.problemsDeal = problemsDeal;
	}

	public void setReportPosition(String reportPosition) {
		this.reportPosition = reportPosition;
	}

	public void setUpWellTime(Date upWellTime) {
		this.upWellTime = upWellTime;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}
}