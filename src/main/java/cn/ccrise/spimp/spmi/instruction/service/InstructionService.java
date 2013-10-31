/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.service;

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
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.instruction.access.InstructionDAO;
import cn.ccrise.spimp.spmi.instruction.entity.Instruction;

/**
 * Instruction Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class InstructionService extends HibernateDataServiceImpl<Instruction, Long> {
	@Autowired
	private InstructionDAO instructionDAO;

	@Override
	public HibernateDAO<Instruction, Long> getDAO() {
		return instructionDAO;
	}
	
	public Page<Instruction> pageQuery(Page<Instruction> page, Date startDate, Date endDate, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("indicator", search, MatchMode.ANYWHERE),
					Restrictions.ilike("content", search, MatchMode.ANYWHERE)));
		}
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("time", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("time", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}