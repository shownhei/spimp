/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.spimp.util.PageFields;

/**
 * 班组文化
 * 班组内经常举行一些考评、学习、活动等班组活动，
 */
@Entity
@Table(name = "electr_team_cultures")
public class TeamCulture extends IDEntity {
	@PageFields(describtion = "主题", allowedNull = false, search = true)
	private String topic;
	@PageFields(describtion = "内容", allowedNull = false, search = false)
	private String content;
	@PageFields(describtion = "班组", allowedNull = false, search = true)
	private GroupEntity groupEntity;
	@PageFields(describtion = "日期", allowedNull = true, search = true)
	private Date recordDate;
	@PageFields(describtion = "附件", allowedNull = true, search = false)
	private String attachment;
	@PageFields(describtion = "备注", allowedNull = true, search = false)
	private String remark;
	@PageFields(describtion = "参与人员", allowedNull = false, search = false)
	private String participants;
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	@Lob
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@ManyToOne
	public GroupEntity getGroupEntity() {
		return groupEntity;
	}
	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getParticipants() {
		return participants;
	}
	public void setParticipants(String participants) {
		this.participants = participants;
	}
	
	

}