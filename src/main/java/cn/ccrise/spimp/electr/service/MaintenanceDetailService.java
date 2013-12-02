/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.MaintenanceDetailDAO;
import cn.ccrise.spimp.electr.entity.MaintenanceDetail;

/**
 * MaintenanceDetail Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaintenanceDetailService extends HibernateDataServiceImpl<MaintenanceDetail, Long> {
	@Autowired
	private MaintenanceDetailDAO maintenanceDetailDAO;

	@Override
	public HibernateDAO<MaintenanceDetail, Long> getDAO() {
		return maintenanceDetailDAO;
	}

	public Page<MaintenanceDetail> pageQuery(Page<MaintenanceDetail> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}