/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.RunLog;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * RunLog DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class RunLogDAO extends HibernateDAOImpl<RunLog, Long> {
}