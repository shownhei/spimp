/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.BrakeDevice;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * BrakeDevice DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class BrakeDeviceDAO extends HibernateDAOImpl<BrakeDevice, Long> {
}