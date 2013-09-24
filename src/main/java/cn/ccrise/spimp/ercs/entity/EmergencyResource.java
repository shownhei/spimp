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
 * EmergencyResource。
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
	 * 类别
	 */
	private Dictionary resourceType;
	/**
	 * 所属单位
	 */
	private GroupEntity department;
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

	@ManyToOne
	public GroupEntity getDepartment() {
		return department;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getResourceNo() {
		return resourceNo;
	}

	@ManyToOne
	public Dictionary getResourceType() {
		return resourceType;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public void setDepartment(GroupEntity department) {
		this.department = department;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public void setResourceNo(String resourceNo) {
		this.resourceNo = resourceNo;
	}

	public void setResourceType(Dictionary resourceType) {
		this.resourceType = resourceType;
	}
}