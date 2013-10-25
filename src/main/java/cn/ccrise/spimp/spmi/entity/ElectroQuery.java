/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * ElectroQuery。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_electro_queries")
public class ElectroQuery extends IDEntity {
	private String deviceVersion;// 设备型号
	private String electroVersion;// 机电型号
	private String electricity;// 额定电流
	private String voltage;// 额定电压
	private String power;// 额定功率
	private String frequency;// 额定频率
	private String explosion;// 防爆合格证信息
	private String mineSecurity;// 煤安标志信息
	private String phase;// 相数
	private Date rolloutDate;// 出厂日期
	private String rolloutNum;// 出厂编号
	private String size;// 尺寸
	private String phone;// 设备图片

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	public String getElectroVersion() {
		return electroVersion;
	}

	public void setElectroVersion(String electroVersion) {
		this.electroVersion = electroVersion;
	}

	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getExplosion() {
		return explosion;
	}

	public void setExplosion(String explosion) {
		this.explosion = explosion;
	}

	public String getMineSecurity() {
		return mineSecurity;
	}

	public void setMineSecurity(String mineSecurity) {
		this.mineSecurity = mineSecurity;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Date getRolloutDate() {
		return rolloutDate;
	}

	public void setRolloutDate(Date rolloutDate) {
		this.rolloutDate = rolloutDate;
	}

	public String getRolloutNum() {
		return rolloutNum;
	}

	public void setRolloutNum(String rolloutNum) {
		this.rolloutNum = rolloutNum;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Lob
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}