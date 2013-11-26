/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.quality.access.GradeDAO;
import cn.ccrise.spimp.spmi.quality.entity.Grade;

/**
 * Grade Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class GradeService extends HibernateDataServiceImpl<Grade, Long> {
	@Autowired
	private GradeDAO gradeDAO;

	@Override
	public HibernateDAO<Grade, Long> getDAO() {
		return gradeDAO;
	}
}