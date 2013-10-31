/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

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
import cn.ccrise.spimp.spmi.schedule.access.OutputDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Output;

/**
 * Output Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class OutputService extends HibernateDataServiceImpl<Output, Long> {
	@Autowired
	private OutputDAO outputDAO;

	@Override
	public HibernateDAO<Output, Long> getDAO() {
		return outputDAO;
	}
	
	public Page<Output> pageQuery(Page<Output> page, Date startDate, Date endDate,Long duty) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("digDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("digDate", endDate));
		}
		
		if (duty != null){
			criterions.add(Restrictions.eq("duty.id", duty));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}