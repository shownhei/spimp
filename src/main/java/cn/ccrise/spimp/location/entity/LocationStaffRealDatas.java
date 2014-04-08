/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.location.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.ccrise.spimp.location.entity.id.MineIdStaffIdEnterCurTime;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "m_staffrealdatas")
public class LocationStaffRealDatas {
	private MineIdStaffIdEnterCurTime id; // 复合主键
	/**
	 * 煤矿名称
	 */
	private String mineName;

	/**
	 * 区域id
	 */
	private String areaId;
	/**
	 * 基站id
	 */
	private String stationId;

	/**
	 * 状态
	 */
	private Integer state;

	@Column(name = "areaid")
	public String getAreaId() {
		return areaId;
	}

	@EmbeddedId
	public MineIdStaffIdEnterCurTime getId() {
		return id;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setId(MineIdStaffIdEnterCurTime id) {
		this.id = id;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
}
