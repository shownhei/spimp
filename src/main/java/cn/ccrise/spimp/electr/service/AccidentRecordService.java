/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

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
import cn.ccrise.spimp.electr.access.AccidentRecordDAO;
import cn.ccrise.spimp.electr.entity.AccidentRecord;

/**
 * AccidentRecord Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class AccidentRecordService extends HibernateDataServiceImpl<AccidentRecord, Long> {
	@Autowired
	private AccidentRecordDAO accidentRecordDAO;

	@Override
	public HibernateDAO<AccidentRecord, Long> getDAO() {
		return accidentRecordDAO;
	}
	
	public Page<AccidentRecord> pageQuery(Page<AccidentRecord> page, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("accedentDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("accedentDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}