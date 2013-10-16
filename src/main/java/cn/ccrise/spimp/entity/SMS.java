/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * SMS。
 * <p>
 * 短信发送记录。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "you_smses")
public class SMS extends IDEntity {
	/**
	 * 接收人手机号
	 */
	private String mobileNumber;
	/**
	 * 接收人姓名
	 */
	private String receiveName;
	/**
	 * 接收矿企名称
	 */
	private String mineName;
	/**
	 * 发送人
	 */
	private String sendName;
	/**
	 * 待发时间
	 */
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	private Timestamp readyTime;
	/**
	 * 已发时间
	 */
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	private Timestamp sendTime;
	/**
	 * 发送状态（已发送、未发送）
	 */
	private String status;
	/**
	 * 短信内容
	 */
	private String content;
	/**
	 * 发送次数
	 */
	private Integer count;

	@Lob
	public String getContent() {
		return content;
	}

	public Integer getCount() {
		return count;
	}

	public String getMineName() {
		return mineName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public Timestamp getReadyTime() {
		return readyTime;
	}

	public String getReceiveName() {
		return receiveName;
	}

	public String getSendName() {
		return sendName;
	}

	public Timestamp getSendTime() {
		return sendTime;
	}

	public String getStatus() {
		return status;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public void setCount(final Integer count) {
		this.count = count;
	}

	public void setMineName(final String mineName) {
		this.mineName = mineName;
	}

	public void setMobileNumber(final String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setReadyTime(final Timestamp readyTime) {
		this.readyTime = readyTime;
	}

	public void setReceiveName(final String receiveName) {
		this.receiveName = receiveName;
	}

	public void setSendName(final String sendName) {
		this.sendName = sendName;
	}

	public void setSendTime(final Timestamp sendTime) {
		this.sendTime = sendTime;
	}

	public void setStatus(final String status) {
		this.status = status;
	}
}