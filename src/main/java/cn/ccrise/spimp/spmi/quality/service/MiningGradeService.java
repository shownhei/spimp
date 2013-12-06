/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.quality.access.MiningGradeDAO;
import cn.ccrise.spimp.spmi.quality.entity.MiningGrade;

/**
 * MiningGrade Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class MiningGradeService extends HibernateDataServiceImpl<MiningGrade, Long> {
	@Autowired
	private MiningGradeDAO miningGradeDAO;

	@Override
	public HibernateDAO<MiningGrade, Long> getDAO() {
		return miningGradeDAO;
	}
}