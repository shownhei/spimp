/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * Refuge。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "ercs_refuges")
public class Refuge extends IDEntity {
	/**
	 * 种类
	 */
	private Dictionary refugeType;
	/**
	 * 名称
	 */
	private String refugeName;
	/**
	 * 数量
	 */
	private Long quantity;
	/**
	 * 位置
	 */
	private String position;
	/**
	 * 基本情况
	 */
	private String basicInfomation;
	/**
	 * 面积
	 */
	private String refugeArea;
	/**
	 * 可容纳人数
	 */
	private String capacity;
	/**
	 * 基础设施
	 */
	private String infrastructure;
	/**
	 * 防护功能
	 */
	private String protection;
	/**
	 * 隶属单位
	 */
	private GroupEntity department;
	/**
	 * 管理人
	 */
	private String manager;
	/**
	 * 联系方式
	 */
	private String telepone;
	private Timestamp addTime;

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	public String getBasicInfomation() {
		return basicInfomation;
	}

	public String getCapacity() {
		return capacity;
	}

	@ManyToOne
	public GroupEntity getDepartment() {
		return department;
	}

	public String getInfrastructure() {
		return infrastructure;
	}

	public String getManager() {
		return manager;
	}

	public String getPosition() {
		return position;
	}

	public String getProtection() {
		return protection;
	}

	public Long getQuantity() {
		return quantity;
	}

	public String getRefugeArea() {
		return refugeArea;
	}

	public String getRefugeName() {
		return refugeName;
	}

	@ManyToOne
	public Dictionary getRefugeType() {
		return refugeType;
	}

	public String getTelepone() {
		return telepone;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setBasicInfomation(String basicInfomation) {
		this.basicInfomation = basicInfomation;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public void setDepartment(GroupEntity department) {
		this.department = department;
	}

	public void setInfrastructure(String infrastructure) {
		this.infrastructure = infrastructure;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setProtection(String protection) {
		this.protection = protection;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public void setRefugeArea(String refugeArea) {
		this.refugeArea = refugeArea;
	}

	public void setRefugeName(String refugeName) {
		this.refugeName = refugeName;
	}

	public void setRefugeType(Dictionary refugeType) {
		this.refugeType = refugeType;
	}

	public void setTelepone(String telepone) {
		this.telepone = telepone;
	}
}