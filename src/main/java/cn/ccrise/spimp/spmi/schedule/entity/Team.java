/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 队组管理 Team。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "spmi_schedule_teams")
public class Team extends IDEntity {

	// 队组名称
	@PageFields(describtion = "队组名称", search = true, allowedNull = false)
	private String teamName;

	// 队组类型
	@PageFields(describtion = "队组类型", search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=schedule_team_type&list=true", selectShowField = "itemName")
	private Dictionary teamType;

	// 人数
	@PageFields(describtion = "人数", allowedNull = false)
	private Integer teammates;

	// 排列顺序
	@PageFields(describtion = "排列顺序", allowedNull = false)
	private Integer sortNumber;

	public Integer getSortNumber() {
		return sortNumber;
	}

	public Integer getTeammates() {
		return teammates;
	}

	public String getTeamName() {
		return teamName;
	}

	@ManyToOne
	public Dictionary getTeamType() {
		return teamType;
	}

	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}

	public void setTeammates(Integer teammates) {
		this.teammates = teammates;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setTeamType(Dictionary teamType) {
		this.teamType = teamType;
	}

	@Override
	public String toString() {
		return teamName;
	}
}