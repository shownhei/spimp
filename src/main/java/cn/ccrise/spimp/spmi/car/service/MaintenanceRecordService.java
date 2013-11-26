/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

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
import cn.ccrise.spimp.spmi.car.access.MaintenanceRecordDAO;
import cn.ccrise.spimp.spmi.car.entity.MaintenanceRecord;

/**
 * MaintenanceRecord Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaintenanceRecordService extends HibernateDataServiceImpl<MaintenanceRecord, Long> {
	@Autowired
	private MaintenanceRecordDAO maintenanceRecordDAO;

	@Override
	public HibernateDAO<MaintenanceRecord, Long> getDAO() {
		return maintenanceRecordDAO;
	}

	public Page<MaintenanceRecord> pageQuery(Page<MaintenanceRecord> page, Date startDate, Date endDate, Long car) {
		List<Criterion> criterions = new ArrayList<Criterion>();

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