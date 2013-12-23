/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.InnovationDAO;
import cn.ccrise.spimp.electr.entity.Innovation;

/**
 * Innovation Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class InnovationService extends HibernateDataServiceImpl<Innovation, Long> {
	@Autowired
	private InnovationDAO innovationDAO;

	@Override
	public HibernateDAO<Innovation, Long> getDAO() {
		return innovationDAO;
	}
	
	public Page<Innovation> pageQuery(Page<Innovation> page,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("projectName", search, MatchMode.ANYWHERE)));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}