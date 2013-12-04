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
import cn.ccrise.spimp.electr.access.RegulationRewardDAO;
import cn.ccrise.spimp.electr.entity.RegulationReward;

/**
 * RegulationReward Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RegulationRewardService extends HibernateDataServiceImpl<RegulationReward, Long> {
	@Autowired
	private RegulationRewardDAO regulationRewardDAO;

	@Override
	public HibernateDAO<RegulationReward, Long> getDAO() {
		return regulationRewardDAO;
	}
	
	public Page<RegulationReward> pageQuery(Page<RegulationReward> page,String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("awardedPeople", search, MatchMode.ANYWHERE)));
		}
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("awardedDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("awardedDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}