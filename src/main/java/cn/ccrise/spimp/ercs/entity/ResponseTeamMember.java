/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * ResponseTeamMember。
 * 
 */
@Entity
@Table(name = "ercs_response_team_members")
public class ResponseTeamMember extends IDEntity {
	/**
	 * 普通工成员
	 */
	public static final String MEMBER_TYPE_NORMAL = "normal";
	/**
	 * 专家成员
	 */
	public static final String MEMBER_TYPE_EXPERT = "expert";
	/**
	 * 成员等级:总指挥（1）、副总指挥（2）、普通（3）
	 */
	private Integer memberLevel;

	/**
	 * 人员类型：专家（normal）、普通（expert）
	 */
	private String memberType;
	/**
	 * 普通成员
	 */
	private Rescuers normalMember;
	/**
	 * 专家成员
	 */
	private SpeciaList expertMember;
	/**
	 * 所属队组
	 */
	private ResponseTeam team;

	@ManyToOne
	public SpeciaList getExpertMember() {
		return expertMember;
	}

	/**
	 * 成员等级:总指挥（1）、副总指挥（2）、普通（3）
	 */
	public Integer getMemberLevel() {
		return memberLevel;
	}

	public String getMemberType() {
		return memberType;
	}

	@ManyToOne
	public Rescuers getNormalMember() {
		return normalMember;
	}

	@ManyToOne
	public ResponseTeam getTeam() {
		return team;
	}

	public void setExpertMember(SpeciaList expertMember) {
		this.expertMember = expertMember;
	}

	/**
	 * 成员等级:总指挥（1）、副总指挥（2）、普通（3）
	 */
	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public void setMemberType(String memberType) {
		this.memberType = memberType;
	}

	public void setNormalMember(Rescuers normalMember) {
		this.normalMember = normalMember;
	}

	public void setTeam(ResponseTeam team) {
		this.team = team;
	}

}