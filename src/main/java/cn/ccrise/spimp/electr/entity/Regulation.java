/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.ercs.entity.UploadedFile;
import cn.ccrise.spimp.util.PageFields;

/**
 * 规章制度。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spimp_electr_regulations")
public class Regulation extends IDEntity {
	/**
	 * 文档名称
	 */
	@PageFields(describtion = "文档名称", allowedNull = false, search = true)
	private String title;
	/**
	 * 附件
	 */
	@PageFields(describtion = "附件", allowedNull = false, search = false)
	private UploadedFile attachment;
	/**
	 * 上传时间
	 */
	@PageFields(describtion = "上传时间", allowedNull = true, search = false)
	private Timestamp uploadTime;
	/**
	 * 上传者
	 */
	@PageFields(describtion = "上传者", allowedNull = true, search = false)
	private String uploader;

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public String getTitle() {
		return title;
	}

	public String getUploader() {
		return uploader;
	}

	/**
	 * 上传时间
	 * 
	 * @return
	 */
	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}

}