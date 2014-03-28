/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.monitor.entity.AlarmConfig;

/**
 * AlarmConfig DAO.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AlarmConfigDAO extends HibernateDAOImpl<AlarmConfig, Long> {
}