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
@Table(name = "spmi_schedule_records")
public class Record extends IDEntity {
	// 日期
	@PageFields(describtion = "日期", allowedNull = false, search = true)
	private Date recordDate;

	// 时间
	@PageFields(describtion = "时间", columnWidth = 80)
	private String recordTime;

	// 队组
	@PageFields(describtion = "队组", allowedNull = false, columnWidth = 120, search = true, type = "select", selectDataUri = "/spmi/schedule/teams?list=true", selectShowField = "teamName")
	private Team team;

	// 班次
	@PageFields(describtion = "班次", columnWidth = 60, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField = "itemName")
	private Dictionary duty;

	// 地点
	@PageFields(describtion = "地点", columnShow = false)
	private String positon;

	// 汇报人
	@PageFields(describtion = "汇报人")
	private String reporter;

	// 接收人
	@PageFields(describtion = "接收人", columnShow = false)
	private String receiver;

	// 事故问题详情
	@PageFields(describtion = "事故问题详情", type = "textarea")
	private String problems;

	// 处理意见
	@PageFields(describtion = "处理意见", type = "textarea")
	private String opinion;

	// 处理结果
	@PageFields(describtion = "处理结果", type = "textarea")
	private String dealResults;

	// 验收人
	@PageFields(describtion = "验收人", columnShow = false)
	private String acceptancer;

	public String getAcceptancer() {
		return acceptancer;
	}

	public String getDealResults() {
		return dealResults;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public String getOpinion() {
		return opinion;
	}

	public String getPositon() {
		return positon;
	}

	public String getProblems() {
		return problems;
	}

	public String getReceiver() {
		return receiver;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public String getReporter() {
		return reporter;
	}

	@ManyToOne
	public Team getTeam() {
		return team;
	}

	public void setAcceptancer(String acceptancer) {
		this.acceptancer = acceptancer;
	}

	public void setDealResults(String dealResults) {
		this.dealResults = dealResults;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public void setPositon(String positon) {
		this.positon = positon;
	}

	public void setProblems(String problems) {
		this.problems = problems;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public void setReporter(String reporter) {
		this.reporter = reporter;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}