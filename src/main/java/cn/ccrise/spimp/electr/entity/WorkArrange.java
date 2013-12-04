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
import cn.ccrise.spimp.util.PageFields;

/**
 * 工作安排。
 * 
 */
@Entity
@Table(name = "electr_work_arranges")
public class WorkArrange extends IDEntity {
	/**
	 * 标题
	 */
	@PageFields(describtion = "标题", columnShow = true, search = true, allowedNull = false)
	private String fileTitle;
	/**
	 * 工作安排
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

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@ManyToOne
	public Account getUploader() {
		return uploader;
	}

	public void setUploader(Account uploader) {
		this.uploader = uploader;
	}

	@ManyToOne
	public GroupEntity getUploadGroup() {
		return uploadGroup;
	}

	public void setUploadGroup(GroupEntity uploadGroup) {
		this.uploadGroup = uploadGroup;
	}

}