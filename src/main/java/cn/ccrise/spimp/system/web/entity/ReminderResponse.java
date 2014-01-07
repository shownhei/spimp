/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.Lists;

/**
 * 提醒响应。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class ReminderResponse {
	private Long count = 0L; // 消息数量
	private List<ReminderMessage> messages = Lists.newLinkedList(); // 提醒消息

	public void addReminderMessage(ReminderMessage reminderMessage) {
		count += reminderMessage.getCount();
		this.messages.add(reminderMessage);
	}

	public Long getCount() {
		return count;
	}

	public List<ReminderMessage> getMessages() {
		return messages;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setMessages(List<ReminderMessage> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
