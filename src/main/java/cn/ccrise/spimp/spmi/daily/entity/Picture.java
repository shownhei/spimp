/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * Picture。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_pictures")
public class Picture extends IDEntity {
	private String name;
	private Timestamp uploadTime = new Timestamp(System.currentTimeMillis()); // 上传时间

	private Long uploaderId; // 上传人id
	/**
	 * 附件
	 */
	private String attachment;
	private String uploader; // 上传人
	private Long groupId;// 所属机构ID

	public String getAttachment() {
		return attachment;
	}

	public Long getGroupId() {
		return groupId;
	}

	public String getName() {
		return name;
	}

	public String getUploader() {
		return uploader;
	}

	public Long getUploaderId() {
		return uploaderId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUploadTime() {
		return uploadTime;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
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