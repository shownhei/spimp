/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.schedule.entity.Transport;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Transport DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class TransportDAO extends HibernateDAOImpl<Transport, Long> {
}