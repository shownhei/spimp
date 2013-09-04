/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.AccountEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.DigestType;
import cn.ccrise.ikjp.core.security.service.impl.GroupEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.RoleEntityServiceImpl;
import cn.ccrise.spimp.access.AccountDAO;
import cn.ccrise.spimp.entity.Account;

/**
 * Account Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AccountService extends AccountEntityServiceImpl<Account> {
	@Autowired
	private AccountDAO accountDAO;
	@Autowired
	private GroupEntityServiceImpl groupEntityServiceImpl;
	@Autowired
	private RoleEntityServiceImpl roleEntityServiceImpl;

	@Override
	public HibernateDAO<Account, Long> getDAO() {
		return accountDAO;
	}

	/**
	 * 保存用户信息。
	 * 
	 * @param account
	 * @param groupEntityId
	 * @param roleEntityId
	 * @return
	 */
	public boolean save(Account account, Long groupEntityId, Long roleEntityId) {
		account.setLocked(false);
		account.setCreateTime(new Timestamp(System.currentTimeMillis()));
		GroupEntity groupEntity = groupEntityServiceImpl.get(groupEntityId);
		account.setGroupEntity(groupEntity);
		account.setRoleEntity(roleEntityServiceImpl.get(roleEntityId));
		return save(account);
	}

	/**
	 * 更新用户信息。考虑到账户的安全性保障，不直接使用页面传入的值进行更新，而是从数据库中自行取值进行有限的属性更新。如果密码为null，表明不更新密码
	 * 。 在用户信息传给前端时，并没有包含密码，因此在更新时，密码可能为null。在修改密码时，不为null。
	 * 
	 * @param accountInDB
	 * @param account
	 * @param groupEntityId
	 * @param roleEntityId
	 * @return
	 */
	public boolean update(Account accountInDB, Account account, Long groupEntityId, Long roleEntityId) {
		accountInDB.setRealName(account.getRealName());
		accountInDB.setTelephone(account.getTelephone());
		accountInDB.setGroupEntity(groupEntityServiceImpl.get(groupEntityId));
		accountInDB.setRoleEntity(roleEntityServiceImpl.get(roleEntityId));
		accountInDB.setLocked(account.isLocked());

		if (account.getCredential() != null) {
			accountInDB.setCredential(digestCredential(account.getCredential(), DigestType.SHA256));
		}

		return update(accountInDB);
	}
}