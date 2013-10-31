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

	public Page<Output> pageQuery(Page<Output> page, Date startDate, Date endDate, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("railwayRemark", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("operateDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("operateDate", endDate));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}