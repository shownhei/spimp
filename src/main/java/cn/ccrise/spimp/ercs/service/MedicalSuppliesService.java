/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.MedicalSuppliesDAO;
import cn.ccrise.spimp.ercs.entity.MedicalSupplies;

/**
 * MedicalSupplies Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class MedicalSuppliesService extends HibernateDataServiceImpl<MedicalSupplies, Long> {
	@Autowired
	private MedicalSuppliesDAO medicalSuppliesDAO;

	@Override
	public HibernateDAO<MedicalSupplies, Long> getDAO() {
		return medicalSuppliesDAO;
	}
}