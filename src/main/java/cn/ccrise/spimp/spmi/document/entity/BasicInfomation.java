/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * BasicInfomation。
 * 保存公司基本情况表
 */
@Entity
@Table(name = "document_basic_infomations")
public class BasicInfomation extends IDEntity {
	@PageFields(describtion = "内容")
	private String basicContent;
	@PageFields(describtion = "更新时间")
	private Timestamp addTime;
	@Lob
	public String getBasicContent() {
		return basicContent;
	}
	public void setBasicContent(String basicContent) {
		this.basicContent = basicContent;
	}
	public Timestamp getAddTime() {
		return addTime;
	}
	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
	
}