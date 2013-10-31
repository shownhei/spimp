/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 应急机构(应急小组)。
 * 
 */
@Entity
@Table(name = "ercs_response_teams")
public class ResponseTeam extends IDEntity {
	public static final Long ROOT_ID = -1L;
	/**
	 * 机构名称、小组名称
	 */
	private String teamName;
	private Boolean isParent = true;

	/**
	 * 上级id
	 */
	private Long parentId;
	/**
	 * 队组类型
	 */
	private String teamType;

	public Boolean getIsParent() {
		return isParent;
	}

	public Long getParentId() {
		return parentId;
	}

	public String getTeamName() {
		return teamName;
	}

	public String getTeamType() {
		return teamType;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

}