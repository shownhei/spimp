/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyResourceDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyResource;

/**
 * EmergencyResource Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyResourceService extends HibernateDataServiceImpl<EmergencyResource, Long> {
	@Autowired
	private EmergencyResourceDAO emergencyResourceDAO;

	@Override
	public HibernateDAO<EmergencyResource, Long> getDAO() {
		return emergencyResourceDAO;
	}
}