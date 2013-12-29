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
import cn.ccrise.spimp.electr.access.WorkArrangeDAO;
import cn.ccrise.spimp.electr.entity.WorkArrange;

/**
 * WorkArrange Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class WorkArrangeService extends HibernateDataServiceImpl<WorkArrange, Long> {
	@Autowired
	private WorkArrangeDAO workArrangeDAO;

	@Override
	public HibernateDAO<WorkArrange, Long> getDAO() {
		return workArrangeDAO;
	}

	public Page<WorkArrange> pageQuery(Page<WorkArrange> page, String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("fileTitle", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("uploadDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("uploadDate", endDate));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}