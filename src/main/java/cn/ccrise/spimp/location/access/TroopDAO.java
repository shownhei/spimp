/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.location.entity.Troop;

/**
 * Troop DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class TroopDAO extends HibernateDAOImpl<Troop, Long> {
}