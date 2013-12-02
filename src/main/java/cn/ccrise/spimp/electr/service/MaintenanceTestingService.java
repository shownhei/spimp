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
import cn.ccrise.spimp.electr.access.MaintenanceTestingDAO;
import cn.ccrise.spimp.electr.entity.MaintenanceTesting;

/**
 * MaintenanceTesting Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaintenanceTestingService extends HibernateDataServiceImpl<MaintenanceTesting, Long> {
	@Autowired
	private MaintenanceTestingDAO maintenanceTestingDAO;

	@Override
	public HibernateDAO<MaintenanceTesting, Long> getDAO() {
		return maintenanceTestingDAO;
	}

	public Page<MaintenanceTesting> pageQuery(Page<MaintenanceTesting> page, Date startDate, Date endDate, Long car,
			String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("miantainer", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("maintenanceDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("maintenanceDate", endDate));
		}

		if (car != null) {
			criterions.add(Restrictions.eq("car.id", car));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}