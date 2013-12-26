/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.spimp.electr.access.MaterialsPlanDAO;
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.entity.MaterialsPlanDetail;
import cn.ccrise.spimp.system.entity.Account;

/**
 * MaterialsPlan Service。
 * 
 */
@Service
public class MaterialsPlanService extends HibernateDataServiceImpl<MaterialsPlan, Long> {
	@Autowired
	private MaterialsPlanDAO materialsPlanDAO;
	@Autowired
	private MaterialsPlanDetailService materialsPlanDetailService;

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

	@Override
	public HibernateDAO<MaterialsPlan, Long> getDAO() {
		return materialsPlanDAO;
	}

	public Page<MaterialsPlan> pageQuery(Page<MaterialsPlan> page, Date startDate, Date endDate, String search,
			HttpSession httpSession) {
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
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		criterions.add(Restrictions.eq("recordGroup", loginAccount.getGroupEntity()));
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}