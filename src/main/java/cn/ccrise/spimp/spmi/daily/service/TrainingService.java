/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.TrainingDAO;
import cn.ccrise.spimp.spmi.daily.entity.Training;

/**
 * Training Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class TrainingService extends HibernateDataServiceImpl<Training, Long> {
	@Autowired
	private TrainingDAO trainingDAO;

	@Override
	public HibernateDAO<Training, Long> getDAO() {
		return trainingDAO;
	}
}