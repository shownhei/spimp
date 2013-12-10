/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.OverhaulDAO;
import cn.ccrise.spimp.electr.entity.Overhaul;

/**
 * Overhaul Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class OverhaulService extends HibernateDataServiceImpl<Overhaul, Long> {
	@Autowired
	private OverhaulDAO overhaulDAO;

	@Override
	public HibernateDAO<Overhaul, Long> getDAO() {
		return overhaulDAO;
	}
	
	public Page<Overhaul> pageQuery(Page<Overhaul> page, Date startDate, Date endDate,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("overhaulPosition", search, MatchMode.ANYWHERE),Restrictions.ilike("chargePersoin", search, MatchMode.ANYWHERE)));
		}
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("overhaulDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("overhaulDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}