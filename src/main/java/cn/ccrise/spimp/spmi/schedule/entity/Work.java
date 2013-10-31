/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * Work。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "schedule_works")
public class Work extends IDEntity {
	// 日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date workDate;
	
	// 领导名称
	@PageFields(describtion="领导名称", allowedNull=false, search=true)
	private String leaderName;
	
	// 发现问题
	@PageFields(describtion="发现问题", type="textarea")
	private String problems;
	
	// 问题处理
	@PageFields(describtion="问题处理", type="textarea")
	private String problemsDeal;

	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
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