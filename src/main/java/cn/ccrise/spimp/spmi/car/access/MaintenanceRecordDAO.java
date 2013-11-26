/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.MaintenanceRecord;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * MaintenanceRecord DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MaintenanceRecordDAO extends HibernateDAOImpl<MaintenanceRecord, Long> {
}