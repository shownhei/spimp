/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 提醒消息。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class ReminderMessage {
	private String message; // 消息
	private String link; // 链接
	private Long count; // 消息数量
	private String category; // 分类

	public ReminderMessage() {
	}

	public ReminderMessage(String message, String link, Long count, String category) {
		this.message = message;
		this.link = link;
		this.count = count;
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public Long getCount() {
		return count;
	}

	public String getLink() {
		return link;
	}

	public String getMessage() {
		return message;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
