/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.quality.access.ElectroGradeDAO;
import cn.ccrise.spimp.spmi.quality.entity.ElectroGrade;

/**
 * ElectroGrade Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ElectroGradeService extends HibernateDataServiceImpl<ElectroGrade, Long> {
	@Autowired
	private ElectroGradeDAO electroGradeDAO;

	@Override
	public HibernateDAO<ElectroGrade, Long> getDAO() {
		return electroGradeDAO;
	}
}