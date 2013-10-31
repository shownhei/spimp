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
 * Dig。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "schedule_digs")
public class Dig extends IDEntity {
//	开采日期
	@PageFields(describtion="日期", allowedNull=false, search=true)
	private Date digDate;
	
	//	班次
	@PageFields(describtion="班次", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField="itemName")
	private Dictionary duty;
	
	//	队组
	@PageFields(describtion="队组", search=true, allowedNull=false, type="select", selectDataUri="/spmi/schedule/teams?list=true", selectShowField="teamName")
	private Team team;
	
	//	巷道类型
	@PageFields(describtion="巷道类型", allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_tunnel_type&list=true", selectShowField="itemName")
	private Dictionary tunnelType;
	
	//	工作地点
	@PageFields(describtion="工作地点", allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_working_place&list=true", selectShowField="itemName")
	private Dictionary workingPlace;
	
	//	跟班队干
	@PageFields(describtion="跟班队干", allowedNull=false)
	private String teamLeader;
	
	//	当班班长
	@PageFields(describtion="当班班长", allowedNull=false)
	private String monitor;
	
	//	安全员
	@PageFields(describtion="安全员")
	private String safeChecker;
	
	//	计划出勤人数
	@PageFields(describtion="计划出勤人数")
	private Integer planMen;
	
	//	实际出勤人数
	@PageFields(describtion="实际出勤人数")
	private Integer infactMen;
	
	//	产量计划吨数
	@PageFields(describtion="产量计划吨数")
	private Double planTons;
	
	//	产量实际吨数
	@PageFields(describtion="产量实际吨数")
	private Double infactTons;

	public Date getDigDate() {
		return digDate;
	}

	public void setDigDate(Date digDate) {
		this.digDate = digDate;
	}

	@ManyToOne
	public Dictionary getDuty() {
		return duty;
	}

	public void setDuty(Dictionary duty) {
		this.duty = duty;
	}

	@ManyToOne
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne
	public Dictionary getWorkingPlace() {
		return workingPlace;
	}

	public void setWorkingPlace(Dictionary workingPlace) {
		this.workingPlace = workingPlace;
	}

	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getSafeChecker() {
		return safeChecker;
	}

	public void setSafeChecker(String safeChecker) {
		this.safeChecker = safeChecker;
	}

	public Integer getPlanMen() {
		return planMen;
	}

	public void setPlanMen(Integer planMen) {
		this.planMen = planMen;
	}

	public Integer getInfactMen() {
		return infactMen;
	}

	public void setInfactMen(Integer infactMen) {
		this.infactMen = infactMen;
	}

	public Double getPlanTons() {
		return planTons;
	}

	public void setPlanTons(Double planTons) {
		this.planTons = planTons;
	}

	public Double getInfactTons() {
		return infactTons;
	}

	public void setInfactTons(Double infactTons) {
		this.infactTons = infactTons;
	}

	@ManyToOne
	public Dictionary getTunnelType() {
		return tunnelType;
	}

	public void setTunnelType(Dictionary tunnelType) {
		this.tunnelType = tunnelType;
	}

}