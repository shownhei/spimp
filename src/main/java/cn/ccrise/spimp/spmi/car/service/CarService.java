/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

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
import cn.ccrise.spimp.spmi.car.access.CarDAO;
import cn.ccrise.spimp.spmi.car.entity.Car;

/**
 * Car Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class CarService extends HibernateDataServiceImpl<Car, Long> {
	@Autowired
	private CarDAO carDAO;

	@Override
	public HibernateDAO<Car, Long> getDAO() {
		return carDAO;
	}
	
	public Page<Car> pageQuery(Page<Car> page,String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("carCategory", search, MatchMode.ANYWHERE),Restrictions.ilike("models", search, MatchMode.ANYWHERE),Restrictions.ilike("carNo", search, MatchMode.ANYWHERE)));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}