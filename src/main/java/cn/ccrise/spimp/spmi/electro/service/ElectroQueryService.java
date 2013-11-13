/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.electro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.electro.access.ElectroQueryDAO;
import cn.ccrise.spimp.spmi.electro.entity.ElectroQuery;

/**
 * ElectroQuery Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ElectroQueryService extends HibernateDataServiceImpl<ElectroQuery, Long> {
	@Autowired
	private ElectroQueryDAO electroQueryDAO;

	@Override
	public HibernateDAO<ElectroQuery, Long> getDAO() {
		return electroQueryDAO;
	}
}