/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.AccidentDAO;
import cn.ccrise.spimp.spmi.daily.entity.Accident;

/**
 * Accident Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AccidentService extends HibernateDataServiceImpl<Accident, Long> {
	@Autowired
	private AccidentDAO accidentDAO;

	@Override
	public HibernateDAO<Accident, Long> getDAO() {
		return accidentDAO;
	}
}