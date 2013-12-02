/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.MaintenanceTesting;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * MaintenanceTesting DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MaintenanceTestingDAO extends HibernateDAOImpl<MaintenanceTesting, Long> {
}