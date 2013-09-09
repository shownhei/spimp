/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.DictionaryDAO;
import cn.ccrise.spimp.ercs.entity.Dictionary;

/**
 * Dictionary Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class DictionaryService extends HibernateDataServiceImpl<Dictionary, Long> {
	@Autowired
	private DictionaryDAO dictionaryDAO;

	@Override
	public HibernateDAO<Dictionary, Long> getDAO() {
		return dictionaryDAO;
	}
}