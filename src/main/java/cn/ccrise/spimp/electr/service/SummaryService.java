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
import cn.ccrise.spimp.electr.access.SummaryDAO;
import cn.ccrise.spimp.electr.entity.Summary;

/**
 * Summary Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class SummaryService extends HibernateDataServiceImpl<Summary, Long> {
	@Autowired
	private SummaryDAO summaryDAO;

	@Override
	public HibernateDAO<Summary, Long> getDAO() {
		return summaryDAO;
	}

	public Page<Summary> pageQuery(Page<Summary> page, String search, Long summaryType, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("summaryTitle", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("uploadDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("uploadDate", endDate));
		}

		if (summaryType != null) {
			criterions.add(Restrictions.eq("summaryType.id", summaryType));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}