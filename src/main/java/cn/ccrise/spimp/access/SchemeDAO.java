/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.entity.Scheme;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Scheme DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class SchemeDAO extends HibernateDAOImpl<Scheme, Long> {
}