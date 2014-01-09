/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.ElectromotorDevice;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * ElectromotorDevice DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class ElectromotorDeviceDAO extends HibernateDAOImpl<ElectromotorDevice, Long> {
}