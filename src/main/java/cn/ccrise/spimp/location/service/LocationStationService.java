/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationStationDAO;
import cn.ccrise.spimp.location.entity.LocationStation;

/**
 * LocationStation Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationStationService extends HibernateDataServiceImpl<LocationStation, Long> {
	@Autowired
	private LocationStationDAO locationStationDAO;

	@Override
	public HibernateDAO<LocationStation, Long> getDAO() {
		return locationStationDAO;
	}
}