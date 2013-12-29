/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.ercs.entity.UploadedFile;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 图纸管理。
 * 
 */
@Entity
@Table(name = "electr_drawing")
public class Drawing extends IDEntity {
	/**
	 * 标题
	 */
	@PageFields(describtion = "标题", columnShow = true, search = true, allowedNull = false)
	private String fileTitle;
	/**
	 * 
	 */
	@PageFields(describtion = "图纸类型", columnShow = true, search = true, allowedNull = false)
	private Dictionary drawingType;
	/**
	 * 图纸
	 */
	@PageFields(describtion = "工作安排", columnShow = true, search = false, allowedNull = false)
	private UploadedFile attachment;
	/**
	 * 上传日期
	 */
	@PageFields(describtion = "上传日期", columnShow = true, search = true, allowedNull = false)
	private Date uploadDate;
	/**
	 * 上传人
	 */
	@PageFields(describtion = "上传人", columnShow = true, search = false, allowedNull = true)
	private Account uploader;
	/**
	 * 上传组织
	 */
	@PageFields(describtion = "上传组织", columnShow = true, search = false, allowedNull = true)
	private GroupEntity uploadGroup;

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	@ManyToOne
	public Account getUploader() {
		return uploader;
	}

	@ManyToOne
	public GroupEntity getUploadGroup() {
		return uploadGroup;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public void setUploader(Account uploader) {
		this.uploader = uploader;
	}

	public void setUploadGroup(GroupEntity uploadGroup) {
		this.uploadGroup = uploadGroup;
	}

}