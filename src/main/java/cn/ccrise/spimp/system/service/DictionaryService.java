/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.system.access.DictionaryDAO;
import cn.ccrise.spimp.system.entity.Dictionary;

/**
 * Dictionary Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class DictionaryService extends HibernateDataServiceImpl<Dictionary, Long> {
	@Autowired
	private DictionaryDAO dictionaryDAO;

	/***
	 * 周期性执行数据库查询，保障数据库连接不会断开
	 */
	@Scheduled(fixedRate = 1000 * 60 * 60)
	public void dbTest() {
		getDAO().getSession().createQuery("from Dictionary a").list();
	}

	@Override
	public HibernateDAO<Dictionary, Long> getDAO() {
		return dictionaryDAO;
	}
}