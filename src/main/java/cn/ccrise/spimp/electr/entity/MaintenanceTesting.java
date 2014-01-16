/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;
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
import cn.ccrise.spimp.util.PageFields;

/**
 * MaintenanceTesting。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "electr_maintenance_testings")
public class MaintenanceTesting extends IDEntity {
	/**
	 * 维修日期
	 */
	@PageFields(describtion = "维修日期", allowedNull = false, search = true)
	private Date maintenanceDate;
	/**
	 * 维修车辆
	 */
	@PageFields(describtion = "维修车辆", allowedNull = false, search = true, type = "select", selectDataUri = "/spmi/car/carslist", selectShowField = "carNo")
	private Car car;
	/**
	 * 故障表现/原因
	 */
	@PageFields(describtion = "故障表现/原因", allowedNull = false, search = false)
	private String cause;
	/**
	 * 处理方法
	 */
	@PageFields(describtion = "处理方法", allowedNull = false, search = false)
	private String treatment;
	/**
	 * 备注
	 */
	@PageFields(describtion = "备注", allowedNull = true, search = false)
	private String remark;
	/**
	 * 维修工
	 */
	@PageFields(describtion = "维修工", allowedNull = false, search = true)
	private String miantainer;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", allowedNull = true, search = false)
	private Timestamp recordDateTime;
	/**
	 * 单位
	 */
	@PageFields(describtion = "单位", allowedNull = false, search = true)
	private GroupEntity maintenanceGroup;

	@ManyToOne
	public Car getCar() {
		return car;
	}

	public String getCause() {
		return cause;
	}

	public Date getMaintenanceDate() {
		return maintenanceDate;
	}

	@ManyToOne
	public GroupEntity getMaintenanceGroup() {
		return maintenanceGroup;
	}

	public String getMiantainer() {
		return miantainer;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getRecordDateTime() {
		return recordDateTime;
	}

	public String getRemark() {
		return remark;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public void setMaintenanceDate(Date maintenanceDate) {
		this.maintenanceDate = maintenanceDate;
	}

	public void setMaintenanceGroup(GroupEntity maintenanceGroup) {
		this.maintenanceGroup = maintenanceGroup;
	}

	public void setMiantainer(String miantainer) {
		this.miantainer = miantainer;
	}

	public void setRecordDateTime(Timestamp recordDateTime) {
		this.recordDateTime = recordDateTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
}