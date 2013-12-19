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
import cn.ccrise.spimp.electr.access.RegularMaintenanceConfigDAO;
import cn.ccrise.spimp.electr.entity.RegularMaintenanceConfig;

/**
 * 定期保养配置
 * 
 */
@Service
public class RegularMaintenanceConfigService extends HibernateDataServiceImpl<RegularMaintenanceConfig, Long> {
	@Autowired
	private RegularMaintenanceConfigDAO regularMaintenanceConfigDAO;

	@Override
	public HibernateDAO<RegularMaintenanceConfig, Long> getDAO() {
		return regularMaintenanceConfigDAO;
	}

	public Page<RegularMaintenanceConfig> pageQuery(Page<RegularMaintenanceConfig> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}