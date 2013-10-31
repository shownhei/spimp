/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.service;

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
import cn.ccrise.spimp.spmi.instruction.access.FocusDAO;
import cn.ccrise.spimp.spmi.instruction.entity.Focus;

/**
 * Focus Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class FocusService extends HibernateDataServiceImpl<Focus, Long> {
	@Autowired
	private FocusDAO focusDAO;

	@Override
	public HibernateDAO<Focus, Long> getDAO() {
		return focusDAO;
	}

	public Page<Focus> pageQuery(Page<Focus> page, String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("name", search, MatchMode.ANYWHERE),
					Restrictions.ilike("recorder", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("startTime", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("startTime", endDate));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}