/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.entity;

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
import cn.ccrise.spimp.entity.Dictionary;
import cn.ccrise.spimp.ercs.entity.UploadedFile;

/**
 * Document。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_document_documents")
public class Document extends IDEntity {
	// 文档名称
	private String documentName;
	
	// 工程分类
	private Dictionary projectType;

	// 科室
	private String office;
	
	// 查询关键字
	private String keyWord;

	// 创建时间
	private Timestamp createTime;
	
	// 最后更新时间
	private Timestamp lastModifyTime;
	
	// 创建人
	private String createBy;
	
	// 更新人
	private String updateBy;
	
	// 附件
	private UploadedFile attachment;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	@ManyToOne
	public Dictionary getProjectType() {
		return projectType;
	}

	public void setProjectType(Dictionary projectType) {
		this.projectType = projectType;
	}

	@Column(updatable = false)
	public String getOffice() {
		return office;
	}

	public void setOffice(String office) {
		this.office = office;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(updatable = false)
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@ManyToOne
	public UploadedFile getAttachment() {
		return attachment;
	}

	public void setAttachment(UploadedFile attachment) {
		this.attachment = attachment;
	}

	@Column(updatable = true)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Timestamp lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
}