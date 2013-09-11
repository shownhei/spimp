/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.RefugeDAO;
import cn.ccrise.spimp.ercs.entity.Refuge;

/**
 * Refuge Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class RefugeService extends HibernateDataServiceImpl<Refuge, Long> {
	@Autowired
	private RefugeDAO refugeDAO;

	@Override
	public HibernateDAO<Refuge, Long> getDAO() {
		return refugeDAO;
	}
}