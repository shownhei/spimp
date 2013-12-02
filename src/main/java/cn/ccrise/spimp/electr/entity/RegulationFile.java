/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Column;
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
 * 制度文件管理。
 * 
 */
@Entity
@Table(name = "electr_regulation_files")
public class RegulationFile extends IDEntity {
	/**
	 * 文档名称
	 */
	@PageFields(describtion = "文档名称", columnShow = true, search = true, allowedNull = false)
	private String fileTitle;
	/**
	 * 制度类型
	 */
	@PageFields(describtion = "制度类型", columnShow = true, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=regulationfile_type&list=true", selectShowField = "itemName")
	private Dictionary fileType;
	/**
	 * 附件
	 */
	@PageFields(describtion = "附件", columnShow = true, search = false, allowedNull = false)
	private UploadedFile attachment;
	/**
	 * 上传日期
	 */
	@PageFields(describtion = "上传日期", columnShow = true, search = true, allowedNull = false)
	private Date uploadDate;
	/**
	 * 上传人
	 */
	@PageFields(describtion = "上传日期", columnShow = true, search = false, allowedNull = true)
	private Account uploader;
	/**
	 * 上传组织
	 */
	@PageFields(describtion = "上传日期", columnShow = true, search = false, allowedNull = true)
	private GroupEntity uploadGroup;

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	@ManyToOne
	public Dictionary getFileType() {
		return fileType;
	}

	public void setFileType(Dictionary fileType) {
		this.fileType = fileType;
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

	@Column(updatable = false)
	@ManyToOne
	public Account getUploader() {
		return uploader;
	}

	public void setUploader(Account uploader) {
		this.uploader = uploader;
	}

	@Column(updatable = false)
	@ManyToOne
	public GroupEntity getUploadGroup() {
		return uploadGroup;
	}

	public void setUploadGroup(GroupEntity uploadGroup) {
		this.uploadGroup = uploadGroup;
	}

}