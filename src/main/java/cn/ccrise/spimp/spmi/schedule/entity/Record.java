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
 * Record。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "schedule_records")
public class Record extends IDEntity {
	// 日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date recordDate;
	
	// 时间
	@PageFields(describtion="时间")
	private String recordTime;
	
	// 队组
	@PageFields(describtion="队组", allowedNull=false, search=true, type="select", selectDataUri="/spmi/schedule/teams?list=true", selectShowField="teamName")
	private Team team;
	
	// 班次
	@PageFields(describtion="班次", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField="itemName")
	private Dictionary duty;
	
	// 地点
	@PageFields(describtion="地点")
	private String positon;
	
	// 汇报人
	@PageFields(describtion="汇报人")
	private String reporter;
	
	// 接收人
	@PageFields(describtion="接收人")
	private String receiver;
	
	// 事故问题详情
	@PageFields(describtion="事故问题详情", type="textarea")
	private String problems;
	
	// 处理意见
	@PageFields(describtion="处理意见", type="textarea")
	private String opinion;
	
	// 处理结果
	@PageFields(describtion="处理结果", type="textarea")
	private String dealResults;
	
	// 验收人
	@PageFields(describtion="验收人")
	private String acceptancer;

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@ManyToOne
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	public String getPositon() {
		return positon;
	}

	public void setPositon(String positon) {
		this.positon = positon;
	}

	public String getReporter() {
		return reporter;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getProblems() {
		return problems;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public String getDealResults() {
		return dealResults;
	}

	public void setDealResults(String dealResults) {
		this.dealResults = dealResults;
	}

	public String getAcceptancer() {
		return acceptancer;
	}

	public void setAcceptancer(String acceptancer) {
		this.acceptancer = acceptancer;
	}
}