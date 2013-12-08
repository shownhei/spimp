/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.DailyReport;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * DailyReport DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class DailyReportDAO extends HibernateDAOImpl<DailyReport, Long> {
}