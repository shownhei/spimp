/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.Equipment;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Equipment DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class EquipmentDAO extends HibernateDAOImpl<Equipment, Long> {
}