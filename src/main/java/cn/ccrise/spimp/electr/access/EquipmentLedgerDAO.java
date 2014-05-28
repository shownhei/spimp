/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.EquipmentLedger;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * EquipmentLedger DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class EquipmentLedgerDAO extends HibernateDAOImpl<EquipmentLedger, Long> {
}