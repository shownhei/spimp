/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.water.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.water.access.WaterDAO;
import cn.ccrise.spimp.spmi.water.entity.Water;

/**
 * Water Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class WaterService extends HibernateDataServiceImpl<Water, Long> {
	@Autowired
	private WaterDAO waterDAO;

	@Override
	public HibernateDAO<Water, Long> getDAO() {
		return waterDAO;
	}
	
	public Page<Water> pageQuery(Page<Water> page, String startDate, String endDate,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("position", search, MatchMode.ANYWHERE)));
		}
		
		if (StringUtils.isNotBlank(startDate)) {
			criterions.add(Restrictions.ge("time", Timestamp.valueOf(startDate + " 00:00:00")));
		}
		if (StringUtils.isNotBlank(endDate)) {
			criterions.add(Restrictions.le("time", Timestamp.valueOf(endDate + " 23:59:59")));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}