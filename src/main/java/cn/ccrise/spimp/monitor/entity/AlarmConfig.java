/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * AlarmConfig.
 * <p>
 * 监测监控报警提示配置.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "k_alarm_config")
public class AlarmConfig extends IDEntity {
	/**
	 * 参数名称.
	 */
	@NotBlank
	private String name;
	/**
	 * 参数值.
	 */
	@NotBlank
	private String value;
	/**
	 * 关联到账户.
	 */
	private Long accountId;
	/**
	 * 类型.
	 * <p>
	 * basic:基本信息<br>
	 * sensor:瓦斯监测-报警传感器类型<br>
	 * prompt:瓦斯监测-报警提示类型<br>
	 * parameter:瓦斯监测-报警提示参数<br>
	 * alarm:人员定位-报警提示类型<br>
	 */
	private String type;

	public Long getAccountId() {
		return accountId;
	}

	@Column(nullable = false)
	public String getName() {
		return name;
	}

	@Column(nullable = false)
	public String getType() {
		return type;
	}

	@Column(nullable = false)
	public String getValue() {
		return value;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
}