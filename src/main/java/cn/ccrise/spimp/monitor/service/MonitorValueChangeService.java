/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorValueChangeDAO;
import cn.ccrise.spimp.monitor.entity.MonitorValueChange;

/**
 * MonitorValueChange Service.
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorValueChangeService extends HibernateDataServiceImpl<MonitorValueChange, Long> {

	@Autowired
	private MonitorValueChangeDAO monitorValueChangeDAO;

	@Override
	public HibernateDAO<MonitorValueChange, Long> getDAO() {
		return monitorValueChangeDAO;
	}
}
