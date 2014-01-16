/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.electr.entity.WindWaterEquipment;

/**
 * WindWaterEquipment DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class WindWaterEquipmentDAO extends HibernateDAOImpl<WindWaterEquipment, Long> {
}