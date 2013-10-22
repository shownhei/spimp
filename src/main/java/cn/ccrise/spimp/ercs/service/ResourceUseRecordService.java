/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.ResourceUseRecordDAO;
import cn.ccrise.spimp.ercs.entity.ResourceUseRecord;

/**
 * ResourceUseRecord Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ResourceUseRecordService extends HibernateDataServiceImpl<ResourceUseRecord, Long> {
	@Autowired
	private ResourceUseRecordDAO resourceUseRecordDAO;

	@Override
	public HibernateDAO<ResourceUseRecord, Long> getDAO() {
		return resourceUseRecordDAO;
	}
}