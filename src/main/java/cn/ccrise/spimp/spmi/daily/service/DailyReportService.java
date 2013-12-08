/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.DailyReportDAO;
import cn.ccrise.spimp.spmi.daily.entity.DailyReport;

/**
 * DailyReport Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class DailyReportService extends HibernateDataServiceImpl<DailyReport, Long> {
	@Autowired
	private DailyReportDAO dailyReportDAO;

	@Override
	public HibernateDAO<DailyReport, Long> getDAO() {
		return dailyReportDAO;
	}
}