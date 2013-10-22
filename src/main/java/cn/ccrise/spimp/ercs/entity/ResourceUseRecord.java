/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * ResourceUseRecord。 应急救援物资使用管理记录表
 */
@Entity
@Table(name = "ercs_resource_use_records")
public class ResourceUseRecord extends IDEntity {
	/**
	 * 对应的物资
	 */
	private EmergencyResource resource;

	/**
	 * 使用时间
	 */
	private Timestamp useTime;
	/**
	 * 使用数量
	 */
	private Integer useAmount;

	/**
	 * 检查维修时间
	 */
	private Timestamp maintenanceTime;

	/**
	 * 更换情况
	 */
	private String replacement;

	/**
	 * 报废情况
	 */
	private String scrapped;
	/**
	 * 备注
	 */
	private String remark;

	public Integer getUseAmount() {
		return useAmount;
	}

	public void setUseAmount(Integer useAmount) {
		this.useAmount = useAmount;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getMaintenanceTime() {
		return maintenanceTime;
	}

	public void setMaintenanceTime(Timestamp maintenanceTime) {
		this.maintenanceTime = maintenanceTime;
	}

	public String getReplacement() {
		return replacement;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	public String getScrapped() {
		return scrapped;
	}

	public void setScrapped(String scrapped) {
		this.scrapped = scrapped;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUseTime() {
		return useTime;
	}

	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	@ManyToOne
	public EmergencyResource getResource() {
		return resource;
	}

	public void setResource(EmergencyResource resource) {
		this.resource = resource;
	}

}