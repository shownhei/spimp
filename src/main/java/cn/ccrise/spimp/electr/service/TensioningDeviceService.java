/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.TensioningDeviceDAO;
import cn.ccrise.spimp.electr.entity.TensioningDevice;

/**
 * TensioningDevice Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class TensioningDeviceService extends HibernateDataServiceImpl<TensioningDevice, Long> {
	@Autowired
	private TensioningDeviceDAO tensioningDeviceDAO;

	@Override
	public HibernateDAO<TensioningDevice, Long> getDAO() {
		return tensioningDeviceDAO;
	}

	public Page<TensioningDevice> pageQuery(Page<TensioningDevice> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}