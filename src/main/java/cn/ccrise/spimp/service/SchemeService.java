/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.access.SchemeDAO;
import cn.ccrise.spimp.entity.Scheme;

/**
 * Scheme Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class SchemeService extends HibernateDataServiceImpl<Scheme, Long> {
	@Autowired
	private SchemeDAO schemeDAO;

	@Override
	public HibernateDAO<Scheme, Long> getDAO() {
		return schemeDAO;
	}
}