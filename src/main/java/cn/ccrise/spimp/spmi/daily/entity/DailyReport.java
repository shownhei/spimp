/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * DailyReport。
 * <p>
 * 日报表。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_daily_reports")
public class DailyReport extends IDEntity {
	private String shift; // 班次
	private Integer output; // 产量，单位：架
	private Double weight; // 实际重量，单位：吨
	private Date reportDate; // 报表日期
	private String issue; // 存在问题
	private String leaveIssue; // 遗留问题
	private String leader; // 跟班队长
	private String result; // 班前安排工作落实结果

	@Lob
	public String getIssue() {
		return issue;
	}

	public String getLeader() {
		return leader;
	}

	@Lob
	public String getLeaveIssue() {
		return leaveIssue;
	}

	public Integer getOutput() {
		return output;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public String getResult() {
		return result;
	}

	public String getShift() {
		return shift;
	}

	public Double getWeight() {
		return weight;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public void setLeaveIssue(String leaveIssue) {
		this.leaveIssue = leaveIssue;
	}

	public void setOutput(Integer output) {
		this.output = output;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}
}