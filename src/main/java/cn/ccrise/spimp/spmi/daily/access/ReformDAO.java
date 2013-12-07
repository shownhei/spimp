/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Reform DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ReformDAO extends HibernateDAOImpl<Reform, Long> {
}