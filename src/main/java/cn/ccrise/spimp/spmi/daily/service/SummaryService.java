/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.SummaryDAO;
import cn.ccrise.spimp.spmi.daily.entity.Summary;

/**
 * Summary Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class SummaryService extends HibernateDataServiceImpl<Summary, Long> {
	@Autowired
	private SummaryDAO summaryDAO;

	@Override
	public HibernateDAO<Summary, Long> getDAO() {
		return summaryDAO;
	}
}