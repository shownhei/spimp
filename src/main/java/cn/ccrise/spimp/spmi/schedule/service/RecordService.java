/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.service;

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
import cn.ccrise.spimp.spmi.schedule.access.RecordDAO;
import cn.ccrise.spimp.spmi.schedule.entity.Record;

/**
 * Record Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RecordService extends HibernateDataServiceImpl<Record, Long> {
	@Autowired
	private RecordDAO recordDAO;

	@Override
	public HibernateDAO<Record, Long> getDAO() {
		return recordDAO;
	}
	
	public Page<Record> pageQuery(Page<Record> page, Date startDate, Date endDate,Long team,Long duty) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("recordDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("recordDate", endDate));
		}
		
		if (team != null){
			criterions.add(Restrictions.eq("team.id", team));
		}
		if (duty != null){
			criterions.add(Restrictions.eq("duty.id", duty));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}