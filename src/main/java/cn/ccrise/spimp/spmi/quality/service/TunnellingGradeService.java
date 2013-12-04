/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.quality.access.TunnellingGradeDAO;
import cn.ccrise.spimp.spmi.quality.entity.TunnellingGrade;

/**
 * TunnellingGrade Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class TunnellingGradeService extends HibernateDataServiceImpl<TunnellingGrade, Long> {
	@Autowired
	private TunnellingGradeDAO tunnellingGradeDAO;

	@Override
	public HibernateDAO<TunnellingGrade, Long> getDAO() {
		return tunnellingGradeDAO;
	}
}