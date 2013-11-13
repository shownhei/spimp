/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.electro.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.electro.access.GradeRecordDAO;
import cn.ccrise.spimp.spmi.electro.entity.GradeRecord;

/**
 * GradeRecord Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class GradeRecordService extends HibernateDataServiceImpl<GradeRecord, Long> {
	@Autowired
	private GradeRecordDAO gradeRecordDAO;

	@Override
	public HibernateDAO<GradeRecord, Long> getDAO() {
		return gradeRecordDAO;
	}
}