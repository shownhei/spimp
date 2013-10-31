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
 * 矿井原煤产量
 * Output。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "schedule_outputs")
public class Output extends IDEntity {
	
	//	开采日期
	@PageFields(describtion="开采日期", allowedNull=false, search=true)
	private Date digDate;
	
	//	班次
	@PageFields(describtion="班次", search=true, allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_duty&list=true", selectShowField="itemName")
	private Dictionary duty;
	
	//	队组
	@PageFields(describtion="队组", allowedNull=false, type="select", selectDataUri="/spmi/schedule/teams?list=true", selectShowField="teamName")
	private Team team;
	
	//	开采方式
	@PageFields(describtion="开采方式", allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_exploit_type&list=true", selectShowField="itemName")
	private Dictionary exploitType;
	
	//	工作面
	@PageFields(describtion="工作面", allowedNull=false, type="select", selectDataUri="/system/dictionaries?typeCode=schedule_working_face&list=true", selectShowField="itemName")
	private Dictionary workingFace;
	
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
	
	//	开机时间
	@PageFields(describtion="开机时间")
	private Date powerOnTime;

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
	public Dictionary getExploitType() {
		return exploitType;
	}

	public void setExploitType(Dictionary exploitType) {
		this.exploitType = exploitType;
	}

	@ManyToOne
	public Dictionary getWorkingFace() {
		return workingFace;
	}

	public void setWorkingFace(Dictionary workingFace) {
		this.workingFace = workingFace;
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

	public Date getPowerOnTime() {
		return powerOnTime;
	}

	public void setPowerOnTime(Date powerOnTime) {
		this.powerOnTime = powerOnTime;
	}
}