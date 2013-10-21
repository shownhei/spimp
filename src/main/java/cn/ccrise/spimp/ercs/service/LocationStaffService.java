/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.LocationStaffDAO;
import cn.ccrise.spimp.ercs.entity.LocationStaff;

/**
 * LocationStaff Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationStaffService extends HibernateDataServiceImpl<LocationStaff, Long> {
	@Autowired
	private LocationStaffDAO locationStaffDAO;

	@Override
	public HibernateDAO<LocationStaff, Long> getDAO() {
		return locationStaffDAO;
	}
}