/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.MaintenanceDetail;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * MaintenanceDetail DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MaintenanceDetailDAO extends HibernateDAOImpl<MaintenanceDetail, Long> {
}