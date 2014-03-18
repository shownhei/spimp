/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationScheduleDAO;
import cn.ccrise.spimp.location.entity.LocationSchedule;

/**
 * LocationSchedule Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationScheduleService extends HibernateDataServiceImpl<LocationSchedule, Long> {
	@Autowired
	private LocationScheduleDAO locationScheduleDAO;

	@Override
	public HibernateDAO<LocationSchedule, Long> getDAO() {
		return locationScheduleDAO;
	}
}