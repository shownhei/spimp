/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.util.PageFields;

/**
 * 设备检修记录。
 * 
 */
@Entity
@Table(name = "electr_overhauls")
public class Overhaul extends IDEntity {
	/**
	 * 检修日期
	 */
	@PageFields(describtion = "检修日期", columnShow = true, search = true, allowedNull = false)
	private Date overhaulDate;

	/**
	 * 检修位置
	 */
	@PageFields(describtion = "检修位置", columnShow = true, search = true, allowedNull = false)
	private String overhaulPosition;
	/**
	 * 负责人
	 */
	@PageFields(describtion = "负责人", columnShow = true, search = true, allowedNull = false)
	private String chargePersoin;
	/**
	 * 检修人
	 */
	@PageFields(describtion = "材料名称", columnShow = true, search = false, allowedNull = false)
	private String checker;
	/**
	 * 存在问题
	 */
	@PageFields(describtion = "存在问题", columnShow = true, search = false, allowedNull = false)
	private String existProblem;
	/**
	 * 遗留问题
	 */
	@PageFields(describtion = "遗留问题", columnShow = true, search = false, allowedNull = false)
	private String vestigialProblem;
	/**
	 * 记录时间
	 */
	@PageFields(describtion = "记录时间", columnShow = true, search = false)
	private Timestamp recordTime;

	public String getChargePersoin() {
		return chargePersoin;
	}

	public String getChecker() {
		return checker;
	}

	@Lob
	public String getExistProblem() {
		return existProblem;
	}

	public Date getOverhaulDate() {
		return overhaulDate;
	}

	@Lob
	public String getOverhaulPosition() {
		return overhaulPosition;
	}

	@Column(updatable = false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getRecordTime() {
		return recordTime;
	}

	public String getVestigialProblem() {
		return vestigialProblem;
	}

	public void setChargePersoin(String chargePersoin) {
		this.chargePersoin = chargePersoin;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public void setExistProblem(String existProblem) {
		this.existProblem = existProblem;
	}

	public void setOverhaulDate(Date overhaulDate) {
		this.overhaulDate = overhaulDate;
	}

	public void setOverhaulPosition(String overhaulPosition) {
		this.overhaulPosition = overhaulPosition;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}

	public void setVestigialProblem(String vestigialProblem) {
		this.vestigialProblem = vestigialProblem;
	}

}