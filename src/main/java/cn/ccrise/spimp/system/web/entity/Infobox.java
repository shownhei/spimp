/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class Infobox {
	private String color; // 颜色
	private String icon; // 图标
	private String value; // 值
	private String content; // 内容
	private String link; // 链接

	public Infobox() {
	}

	public Infobox(String color, String icon, String value, String content, String link) {
		this.color = color;
		this.icon = icon;
		this.value = value;
		this.content = content;
		this.link = link;
	}

	public String getColor() {
		return color;
	}

	public String getContent() {
		return content;
	}

	public String getIcon() {
		return icon;
	}

	public String getLink() {
		return link;
	}

	public String getValue() {
		return value;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
