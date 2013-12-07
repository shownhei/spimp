/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * Plan。
 * <p>
 * 工作安排。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_plans")
public class Plan extends IDEntity {
	@NotBlank
	private String title; // 主题
	private String content; // 内容
	private String category; // 分类
	private Date cutoffDate; // 截止日期
	private String feedback; // 反馈
	private Timestamp createTime = new Timestamp(System.currentTimeMillis()); // 创建时间
	private Timestamp feedbackTime; // 反馈时间
	private String status; // 状态：已指派/已读/已执行/已完成
	private Long createrAccountId; // 创建人id
	private Long executorAccountId; // 执行人id

	// 非持久化属性
	private String creater; // 创建人
	private String executor; // 执行人

	public String getCategory() {
		return category;
	}

	@Lob
	public String getContent() {
		return content;
	}

	@Transient
	public String getCreater() {
		return creater;
	}

	@Column(nullable = false)
	public Long getCreaterAccountId() {
		return createrAccountId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public Date getCutoffDate() {
		return cutoffDate;
	}

	@Transient
	public String getExecutor() {
		return executor;
	}

	@Column(nullable = false)
	public Long getExecutorAccountId() {
		return executorAccountId;
	}

	@Lob
	public String getFeedback() {
		return feedback;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getFeedbackTime() {
		return feedbackTime;
	}

	@Column(nullable = false)
	public String getStatus() {
		return status;
	}

	@Column(nullable = false)
	public String getTitle() {
		return title;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreaterAccountId(Long createrAccountId) {
		this.createrAccountId = createrAccountId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCutoffDate(Date cutoffDate) {
		this.cutoffDate = cutoffDate;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public void setExecutorAccountId(Long executorAccountId) {
		this.executorAccountId = executorAccountId;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setFeedbackTime(Timestamp feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}