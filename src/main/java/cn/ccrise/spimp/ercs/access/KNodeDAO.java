/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.ercs.entity.KNode;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * KNode DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class KNodeDAO extends HibernateDAOImpl<KNode, Long> {
}