/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

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
import cn.ccrise.spimp.electr.access.MaintenancePlanDAO;
import cn.ccrise.spimp.electr.entity.MaintenancePlan;

/**
 * 设备管理 检修计划
 * 
 */
@Service
public class MaintenancePlanService extends HibernateDataServiceImpl<MaintenancePlan, Long> {
	@Autowired
	private MaintenancePlanDAO maintenancePlanDAO;

	@Override
	public HibernateDAO<MaintenancePlan, Long> getDAO() {
		return maintenancePlanDAO;
	}

	public Page<MaintenancePlan> pageQuery(Page<MaintenancePlan> page, Long project, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (startDate != null) {
			criterions.add(Restrictions.ge("workDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("workDate", endDate));
		}

		if (project != null) {
			criterions.add(Restrictions.eq("project.id", project));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}