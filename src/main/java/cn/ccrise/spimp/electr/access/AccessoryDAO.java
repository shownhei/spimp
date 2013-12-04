/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.Accessory;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Accessory DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class AccessoryDAO extends HibernateDAOImpl<Accessory, Long> {
}