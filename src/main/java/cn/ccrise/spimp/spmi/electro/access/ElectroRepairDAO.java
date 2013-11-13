/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.electro.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.spmi.electro.entity.ElectroRepair;

/**
 * ElectroRepair DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ElectroRepairDAO extends HibernateDAOImpl<ElectroRepair, Long> {
}