/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.ReformDAO;
import cn.ccrise.spimp.spmi.daily.entity.Reform;

/**
 * Reform Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ReformService extends HibernateDataServiceImpl<Reform, Long> {
	@Autowired
	private ReformDAO reformDAO;

	@Override
	public HibernateDAO<Reform, Long> getDAO() {
		return reformDAO;
	}
}