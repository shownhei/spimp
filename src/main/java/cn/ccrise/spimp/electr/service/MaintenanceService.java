/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.Iterator;
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
import cn.ccrise.spimp.electr.access.MaintenanceDAO;
import cn.ccrise.spimp.electr.entity.Maintenance;
import cn.ccrise.spimp.electr.entity.MaintenanceDetail;

/**
 * Maintenance Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaintenanceService extends HibernateDataServiceImpl<Maintenance, Long> {
	@Autowired
	private MaintenanceDAO maintenanceDAO;
	@Autowired
	private MaintenanceDetailService maintenanceDetailService;

	public boolean deleteMaintenance(Long id) {
		Maintenance main = findUniqueBy("id", id);
		Iterator<MaintenanceDetail> it = maintenanceDetailService.find(Restrictions.eq("maintenance", main)).iterator();
		while (it.hasNext()) {
			maintenanceDetailService.delete(it.next());
		}
		return this.delete(id);
	}

	@Override
	public HibernateDAO<Maintenance, Long> getDAO() {
		return maintenanceDAO;
	}

	public Page<Maintenance> pageQuery(Page<Maintenance> page, Long car, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("maintenancePeople", search, MatchMode.ANYWHERE)));
		}

		if (car != null) {
			criterions.add(Restrictions.eq("car.id", car));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}