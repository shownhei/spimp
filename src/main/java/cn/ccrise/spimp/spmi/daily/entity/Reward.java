/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Reward。
 * <p>
 * 奖惩记录。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_rewards")
public class Reward extends IDEntity {
	@NotBlank
	private String name; // 奖惩人姓名
	private String groupName; // 奖惩人所属部门
	private String category; // 分类：奖励/惩罚
	private String content; // 奖惩内容
	private String reason; // 理由
	private Date rewardDate; // 奖惩日期
	private String executor; // 授奖人

	public String getCategory() {
		return category;
	}

	public String getContent() {
		return content;
	}

	public String getExecutor() {
		return executor;
	}

	public String getGroupName() {
		return groupName;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	@Lob
	public String getReason() {
		return reason;
	}

	public Date getRewardDate() {
		return rewardDate;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRewardDate(Date rewardDate) {
		this.rewardDate = rewardDate;
	}
}