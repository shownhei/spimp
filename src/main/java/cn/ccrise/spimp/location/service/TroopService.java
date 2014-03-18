/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.TroopDAO;
import cn.ccrise.spimp.location.entity.Troop;

/**
 * Troop Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class TroopService extends HibernateDataServiceImpl<Troop, Long> {
	@Autowired
	private TroopDAO troopDAO;

	@Override
	public HibernateDAO<Troop, Long> getDAO() {
		return troopDAO;
	}
}