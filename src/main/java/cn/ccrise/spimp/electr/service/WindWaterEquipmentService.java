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

import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.WindWaterEquipmentDAO;
import cn.ccrise.spimp.electr.entity.WindWaterEquipment;

/**
 * WindWaterEquipment Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class WindWaterEquipmentService extends HibernateDataServiceImpl<WindWaterEquipment, Long> {
	@Autowired
	private WindWaterEquipmentDAO windWaterEquipmentDAO;

	@Override
	public HibernateDAO<WindWaterEquipment, Long> getDAO() {
		return windWaterEquipmentDAO;
	}
	
	public Page<WindWaterEquipment> pageQuery(Page<WindWaterEquipment> page,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("location", search, MatchMode.ANYWHERE),Restrictions.ilike("equipmentCode", search, MatchMode.ANYWHERE)));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}