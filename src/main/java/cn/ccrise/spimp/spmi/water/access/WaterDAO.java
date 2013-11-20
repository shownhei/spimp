/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.water.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.water.entity.Water;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Water DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class WaterDAO extends HibernateDAOImpl<Water, Long> {
}