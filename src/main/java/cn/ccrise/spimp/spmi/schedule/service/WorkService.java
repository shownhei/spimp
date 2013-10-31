/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
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
import cn.ccrise.spimp.spmi.schedule.access.WorkDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Work;

/**
 * Work Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class WorkService extends HibernateDataServiceImpl<Work, Long> {
	@Autowired
	private WorkDAO workDAO;

	@Override
	public HibernateDAO<Work, Long> getDAO() {
		return workDAO;
	}
	
	public Page<Work> pageQuery(Page<Work> page, Date startDate, Date endDate,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("leaderName", search, MatchMode.ANYWHERE)));
		}
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("workDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("workDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}