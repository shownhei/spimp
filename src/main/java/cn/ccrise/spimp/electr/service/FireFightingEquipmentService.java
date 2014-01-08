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
import cn.ccrise.spimp.electr.access.FireFightingEquipmentDAO;
import cn.ccrise.spimp.electr.entity.FireFightingEquipment;

/**
 * FireFightingEquipment Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class FireFightingEquipmentService extends HibernateDataServiceImpl<FireFightingEquipment, Long> {
	@Autowired
	private FireFightingEquipmentDAO fireFightingEquipmentDAO;

	@Override
	public HibernateDAO<FireFightingEquipment, Long> getDAO() {
		return fireFightingEquipmentDAO;
	}
	
	public Page<FireFightingEquipment> pageQuery(Page<FireFightingEquipment> page,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("location", search, MatchMode.ANYWHERE),Restrictions.ilike("equipmentCode", search, MatchMode.ANYWHERE)));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}