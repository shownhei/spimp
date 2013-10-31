/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.schedule.entity.Record;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Record DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class RecordDAO extends HibernateDAOImpl<Record, Long> {
}