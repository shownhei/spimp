/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.ercs.entity.Refuge;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Refuge DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class RefugeDAO extends HibernateDAOImpl<Refuge, Long> {
}