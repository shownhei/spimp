/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.instruction.access.FocusDAO;
import cn.ccrise.spimp.spmi.instruction.entity.Focus;

/**
 * Focus Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class FocusService extends HibernateDataServiceImpl<Focus, Long> {
	@Autowired
	private FocusDAO focusDAO;

	@Override
	public HibernateDAO<Focus, Long> getDAO() {
		return focusDAO;
	}
}