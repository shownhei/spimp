/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.car.access.ProblemDAO;
import cn.ccrise.spimp.spmi.car.entity.Problem;

/**
 * Problem Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class ProblemService extends HibernateDataServiceImpl<Problem, Long> {
	@Autowired
	private ProblemDAO problemDAO;

	@Override
	public HibernateDAO<Problem, Long> getDAO() {
		return problemDAO;
	}
	
	public Page<Problem> pageQuery(Page<Problem> page, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("reportDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("reportDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}