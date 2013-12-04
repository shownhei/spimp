/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.electr.access.AccessoryDAO;
import cn.ccrise.spimp.electr.entity.Accessory;

/**
 * Accessory Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AccessoryService extends HibernateDataServiceImpl<Accessory, Long> {
	@Autowired
	private AccessoryDAO accessoryDAO;

	@Override
	public HibernateDAO<Accessory, Long> getDAO() {
		return accessoryDAO;
	}
}