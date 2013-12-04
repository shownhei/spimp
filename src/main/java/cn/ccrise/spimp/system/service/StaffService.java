/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.system.access.StaffDAO;
import cn.ccrise.spimp.system.entity.Staff;

/**
 * Staff Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class StaffService extends HibernateDataServiceImpl<Staff, Long> {
	@Autowired
	private StaffDAO staffDAO;

	@Override
	public HibernateDAO<Staff, Long> getDAO() {
		return staffDAO;
	}
}