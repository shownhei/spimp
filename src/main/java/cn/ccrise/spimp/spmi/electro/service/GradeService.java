/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.electro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.electro.access.GradeDAO;
import cn.ccrise.spimp.spmi.electro.entity.Grade;

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