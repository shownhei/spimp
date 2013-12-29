/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.util.PageFields;

/**
 * 定期检修设置。
 * 
 */
@Entity
@Table(name = "electr_reminder_settings")
public class ReminderSetting extends IDEntity {
	/**
	 * 项目
	 */
	@PageFields(describtion = "项目", columnShow = true, search = true, allowedNull = false, type = "select", selectDataUri = "/system/dictionaries?typeCode=&list=true", selectShowField = "itemName")
	private Dictionary project;
	/**
	 * 到期时间
	 */
	@PageFields(describtion = "到期时间", columnShow = true, search = true, allowedNull = false)
	private Date expirationDate;
	/**
	 * 提前天数
	 */
	@PageFields(describtion = "提前天数", columnShow = true, search = false, allowedNull = false)
	private Integer daysEarly;
	/**
	 * 到期日期
	 */
	@PageFields(describtion = "到期日期", columnShow = true, search = true, allowedNull = false)
	private Date dateEarly;
	/**
	 * 记录日期
	 */
	@PageFields(describtion = "记录日期", columnShow = true, search = true, allowedNull = false)
	private Date recordDate;
	/**
	 * 记录人
	 */
	@PageFields(describtion = "记录人", columnShow = true, search = false, allowedNull = true)
	private Account recordAccount;
	/**
	 * 记录组织
	 */
	@PageFields(describtion = "记录组织", columnShow = false, search = false, allowedNull = true)
	private GroupEntity recordGroup;

	public Date getDateEarly() {
		return dateEarly;
	}

	public Integer getDaysEarly() {
		return daysEarly;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	@ManyToOne
	public Dictionary getProject() {
		return project;
	}

	@ManyToOne
	public Account getRecordAccount() {
		return recordAccount;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	@ManyToOne
	public GroupEntity getRecordGroup() {
		return recordGroup;
	}

	public void setDateEarly(Date dateEarly) {
		this.dateEarly = dateEarly;
	}

	public void setDaysEarly(Integer daysEarly) {
		this.daysEarly = daysEarly;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setProject(Dictionary project) {
		this.project = project;
	}

	public void setRecordAccount(Account recordAccount) {
		this.recordAccount = recordAccount;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public void setRecordGroup(GroupEntity recordGroup) {
		this.recordGroup = recordGroup;
	}
}