/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

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

/**
 * EmergencyResource。应急资源
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_emergency_resources")
public class EmergencyResource extends IDEntity {
	/**
	 * 名称
	 */
	private String resourceName;
	/**
	 * 编号
	 */
	private String resourceNo;
	/**
	 * 数量
	 */
	private Integer amount;
	/**
	 * 功能 作用
	 */
	private String function;
	/**
	 * 型号
	 */
	private String model;
	/**
	 * 原产地
	 */
	private String origin;
	/**
	 * 购买时间
	 */
	private Date butTime;
	/**
	 * 有效使用期限
	 */
	private String expiration;
	/**
	 * 存放位置
	 */
	private String location;
	/**
	 * 管理人员
	 */
	private String manager;

	/**
	 * 联系方式
	 */
	private String telephone;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 录入时间
	 */
	private Timestamp addTime;

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	public Integer getAmount() {
		return amount;
	}

	public Date getButTime() {
		return butTime;
	}

	public String getExpiration() {
		return expiration;
	}

	@Column(name = "function_role")
	public String getFunction() {
		return function;
	}

	public String getLocation() {
		return location;
	}

	public String getManager() {
		return manager;
	}

	public String getModel() {
		return model;
	}

	public String getOrigin() {
		return origin;
	}

	public String getRemark() {
		return remark;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceNo() {
		return resourceNo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public void setButTime(Date butTime) {
		this.butTime = butTime;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceNo(String resourceNo) {
		this.resourceNo = resourceNo;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}