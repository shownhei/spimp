/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.schedule.access.TeamDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Team;

/**
 * Team Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class TeamService extends HibernateDataServiceImpl<Team, Long> {
	@Autowired
	private TeamDAO teamDAO;

	@Override
	public HibernateDAO<Team, Long> getDAO() {
		return teamDAO;
	}
	
	public Page<Team> pageQuery(Page<Team> page,String search,Long teamType) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("teamName", search, MatchMode.ANYWHERE)));
		}
		
		if (teamType != null){
			criterions.add(Restrictions.eq("teamType.id", teamType));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}