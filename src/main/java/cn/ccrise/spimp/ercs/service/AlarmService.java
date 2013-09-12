/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.AlarmDAO;
import cn.ccrise.spimp.ercs.entity.Alarm;

/**
 * Alarm Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AlarmService extends HibernateDataServiceImpl<Alarm, Long> {
	@Autowired
	private AlarmDAO alarmDAO;

	@Override
	public HibernateDAO<Alarm, Long> getDAO() {
		return alarmDAO;
	}
}