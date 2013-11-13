/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.electro.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * ElectroRepair。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_electro_repairs")
public class ElectroRepair extends IDEntity {
	private String address;// 检修地点
	private String name;// 检修人名
	private String deviceName;// 设备名称
	private String version;// 设备型号
	private Date startTime;// 开工时间
	private Date endTime;// 完工时间
	private String content;// 检修内容

	public String getAddress() {
		return address;
	}

	public String getContent() {
		return content;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getVersion() {
		return version;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}