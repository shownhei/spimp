/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.ReminderSetting;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * ReminderSetting DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class ReminderSettingDAO extends HibernateDAOImpl<ReminderSetting, Long> {
}