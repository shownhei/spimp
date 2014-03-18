/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationStateDAO;
import cn.ccrise.spimp.location.entity.LocationState;

@Service
public class LocationStateService extends HibernateDataServiceImpl<LocationState, Long> {
	@Autowired
	private LocationStateDAO locationStateDAO;

	@Override
	public HibernateDAO<LocationState, Long> getDAO() {
		return locationStateDAO;
	}
}
