/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.KNodeDAO;
import cn.ccrise.spimp.ercs.entity.KNode;

/**
 * KNode Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class KNodeService extends HibernateDataServiceImpl<KNode, Long> {
	@Autowired
	private KNodeDAO kNodeDAO;

	@Override
	public HibernateDAO<KNode, Long> getDAO() {
		return kNodeDAO;
	}
}