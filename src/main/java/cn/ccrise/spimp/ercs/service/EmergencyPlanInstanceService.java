/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyPlanInstanceDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;

/**
 * EmergencyPlanInstance Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyPlanInstanceService extends HibernateDataServiceImpl<EmergencyPlanInstance, Long> {
	@Autowired
	private EmergencyPlanInstanceDAO emergencyPlanInstanceDAO;

	@Override
	public HibernateDAO<EmergencyPlanInstance, Long> getDAO() {
		return emergencyPlanInstanceDAO;
	}
}