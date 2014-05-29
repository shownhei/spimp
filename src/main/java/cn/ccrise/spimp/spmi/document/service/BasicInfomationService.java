/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.service;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.document.access.BasicInfomationDAO;
import cn.ccrise.spimp.spmi.document.entity.BasicInfomation;

/**
 * BasicInfomation Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class BasicInfomationService extends HibernateDataServiceImpl<BasicInfomation, Long> {
	@Autowired
	private BasicInfomationDAO basicInfomationDAO;

	@Override
	public HibernateDAO<BasicInfomation, Long> getDAO() {
		return basicInfomationDAO;
	}
	
	public Page<BasicInfomation> pageQuery(Page<BasicInfomation> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}