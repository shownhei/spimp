/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.spmi.entity.ElectroQuery;

/**
 * ElectroQuery DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ElectroQueryDAO extends HibernateDAOImpl<ElectroQuery, Long> {
}