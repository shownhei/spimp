/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.schedule.entity.Report;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Report DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class ReportDAO extends HibernateDAOImpl<Report, Long> {
}