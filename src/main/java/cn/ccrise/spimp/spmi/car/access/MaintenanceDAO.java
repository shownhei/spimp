/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.Maintenance;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Maintenance DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MaintenanceDAO extends HibernateDAOImpl<Maintenance, Long> {
}