/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * Reform。
 * <p>
 * 整改通知单。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_reforms")
public class Reform extends IDEntity {
	public enum ReformStatus {
		已下发, 已指派, 已执行, 已审核
	}

	// 业务属性
	private String number; // 编号：自动生成，机构编号+检查日期+序号
	@NotBlank
	private String issue; // 存在安全问题或隐患
	@NotNull
	private Date deadlineDate; // 整改期限
	@NotNull
	private Date checkDate; // 检查日期
	private String standard; // 整改标准或要求
	private String measure; // 整改措施
	private String checker; // 检查人员
	private Date doneDate; // 完成日期
	private Timestamp createTime = new Timestamp(System.currentTimeMillis()); // 创建时间
	private Long createrId; // 创建人id
	private ReformStatus status; // 状态：已下发/已指派/已执行/已完成
	private String feedback; // 审核意见
	private Timestamp feedbackTime; // 审核时间

	// 非业务属性
	@NotNull
	private Long principalId; // 整改责任人id
	@NotNull
	private Long sendGroupId; // 隐患下发部门id
	@NotNull
	private Long testGroupId; // 被检单位id
	private Long planId; // 关联的工作安排id

	// 非持久化属性
	private String sendGroup; // 隐患下发部门
	private String testGroup; // 被检单位
	private String principal; // 整改责任人
	private String creater; // 创建人

	@Column(nullable = false)
	public Date getCheckDate() {
		return checkDate;
	}

	public String getChecker() {
		return checker;
	}

	@Transient
	public String getCreater() {
		return creater;
	}

	@Column(nullable = false)
	public Long getCreaterId() {
		return createrId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(updatable = false)
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column(nullable = false)
	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public Date getDoneDate() {
		return doneDate;
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
	@Lob
	public String getIssue() {
		return issue;
	}

	@Lob
	public String getMeasure() {
		return measure;
	}

	@Column(nullable = false)
	public String getNumber() {
		return number;
	}

	@Column(nullable = false, unique = true)
	public Long getPlanId() {
		return planId;
	}

	@Transient
	public String getPrincipal() {
		return principal;
	}

	@Column(nullable = false)
	public Long getPrincipalId() {
		return principalId;
	}

	@Transient
	public String getSendGroup() {
		return sendGroup;
	}

	@Column(nullable = false)
	public Long getSendGroupId() {
		return sendGroupId;
	}

	public String getStandard() {
		return standard;
	}

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public ReformStatus getStatus() {
		return status;
	}

	@Transient
	public String getTestGroup() {
		return testGroup;
	}

	@Column(nullable = false)
	public Long getTestGroupId() {
		return testGroupId;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public void setDoneDate(Date doneDate) {
		this.doneDate = doneDate;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public void setFeedbackTime(Timestamp feedbackTime) {
		this.feedbackTime = feedbackTime;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setPrincipalId(Long principalId) {
		this.principalId = principalId;
	}

	public void setSendGroup(String sendGroup) {
		this.sendGroup = sendGroup;
	}

	public void setSendGroupId(Long sendGroupId) {
		this.sendGroupId = sendGroupId;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public void setStatus(ReformStatus status) {
		this.status = status;
	}

	public void setTestGroup(String testGroup) {
		this.testGroup = testGroup;
	}

	public void setTestGroupId(Long testGroupId) {
		this.testGroupId = testGroupId;
	}
}