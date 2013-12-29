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
import cn.ccrise.spimp.electr.access.InnovationImageDAO;
import cn.ccrise.spimp.electr.entity.InnovationImage;

/**
 * InnovationImage Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class InnovationImageService extends HibernateDataServiceImpl<InnovationImage, Long> {
	@Autowired
	private InnovationImageDAO innovationImageDAO;

	@Override
	public HibernateDAO<InnovationImage, Long> getDAO() {
		return innovationImageDAO;
	}

	public Page<InnovationImage> pageQuery(Page<InnovationImage> page) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}