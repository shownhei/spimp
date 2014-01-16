/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

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
import cn.ccrise.spimp.electr.access.TransformEquipmentDAO;
import cn.ccrise.spimp.electr.entity.TransformEquipment;

@Service
public class TransformEquipmentService extends HibernateDataServiceImpl<TransformEquipment, Long> {
	@Autowired
	private TransformEquipmentDAO transformEquipmentDAO;

	@Override
	public HibernateDAO<TransformEquipment, Long> getDAO() {
		return transformEquipmentDAO;
	}

	public Page<TransformEquipment> pageQuery(Page<TransformEquipment> page, Long deviceClass, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("deviceName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("deviceModel", search, MatchMode.ANYWHERE)));
		}

		if (deviceClass != null) {
			criterions.add(Restrictions.eq("deviceClass.id", deviceClass));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}