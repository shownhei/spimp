/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.ercs.entity.ResourceUseRecord;

/**
 * ResourceUseRecord DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ResourceUseRecordDAO extends HibernateDAOImpl<ResourceUseRecord, Long> {
}