/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.schedule.entity.Dig;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Dig DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class DigDAO extends HibernateDAOImpl<Dig, Long> {
}