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
 * Circumstance。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "schedule_circumstances")
public class Circumstance extends IDEntity {
	// 日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date workDate;
	
	// 班次
	@PageFields(describtion="班次", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField="itemName")
	private Dictionary duty;
	
	// 姓名
	@PageFields(describtion="姓名", search=true, allowedNull=false)
	private String name;
	
	// 职务
	@PageFields(describtion="职务")
	private String business;
	
	// 下井时间
	@PageFields(describtion="下井时间")
	private Date downWellTime;
	
	// 升井时间
	@PageFields(describtion="升井时间")
	private Date upWellTime;
	
	// 汇报地点
	@PageFields(describtion="汇报地点")
	private String reportPosition;
	
	// 内容
	@PageFields(describtion="内容", type="textarea")
	private String contents;
	
	// 发现问题
	@PageFields(describtion="发现问题", type="textarea")
	private String problems;
	
	// 处理意见
	@PageFields(describtion="处理意见", type="textarea")
	private String problemsDeal;

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
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

	public Date getDownWellTime() {
		return downWellTime;
	}

	public void setDownWellTime(Date downWellTime) {
		this.downWellTime = downWellTime;
	}

	public Date getUpWellTime() {
		return upWellTime;
	}

	public void setUpWellTime(Date upWellTime) {
		this.upWellTime = upWellTime;
	}

	public String getReportPosition() {
		return reportPosition;
	}

	public void setReportPosition(String reportPosition) {
		this.reportPosition = reportPosition;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getProblemsDeal() {
		return problemsDeal;
	}

	public void setProblemsDeal(String problemsDeal) {
		this.problemsDeal = problemsDeal;
	}
}