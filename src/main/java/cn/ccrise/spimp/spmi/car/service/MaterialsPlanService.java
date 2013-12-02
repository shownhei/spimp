/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

import java.sql.Date;
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
import cn.ccrise.spimp.spmi.car.access.MaterialsPlanDAO;
import cn.ccrise.spimp.spmi.car.entity.MaterialsPlan;
import cn.ccrise.spimp.spmi.car.entity.MaterialsPlanDetail;

/**
 * MaterialsPlan Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MaterialsPlanService extends HibernateDataServiceImpl<MaterialsPlan, Long> {
	@Autowired
	private MaterialsPlanDAO materialsPlanDAO;
	@Autowired
	private MaterialsPlanDetailService materialsPlanDetailService;

	@Override
	public HibernateDAO<MaterialsPlan, Long> getDAO() {
		return materialsPlanDAO;
	}

	public boolean deletePlan(Long planId) {
		MaterialsPlan plan = findUniqueBy("id", planId);
		List<MaterialsPlanDetail> details = materialsPlanDetailService.findBy("plan", plan);
		if (details != null) {
			Iterator<MaterialsPlanDetail> it = details.iterator();
			while (it.hasNext()) {
				materialsPlanDetailService.delete(it.next());
			}
		}
		return delete(plan);
	}

	public Page<MaterialsPlan> pageQuery(Page<MaterialsPlan> page, Date startDate, Date endDate, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("planTitle", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("planDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("planDate", endDate));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}