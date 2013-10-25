/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.SpeciaListDAO;
import cn.ccrise.spimp.ercs.entity.SpeciaList;

/**
 * SpeciaList Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class SpeciaListService extends HibernateDataServiceImpl<SpeciaList, Long> {
	@Autowired
	private SpeciaListDAO speciaListDAO;

	@Override
	public HibernateDAO<SpeciaList, Long> getDAO() {
		return speciaListDAO;
	}
}