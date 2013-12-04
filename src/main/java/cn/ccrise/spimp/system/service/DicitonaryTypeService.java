/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.service;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.system.access.DicitonaryTypeDAO;
import cn.ccrise.spimp.system.entity.DicitonaryType;

/**
 * DicitonaryType Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class DicitonaryTypeService extends HibernateDataServiceImpl<DicitonaryType, Long> {
	@Autowired
	private DicitonaryTypeDAO dicitonaryTypeDAO;

	@Override
	public HibernateDAO<DicitonaryType, Long> getDAO() {
		return dicitonaryTypeDAO;
	}
	
	public Page<DicitonaryType> pageQuery(Page<DicitonaryType> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}