/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationAttendanceDAO;
import cn.ccrise.spimp.location.entity.LocationAttendance;

/**
 * LocationAttendance Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationAttendanceService extends HibernateDataServiceImpl<LocationAttendance, Long> {
	@Autowired
	private LocationAttendanceDAO locationAttendanceDAO;

	@Override
	public HibernateDAO<LocationAttendance, Long> getDAO() {
		return locationAttendanceDAO;
	}
}