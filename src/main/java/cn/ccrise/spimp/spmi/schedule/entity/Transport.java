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
@Table(name = "schedule_transports")
public class Transport extends IDEntity {
	//	日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date operateDate;
	
	//	煤种
	@PageFields(describtion="煤种", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_coal_type&list=true", selectShowField="itemName")
	private Dictionary coalType;
	
	//	铁路运输车数
	@PageFields(describtion="铁路运输车数")
	private Integer railwayTrans;
	
	//	铁路运输吨数
	@PageFields(describtion="铁路运输吨数")
	private Double railwayTons;
	
	//	铁路运输装车时间
	@PageFields(describtion="铁路装车时间")
	private Date railwayLoadStartTime;
	
	//	铁路运输装完时间
	@PageFields(describtion="铁路装完时间")
	private Date railwayLoadEndTime;
	
	//	铁路运输备注
	@PageFields(describtion="铁路运输备注", type="textarea")
	private String railwayRemark;

	//	公路运输车数
	@PageFields(describtion="公路运输车数")
	private Integer roadTrans;
	
	//	公路运输吨数
	@PageFields(describtion="公路运输吨数")
	private Double roadTons;
	
	//	公路运输外运合计
	@PageFields(describtion="公路外运合计")
	private Double roadTonsTotal;
	
	//	公路运输库存
	@PageFields(describtion="公路运输库存")
	private Double roadStorage;
	
	// 公路运输备注
	@PageFields(describtion="公路运输备注", type="textarea")
	private Double roadRemark;

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	@ManyToOne
	public Dictionary getCoalType() {
		return coalType;
	}

	public void setCoalType(Dictionary coalType) {
		this.coalType = coalType;
	}

	public Integer getRailwayTrans() {
		return railwayTrans;
	}

	public void setRailwayTrans(Integer railwayTrans) {
		this.railwayTrans = railwayTrans;
	}

	public Double getRailwayTons() {
		return railwayTons;
	}

	public void setRailwayTons(Double railwayTons) {
		this.railwayTons = railwayTons;
	}

	public Date getRailwayLoadStartTime() {
		return railwayLoadStartTime;
	}

	public void setRailwayLoadStartTime(Date railwayLoadStartTime) {
		this.railwayLoadStartTime = railwayLoadStartTime;
	}

	public Date getRailwayLoadEndTime() {
		return railwayLoadEndTime;
	}

	public void setRailwayLoadEndTime(Date railwayLoadEndTime) {
		this.railwayLoadEndTime = railwayLoadEndTime;
	}

	public String getRailwayRemark() {
		return railwayRemark;
	}

	public void setRailwayRemark(String railwayRemark) {
		this.railwayRemark = railwayRemark;
	}

	public Integer getRoadTrans() {
		return roadTrans;
	}

	public void setRoadTrans(Integer roadTrans) {
		this.roadTrans = roadTrans;
	}

	public Double getRoadTons() {
		return roadTons;
	}

	public void setRoadTons(Double roadTons) {
		this.roadTons = roadTons;
	}

	public Double getRoadTonsTotal() {
		return roadTonsTotal;
	}

	public void setRoadTonsTotal(Double roadTonsTotal) {
		this.roadTonsTotal = roadTonsTotal;
	}

	public Double getRoadStorage() {
		return roadStorage;
	}

	public void setRoadStorage(Double roadStorage) {
		this.roadStorage = roadStorage;
	}

	public Double getRoadRemark() {
		return roadRemark;
	}

	public void setRoadRemark(Double roadRemark) {
		this.roadRemark = roadRemark;
	}
}