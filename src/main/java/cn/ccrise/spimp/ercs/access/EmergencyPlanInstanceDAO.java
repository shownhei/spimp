/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * EmergencyPlanInstance DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class EmergencyPlanInstanceDAO extends HibernateDAOImpl<EmergencyPlanInstance, Long> {
}