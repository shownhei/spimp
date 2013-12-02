/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.RunLogDAO;
import cn.ccrise.spimp.electr.entity.RunLog;

/**
 * RunLog Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RunLogService extends HibernateDataServiceImpl<RunLog, Long> {
	@Autowired
	private RunLogDAO runLogDAO;

	@Override
	public HibernateDAO<RunLog, Long> getDAO() {
		return runLogDAO;
	}

	public Page<RunLog> pageQuery(Page<RunLog> page, Long car, String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("classType", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("addDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("addDate", endDate));
		}

		if (car != null) {
			criterions.add(Restrictions.eq("car.id", car));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}