/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * Accident。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_accidents")
public class Accident extends IDEntity {
	@NotBlank
	private String content; // 内容
	private String location; // 地点
	private String scheme; // 处理方案
	private Timestamp occurrenceTime; // 发生时间
	private String principal; // 责任人

	@Column(nullable = false)
	@Lob
	public String getContent() {
		return content;
	}

	public String getLocation() {
		return location;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getOccurrenceTime() {
		return occurrenceTime;
	}

	public String getPrincipal() {
		return principal;
	}

	@Lob
	public String getScheme() {
		return scheme;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setOccurrenceTime(Timestamp occurrenceTime) {
		this.occurrenceTime = occurrenceTime;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}
}