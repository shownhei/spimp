/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.water.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * Water。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_waters")
public class Water extends IDEntity {
	// 时间
	@PageFields(describtion = "时间", allowedNull = false, search = true)
	private Timestamp time;

	// 地点
	@PageFields(describtion = "地点", search = true)
	private String position;

	// 涌出量
	@PageFields(describtion = "涌出量")
	private Double flux;

	// 状况
	@PageFields(describtion = "状况", type = "textarea")
	private String status;

	// 组织处理情况
	@PageFields(describtion = "组织处理情况", type = "textarea")
	private String dealing;

	public String getDealing() {
		return dealing;
	}

	public Double getFlux() {
		return flux;
	}

	public String getPosition() {
		return position;
	}

	public String getStatus() {
		return status;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getTime() {
		return time;
	}

	public void setDealing(String dealing) {
		this.dealing = dealing;
	}

	public void setFlux(Double flux) {
		this.flux = flux;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
}