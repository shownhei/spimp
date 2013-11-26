/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.quality.access.TransportationGradeDAO;
import cn.ccrise.spimp.spmi.quality.entity.TransportationGrade;

/**
 * TransportationGrade Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class TransportationGradeService extends HibernateDataServiceImpl<TransportationGrade, Long> {
	@Autowired
	private TransportationGradeDAO transportationGradeDAO;

	@Override
	public HibernateDAO<TransportationGrade, Long> getDAO() {
		return transportationGradeDAO;
	}
}