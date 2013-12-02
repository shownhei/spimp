/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * Car。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "car_cars")
public class Car extends IDEntity {
	public static final Integer CAR_STATUS_NORMAL = 1;
	public static final Integer CAR_STATUS_UNNORMAL = 0;
	/**
	 * 车辆类型
	 */
	@PageFields(describtion = "车辆类型", allowedNull = false, search = true)
	private String carCategory;
	/**
	 * 车辆型号
	 */
	@PageFields(describtion = "车辆型号", allowedNull = false, search = true)
	private String models;
	/**
	 * 车号
	 */
	@PageFields(describtion = "车号", allowedNull = false, search = true)
	private String carNo;

	/**
	 * 车辆状态 1正常 0 停用
	 */
	@PageFields(describtion = "车辆状态", allowedNull = false, search = true)
	private Integer carStatus;
	/**
	 * 排气量
	 */
	@PageFields(describtion = "排气量", allowedNull = true, search = false)
	private String engineSize;
	/**
	 * 发动机号
	 */
	@PageFields(describtion = "发动机号", allowedNull = true, search = false)
	private String engineNumber;
	/**
	 * 购买日期
	 */
	@PageFields(describtion = "购买日期", allowedNull = false, search = false)
	private Date buyDate;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", allowedNull = true, search = false)
	private Timestamp addDateTime;

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddDateTime() {
		return addDateTime;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public String getCarCategory() {
		return carCategory;
	}

	public String getCarNo() {
		return carNo;
	}

	public Integer getCarStatus() {
		return carStatus;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public String getEngineSize() {
		return engineSize;
	}

	public String getModels() {
		return models;
	}

	public void setAddDateTime(Timestamp addDateTime) {
		this.addDateTime = addDateTime;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public void setCarCategory(String carCategory) {
		this.carCategory = carCategory;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public void setCarStatus(Integer carStatus) {
		this.carStatus = carStatus;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public void setEngineSize(String engineSize) {
		this.engineSize = engineSize;
	}

	public void setModels(String models) {
		this.models = models;
	}

}