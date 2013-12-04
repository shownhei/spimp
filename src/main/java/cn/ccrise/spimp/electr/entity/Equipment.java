/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cn.ccrise.ikjp.core.entity.IDEntity;

import com.google.common.collect.Lists;

/**
 * Equipment。
 * <p>
 * 设备。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "electr_equipments")
public class Equipment extends IDEntity {
	private String pictureURL; // 图片路径
	private String specificationURL; // 说明书路径
	private List<Accessory> accessories = Lists.newArrayList(); // 配件

	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<Accessory> getAccessories() {
		return accessories;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public String getSpecificationURL() {
		return specificationURL;
	}

	public void setAccessories(List<Accessory> accessories) {
		this.accessories = accessories;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public void setSpecificationURL(String specificationURL) {
		this.specificationURL = specificationURL;
	}
}