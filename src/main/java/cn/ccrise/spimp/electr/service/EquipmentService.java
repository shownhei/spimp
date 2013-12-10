/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.EquipmentDAO;
import cn.ccrise.spimp.electr.entity.Equipment;

/**
 * Equipment Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class EquipmentService extends HibernateDataServiceImpl<Equipment, Long> {
	@Autowired
	private EquipmentDAO equipmentDAO;

	@Override
	public HibernateDAO<Equipment, Long> getDAO() {
		return equipmentDAO;
	}

	public Page<Equipment> pageQuery(Page<Equipment> page, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (deviceClass != null) {
			criterions.add(Restrictions.eq("deviceClass.id", deviceClass));
		}
		if (deviceCategory != null) {
			criterions.add(Restrictions.eq("deviceCategory.id", deviceCategory));
		}
		if (deviceType != null) {
			criterions.add(Restrictions.eq("deviceType.id", deviceType));
		}
		if (serviceEnvironment != null) {
			criterions.add(Restrictions.eq("serviceEnvironment.id", serviceEnvironment));
		}
		if (deviceArea != null) {
			criterions.add(Restrictions.eq("deviceArea.id", deviceArea));
		}
		if (stowedPosition != null) {
			criterions.add(Restrictions.eq("stowedPosition.id", stowedPosition));
		}

		if (criterions.size() > 0) {
			return getPage(page, criterions.toArray(new Criterion[0]));
		} else {
			return getPage(page);
		}
	}
}