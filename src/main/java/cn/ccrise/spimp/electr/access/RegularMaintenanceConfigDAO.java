/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.RegularMaintenanceConfig;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * RegularMaintenanceConfig DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class RegularMaintenanceConfigDAO extends HibernateDAOImpl<RegularMaintenanceConfig, Long> {
}