/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 小改小革。
 * 
 */
@Entity
@Table(name = "electr_innovations")
public class Innovation extends IDEntity {
	/**
	 * 项目名称
	 */
	@PageFields(describtion = "项目名称", columnShow = true, search = true, allowedNull = false)
	private String projectName;
	/**
	 * 负责人
	 */
	@PageFields(describtion = "负责人", columnShow = true, search = false, allowedNull = false)
	private String chargePerson;
	/**
	 * 申报日期
	 */
	@PageFields(describtion = "申报日期", columnShow = true, search = false, allowedNull = true)
	private Date declarationDate;
	/**
	 * 实施地点
	 */
	@PageFields(describtion = "实施地点", columnShow = true, search = false, allowedNull = false)
	private String implementationAddress;
	/**
	 * 实施时间
	 */
	@PageFields(describtion = "实施时间", columnShow = true, search = false, allowedNull = false)
	private Date implementationPeriod;

	/**
	 * 参与人
	 */
	@PageFields(describtion = "参与人", columnShow = true, search = false, allowedNull = false)
	private String participant;
	/**
	 * 目的
	 */
	@PageFields(describtion = "目的", columnShow = false, search = false, allowedNull = false)
	private String inventionPurpose;
	/**
	 * 主要内容或原理
	 */
	@PageFields(describtion = "主要内容或原理", columnShow = false, search = false, allowedNull = false)
	private String content;
	/**
	 * 效果及经济社会效益分析
	 */
	@PageFields(describtion = "效果及经济社会效益分析", columnShow = false, search = false, allowedNull = false)
	private String analysis;
	/**
	 * 记录组织
	 */
	@PageFields(describtion = "记录组织", columnShow = false, search = false, allowedNull = true)
	private GroupEntity recordGroup;

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getChargePerson() {
		return chargePerson;
	}

	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}

	public Date getDeclarationDate() {
		return declarationDate;
	}

	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}

	public String getImplementationAddress() {
		return implementationAddress;
	}

	public void setImplementationAddress(String implementationAddress) {
		this.implementationAddress = implementationAddress;
	}

	public Date getImplementationPeriod() {
		return implementationPeriod;
	}

	public void setImplementationPeriod(Date implementationPeriod) {
		this.implementationPeriod = implementationPeriod;
	}

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
	}

	@Lob
	public String getInventionPurpose() {
		return inventionPurpose;
	}

	public void setInventionPurpose(String inventionPurpose) {
		this.inventionPurpose = inventionPurpose;
	}

	@Lob
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Lob
	public String getAnalysis() {
		return analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}

	@ManyToOne
	public GroupEntity getRecordGroup() {
		return recordGroup;
	}

	public void setRecordGroup(GroupEntity recordGroup) {
		this.recordGroup = recordGroup;
	}

}