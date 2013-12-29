/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

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
	private String fileUrl; // 上传文件路径

	public String getCategory() {
		return category;
	}

	@Column(nullable = false)
	public String getFileUrl() {
		return fileUrl;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	public String getOrigin() {
		return origin;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}
}