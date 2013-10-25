/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.entity.ElectroRepair;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * ElectroRepair DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ElectroRepairDAO extends HibernateDAOImpl<ElectroRepair, Long> {
}