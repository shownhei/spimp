/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * Transport。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_schedule_transports")
public class Transport extends IDEntity {
	// 日期
	@PageFields(describtion = "日期", allowedNull = false, search = true)
	private Date operateDate;

	// 煤种
	@PageFields(describtion = "煤种", search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=schedule_coal_type&list=true", selectShowField = "itemName")
	private Dictionary coalType;

	// 铁路运输车数
	@PageFields(describtion = "铁路运输车数")
	private Integer railwayTrans;

	// 铁路运输吨数
	@PageFields(describtion = "铁路运输吨数")
	private Double railwayTons;

	// 铁路运输装车时间
	@PageFields(describtion = "铁路装车时间", columnShow = false)
	private Date railwayLoadStartTime;

	// 铁路运输装完时间
	@PageFields(describtion = "铁路装完时间", columnShow = false)
	private Date railwayLoadEndTime;

	// 铁路运输备注
	@PageFields(describtion = "铁路运输备注", type = "textarea", columnShow = false)
	private String railwayRemark;

	// 公路运输车数
	@PageFields(describtion = "公路运输车数")
	private Integer roadTrans;

	// 公路运输吨数
	@PageFields(describtion = "公路运输吨数")
	private Double roadTons;

	// 公路运输外运合计
	@PageFields(describtion = "公路外运合计")
	private Double roadTonsTotal;

	// 公路运输库存
	@PageFields(describtion = "公路运输库存")
	private Double roadStorage;

	// 公路运输备注
	@PageFields(describtion = "公路运输备注", type = "textarea", columnShow = false)
	private String roadRemark;

	@ManyToOne
	public Dictionary getCoalType() {
		return coalType;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public Date getRailwayLoadEndTime() {
		return railwayLoadEndTime;
	}

	public Date getRailwayLoadStartTime() {
		return railwayLoadStartTime;
	}

	public String getRailwayRemark() {
		return railwayRemark;
	}

	public Double getRailwayTons() {
		return railwayTons;
	}

	public Integer getRailwayTrans() {
		return railwayTrans;
	}

	public Double getRoadStorage() {
		return roadStorage;
	}

	public Double getRoadTons() {
		return roadTons;
	}

	public Double getRoadTonsTotal() {
		return roadTonsTotal;
	}

	public Integer getRoadTrans() {
		return roadTrans;
	}

	public void setCoalType(Dictionary coalType) {
		this.coalType = coalType;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public void setRailwayLoadEndTime(Date railwayLoadEndTime) {
		this.railwayLoadEndTime = railwayLoadEndTime;
	}

	public void setRailwayLoadStartTime(Date railwayLoadStartTime) {
		this.railwayLoadStartTime = railwayLoadStartTime;
	}

	public void setRailwayRemark(String railwayRemark) {
		this.railwayRemark = railwayRemark;
	}

	public void setRailwayTons(Double railwayTons) {
		this.railwayTons = railwayTons;
	}

	public void setRailwayTrans(Integer railwayTrans) {
		this.railwayTrans = railwayTrans;
	}

	public void setRoadStorage(Double roadStorage) {
		this.roadStorage = roadStorage;
	}

	public void setRoadTons(Double roadTons) {
		this.roadTons = roadTons;
	}

	public void setRoadTonsTotal(Double roadTonsTotal) {
		this.roadTonsTotal = roadTonsTotal;
	}

	public void setRoadTrans(Integer roadTrans) {
		this.roadTrans = roadTrans;
	}

	public String getRoadRemark() {
		return roadRemark;
	}

	public void setRoadRemark(String roadRemark) {
		this.roadRemark = roadRemark;
	}
}