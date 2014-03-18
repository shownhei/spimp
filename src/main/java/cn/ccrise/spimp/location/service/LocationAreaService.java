/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationAreaDAO;
import cn.ccrise.spimp.location.entity.LocationArea;

/**
 * LocationArea Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationAreaService extends HibernateDataServiceImpl<LocationArea, Long> {
	@Autowired
	private LocationAreaDAO locationAreaDAO;

	@Override
	public HibernateDAO<LocationArea, Long> getDAO() {
		return locationAreaDAO;
	}
}