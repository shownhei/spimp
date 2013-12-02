/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.Blotters;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Blotters DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class BlottersDAO extends HibernateDAOImpl<Blotters, Long> {
}