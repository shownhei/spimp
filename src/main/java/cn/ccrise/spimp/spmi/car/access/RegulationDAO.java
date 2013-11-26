/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.Regulation;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Regulation DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class RegulationDAO extends HibernateDAOImpl<Regulation, Long> {
}