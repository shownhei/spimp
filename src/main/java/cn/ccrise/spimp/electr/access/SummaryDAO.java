/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.Summary;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Summary DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class SummaryDAO extends HibernateDAOImpl<Summary, Long> {
}