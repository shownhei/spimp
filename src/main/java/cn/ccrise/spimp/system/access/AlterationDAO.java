/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.system.entity.Alteration;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Alteration DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AlterationDAO extends HibernateDAOImpl<Alteration, Long> {
}