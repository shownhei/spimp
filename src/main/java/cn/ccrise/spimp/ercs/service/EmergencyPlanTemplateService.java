/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyPlanTemplateDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanTemplate;

/**
 * EmergencyPlanTemplate Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyPlanTemplateService extends HibernateDataServiceImpl<EmergencyPlanTemplate, Long> {
	@Autowired
	private EmergencyPlanTemplateDAO emergencyPlanTemplateDAO;

	@Override
	public HibernateDAO<EmergencyPlanTemplate, Long> getDAO() {
		return emergencyPlanTemplateDAO;
	}
}