/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.schedule.access.TransportDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Transport;

/**
 * Transport Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class TransportService extends HibernateDataServiceImpl<Transport, Long> {
	@Autowired
	private TransportDAO transportDAO;

	@Override
	public HibernateDAO<Transport, Long> getDAO() {
		return transportDAO;
	}

	public Page<Transport> pageQuery(Page<Transport> page, Date startDate, Date endDate, Long coalType) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (startDate != null) {
			criterions.add(Restrictions.ge("operateDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("operateDate", endDate));
		}

		if (coalType != null) {
			criterions.add(Restrictions.eq("coalType.id", coalType));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}