/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.ercs.entity.UploadedFile;

/**
 * Training。
 * <p>
 * 培训计划。
 * </p>
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_trainings")
public class Training extends IDEntity {
	private String name; // 计划名称
	private String category; // 分类：月度/年度培训计划
	private String origin; // 来源：内培计划/委外培训计划
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

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
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

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}