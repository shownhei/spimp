/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.system.access.AlterationDAO;
import cn.ccrise.spimp.system.entity.Alteration;

/**
 * Alteration Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AlterationService extends HibernateDataServiceImpl<Alteration, Long> {
	@Autowired
	private AlterationDAO alterationDAO;

	@Override
	public HibernateDAO<Alteration, Long> getDAO() {
		return alterationDAO;
	}
}