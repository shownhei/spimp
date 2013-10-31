/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.entity.Dictionary;

/**
 * Dictionary DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class DictionaryDAO extends HibernateDAOImpl<Dictionary, Long> {
}