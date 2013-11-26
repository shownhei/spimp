/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.security.dao.AccountEntityDAO;
import cn.ccrise.spimp.system.entity.Account;

/**
 * Account DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AccountDAO extends AccountEntityDAO<Account> {
}