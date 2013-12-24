/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 小改小革相关图片。
 * 
 */
@Entity
@Table(name = "electr_innovation_images")
public class InnovationImage extends IDEntity {
	private String simpleName;
	private String imagePath;
	private Long innovationId;

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getInnovationId() {
		return innovationId;
	}

	public void setInnovationId(Long innovationId) {
		this.innovationId = innovationId;
	}

}