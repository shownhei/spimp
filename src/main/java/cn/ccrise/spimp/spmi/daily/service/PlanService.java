/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.PlanDAO;
import cn.ccrise.spimp.spmi.daily.entity.Plan;

/**
 * Plan Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class PlanService extends HibernateDataServiceImpl<Plan, Long> {
	@Autowired
	private PlanDAO planDAO;

	@Override
	public HibernateDAO<Plan, Long> getDAO() {
		return planDAO;
	}
}