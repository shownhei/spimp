/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

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
import cn.ccrise.spimp.spmi.schedule.access.CircumstanceDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Circumstance;

/**
 * Circumstance Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class CircumstanceService extends HibernateDataServiceImpl<Circumstance, Long> {
	@Autowired
	private CircumstanceDAO circumstanceDAO;

	@Override
	public HibernateDAO<Circumstance, Long> getDAO() {
		return circumstanceDAO;
	}

	public Page<Circumstance> pageQuery(Page<Circumstance> page, Date startDate, Date endDate, Long duty, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("name", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("workDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("workDate", endDate));
		}

		if (duty != null) {
			criterions.add(Restrictions.eq("duty.id", duty));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}