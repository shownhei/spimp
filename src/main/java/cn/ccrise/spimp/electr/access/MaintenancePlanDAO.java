/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.MaintenancePlan;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * MaintenancePlan DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MaintenancePlanDAO extends HibernateDAOImpl<MaintenancePlan, Long> {
}