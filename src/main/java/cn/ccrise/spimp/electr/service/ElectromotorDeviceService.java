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
import cn.ccrise.spimp.electr.access.ElectromotorDeviceDAO;
import cn.ccrise.spimp.electr.entity.ElectromotorDevice;

/**
 * ElectromotorDevice Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class ElectromotorDeviceService extends HibernateDataServiceImpl<ElectromotorDevice, Long> {
	@Autowired
	private ElectromotorDeviceDAO electromotorDeviceDAO;

	@Override
	public HibernateDAO<ElectromotorDevice, Long> getDAO() {
		return electromotorDeviceDAO;
	}

	public Page<ElectromotorDevice> pageQuery(Page<ElectromotorDevice> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}