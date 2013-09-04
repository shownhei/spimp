/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.util;

import java.util.List;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.spimp.entity.Account;

/**
 * 返回数据过滤。
 * <p>
 * 在以下几种情况下需要过滤返回数据：
 * <li>默认情况下，Spring MVC 3 会将POJO的所有属性都序列化，当POJO有外键关联到其他POJO的时候，返回的数据量会很大</li>
 * <li>涉及到系统安全的数据返回时需要过滤，例如用户密码</li>
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public abstract class ResponseDataFilter {
	/**
	 * 过滤账户信息。
	 * 
	 * @param account
	 *            需要过滤的账户
	 * @return 过滤后的账户
	 */
	public static Account filter(Account account) {
		account.setCredential(null);
		account.getGroupEntity().setGroupEntities(null);
		account.getRoleEntity().setResourceEntities(null);
		return account;
	}

	/**
	 * 过滤机构。
	 * 
	 * @param groupEntity
	 * @return 过滤后的机构
	 */
	public static GroupEntity filter(GroupEntity groupEntity) {
		groupEntity.setOrganizationEntity(null);
		groupEntity.setGroupEntities(null);
		return groupEntity;
	}

	/**
	 * 过滤账户列表。
	 * 
	 * @param accounts
	 */
	public static void filter(List<Account> accounts) {
		for (int i = 0; i < accounts.size(); i++) {
			filter(accounts.get(i));
		}
	}

	/**
	 * 过滤资源。
	 * 
	 * @param resourceEntity
	 * @return
	 */
	public static ResourceEntity filter(ResourceEntity resourceEntity) {
		resourceEntity.setResourceEntities(null);
		return resourceEntity;
	}

	/**
	 * 过滤存入Session中的账户信息。
	 * 
	 * @param loginAccount
	 *            需要过滤的账户
	 * @return 过滤后的账户
	 */
	public static Account filterLoginAccount(Account loginAccount) {
		loginAccount.getGroupEntity().setGroupEntities(null);
		return loginAccount;
	}
}
