/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.ercs.entity.Dictionary;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Dictionary DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class DictionaryDAO extends HibernateDAOImpl<Dictionary, Long> {
}