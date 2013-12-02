/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

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
import cn.ccrise.spimp.spmi.car.access.MaterialsPlanDetailDAO;
import cn.ccrise.spimp.spmi.car.entity.MaterialsPlanDetail;

/**
 * MaterialsPlanDetail Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaterialsPlanDetailService extends HibernateDataServiceImpl<MaterialsPlanDetail, Long> {
	@Autowired
	private MaterialsPlanDetailDAO materialsPlanDetailDAO;

	@Override
	public HibernateDAO<MaterialsPlanDetail, Long> getDAO() {
		return materialsPlanDetailDAO;
	}

	public Page<MaterialsPlanDetail> pageQuery(Page<MaterialsPlanDetail> page, Long plan, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("materialName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("model", search, MatchMode.ANYWHERE)));
		}

		if (plan != null) {
			criterions.add(Restrictions.eq("plan.id", plan));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}