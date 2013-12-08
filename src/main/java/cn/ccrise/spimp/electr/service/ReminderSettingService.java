/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.ReminderSettingDAO;
import cn.ccrise.spimp.electr.entity.ReminderSetting;

/**
 * ReminderSetting Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class ReminderSettingService extends HibernateDataServiceImpl<ReminderSetting, Long> {
	@Autowired
	private ReminderSettingDAO reminderSettingDAO;

	@Override
	public HibernateDAO<ReminderSetting, Long> getDAO() {
		return reminderSettingDAO;
	}

	public Page<ReminderSetting> pageQuery(Page<ReminderSetting> page, Long project, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (startDate != null) {
			criterions.add(Restrictions.ge("expirationDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("expirationDate", endDate));
		}

		if (project != null) {
			criterions.add(Restrictions.eq("project.id", project));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}