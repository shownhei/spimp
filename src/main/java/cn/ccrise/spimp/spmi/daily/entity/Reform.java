/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Reform。
 * <p>
 * 整改通知单。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_reforms")
public class Reform extends IDEntity {
	private String number; // 编号：机构编号+日期+序号
	@NotBlank
	private String issue; // 存在安全问题或隐患
	private Date deadlineDate; // 整改期限
	private String standard; // 整改标准或要求
	private String measure; // 整改措施
	private String principal; // 整改责任人
	private Date checkDate; // 检查日期
	private String checker; // 检查人员
	private Long sendGroupId; // 下发部门id
	private String status; // 状态：未下发/已下发/已执行/已完成

	// 非持久化属性
	private String sendGroup; // 下发部门

	public Date getCheckDate() {
		return checkDate;
	}

	public String getChecker() {
		return checker;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	@Column(nullable = false)
	public String getIssue() {
		return issue;
	}

	public String getMeasure() {
		return measure;
	}

	@Column(nullable = false)
	public String getNumber() {
		return number;
	}

	public String getPrincipal() {
		return principal;
	}

	@Transient
	public String getSendGroup() {
		return sendGroup;
	}

	public Long getSendGroupId() {
		return sendGroupId;
	}

	public String getStandard() {
		return standard;
	}

	@Column(nullable = false)
	public String getStatus() {
		return status;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setSendGroup(String sendGroup) {
		this.sendGroup = sendGroup;
	}

	public void setSendGroupId(Long sendGroupId) {
		this.sendGroupId = sendGroupId;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}