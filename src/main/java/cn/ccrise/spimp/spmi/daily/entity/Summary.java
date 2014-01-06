/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.ercs.entity.UploadedFile;

/**
 * Summary。
 * <p>
 * 总结。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_summaries")
public class Summary extends IDEntity {
	private String name; // 文件名
	private String category; // 分类
	private Timestamp uploadTime = new Timestamp(System.currentTimeMillis()); // 上传时间
	private Long uploaderId; // 上传人id

	// 非持久化属性
	private String uploader; // 上传人
	/**
	 * 附件
	 */
	private UploadedFile attachment;

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public String getCategory() {
		return category;
	}

	public String getName() {
		return name;
	}

	@Transient
	public String getUploader() {
		return uploader;
	}

	@Column(nullable = false)
	public Long getUploaderId() {
		return uploaderId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}

	public void setUploaderId(Long uploaderId) {
		this.uploaderId = uploaderId;
	}

	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}
}