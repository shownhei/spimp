/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.access.ElectroRepairDAO;
import cn.ccrise.spimp.spmi.entity.ElectroRepair;

/**
 * ElectroRepair Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ElectroRepairService extends HibernateDataServiceImpl<ElectroRepair, Long> {
	@Autowired
	private ElectroRepairDAO electroRepairDAO;

	@Override
	public HibernateDAO<ElectroRepair, Long> getDAO() {
		return electroRepairDAO;
	}
}