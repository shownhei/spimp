/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.Accident;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Accident DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AccidentDAO extends HibernateDAOImpl<Accident, Long> {
}