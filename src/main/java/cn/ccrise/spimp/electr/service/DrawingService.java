/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.DrawingDAO;
import cn.ccrise.spimp.electr.entity.Drawing;
import cn.ccrise.spimp.system.entity.Account;

/**
 * Drawing Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class DrawingService extends HibernateDataServiceImpl<Drawing, Long> {
	@Autowired
	private DrawingDAO drawingDAO;

	@Override
	public HibernateDAO<Drawing, Long> getDAO() {
		return drawingDAO;
	}

	public Page<Drawing> pageQuery(Page<Drawing> page, String search, Date startDate, Date endDate, Account account,
			GroupEntity group) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("fileTitle", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("uploadDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("uploadDate", endDate));
		}
		criterions.add(Restrictions.le("uploader", account));
		criterions.add(Restrictions.le("uploadGroup", group));
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}