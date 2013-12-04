/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 奖惩记录。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "electr_regulation_rewards")
public class RegulationReward extends IDEntity {

	/**
	 * 受奖人
	 */
	@PageFields(describtion = "受奖人", allowedNull = false, search = true)
	private String awardedPeople;
	/**
	 * 奖惩日期
	 */
	@PageFields(describtion = "奖惩日期", allowedNull = false, search = true)
	private Date awardedDate;
	/**
	 * 奖惩原因
	 */
	@PageFields(describtion = "奖惩原因", allowedNull = true, search = false)
	private String awardedReason;
	/**
	 * 奖惩类型
	 */
	@PageFields(describtion = "奖惩类型", allowedNull = false, search = true)
	private Integer awardType;
	/**
	 * 奖惩金额
	 */
	@PageFields(describtion = "奖惩金额", allowedNull = true, search = false)
	private Double awardedMoney;

	public String getAwardedPeople() {
		return awardedPeople;
	}

	public void setAwardedPeople(String awardedPeople) {
		this.awardedPeople = awardedPeople;
	}

	public Date getAwardedDate() {
		return awardedDate;
	}

	public void setAwardedDate(Date awardedDate) {
		this.awardedDate = awardedDate;
	}

	public Integer getAwardType() {
		return awardType;
	}

	public void setAwardType(Integer awardType) {
		this.awardType = awardType;
	}

	public String getAwardedReason() {
		return awardedReason;
	}

	public void setAwardedReason(String awardedReason) {
		this.awardedReason = awardedReason;
	}

	public Double getAwardedMoney() {
		return awardedMoney;
	}

	public void setAwardedMoney(Double awardedMoney) {
		this.awardedMoney = awardedMoney;
	}

}