/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.security.entity.AccountEntity;

/**
 * Account。
 * <p>
 * 账号。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_accounts")
public class Account extends AccountEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 真实姓名
	 */
	@NotBlank
	private String realName;
	/**
	 * 电话
	 */
	private String telephone;

	@Column(nullable = false)
	public String getRealName() {
		return realName;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}