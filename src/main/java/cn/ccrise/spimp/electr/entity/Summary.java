/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.ercs.entity.UploadedFile;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 每月总结。
 */
@Entity
@Table(name = "electr_summaries")
public class Summary extends IDEntity {
	/**
	 * 总结标题
	 */
	@PageFields(describtion = "材料名称", columnShow = true, search = true, allowedNull = false)
	private String summaryTitle;
	/**
	 * 附件
	 */
	@PageFields(describtion = "附件", columnShow = true, search = false, allowedNull = false)
	private UploadedFile summary;
	/**
	 * 总结分类
	 */
	@PageFields(describtion = "总结分类", columnShow = true, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=summary_type&list=true", selectShowField = "itemName")
	private Dictionary summaryType;
	/**
	 * 上传日期
	 */
	@PageFields(describtion = "上传日期", columnShow = true, search = true, allowedNull = false)
	private Date uploadDate;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", columnShow = false, search = false, allowedNull = true)
	private Timestamp recordTime;

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getSummaryTitle() {
		return summaryTitle;
	}

	public void setSummaryTitle(String summaryTitle) {
		this.summaryTitle = summaryTitle;
	}

	@ManyToOne
	public UploadedFile getSummary() {
		return summary;
	}

	public void setSummary(UploadedFile summary) {
		this.summary = summary;
	}

	@ManyToOne
	public Dictionary getSummaryType() {
		return summaryType;
	}

	public void setSummaryType(Dictionary summaryType) {
		this.summaryType = summaryType;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

}