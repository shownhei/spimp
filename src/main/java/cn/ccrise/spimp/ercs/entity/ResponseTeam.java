/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 应急机构(应急小组)。
 * 
 */
@Entity
@Table(name = "ercs_response_teams")
public class ResponseTeam extends IDEntity {
	/**
	 * 机构名称、小组名称
	 */
	private String teamName;
	/**
	 * 总指挥
	 */
	private String commander;
	/**
	 * 副总指挥
	 */
	private String deputyCommander;

	/**
	 * 下属成员
	 */
	private String members;
	/**
	 * 事故响应级别
	 */
	private Dictionary responseLevel;

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getCommander() {
		return commander;
	}

	public void setCommander(String commander) {
		this.commander = commander;
	}

	public String getDeputyCommander() {
		return deputyCommander;
	}

	public void setDeputyCommander(String deputyCommander) {
		this.deputyCommander = deputyCommander;
	}

	public String getMembers() {
		return members;
	}

	public void setMembers(String members) {
		this.members = members;
	}

	/**
	 * 
	 * @return
	 */
	@ManyToOne
	public Dictionary getResponseLevel() {
		return responseLevel;
	}

	public void setResponseLevel(Dictionary responseLevel) {
		this.responseLevel = responseLevel;
	}

}