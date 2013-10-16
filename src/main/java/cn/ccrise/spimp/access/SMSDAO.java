/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.entity.SMS;

/**
 * SMS DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class SMSDAO extends HibernateDAOImpl<SMS, Long> {
}