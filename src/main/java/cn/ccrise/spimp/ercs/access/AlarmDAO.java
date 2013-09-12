/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Alarm DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AlarmDAO extends HibernateDAOImpl<Alarm, Long> {
}