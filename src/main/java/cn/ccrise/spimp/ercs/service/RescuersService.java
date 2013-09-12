/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.RescuersDAO;
import cn.ccrise.spimp.ercs.entity.Rescuers;

/**
 * Rescuers Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class RescuersService extends HibernateDataServiceImpl<Rescuers, Long> {
	@Autowired
	private RescuersDAO rescuersDAO;

	@Override
	public HibernateDAO<Rescuers, Long> getDAO() {
		return rescuersDAO;
	}
}