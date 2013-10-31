/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 重点工作 Focus。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_instruction_focus")
public class Focus extends IDEntity {

	// 工作名称
	@PageFields(describtion = "工作名称", allowedNull = false, search = true)
	private String name;

	// 开始时间
	@PageFields(describtion = "开始时间", search = true)
	private Date startTime;

	// 结束时间
	@PageFields(describtion = "结束时间")
	private Date endTime;

	// 地点
	@PageFields(describtion = "地点", allowedNull = false)
	private String positon;

	// 现场负责人
	@PageFields(describtion = "现场负责人")
	private String responser;

	// 工作人员
	@PageFields(describtion = "工作人员")
	private String workers;

	// 工作进度
	@PageFields(describtion = "工作进度")
	private String process;

	// 工作总结情况
	@PageFields(describtion = "工作总结情况")
	private String summary;

	// 工作简述
	@PageFields(describtion = "工作简述", allowedNull = false, type = "textarea")
	private String description;

	// 记录人
	@PageFields(describtion = "记录人", allowedNull = false, search = true)
	private String recorder;

	public String getDescription() {
		return description;
	}

	public Date getEndTime() {
		return endTime;
	}

	public String getName() {
		return name;
	}

	public String getPositon() {
		return positon;
	}

	public String getProcess() {
		return process;
	}

	public String getRecorder() {
		return recorder;
	}

	public String getResponser() {
		return responser;
	}

	public Date getStartTime() {
		return startTime;
	}

	public String getSummary() {
		return summary;
	}

	public String getWorkers() {
		return workers;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPositon(String positon) {
		this.positon = positon;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}

	public void setResponser(String responser) {
		this.responser = responser;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public void setWorkers(String workers) {
		this.workers = workers;
	}
}