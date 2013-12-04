/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.electr.access.EquipmentDAO;
import cn.ccrise.spimp.electr.entity.Equipment;

/**
 * Equipment Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EquipmentService extends HibernateDataServiceImpl<Equipment, Long> {
	@Autowired
	private EquipmentDAO equipmentDAO;

	@Override
	public HibernateDAO<Equipment, Long> getDAO() {
		return equipmentDAO;
	}
}