/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * 车辆保养。包括日常保养和定期保养
 * 
 */
@Entity
@Table(name = "car_daily_maintenances")
public class DailyMaintenance extends IDEntity {
	// 车牌号、保养类别、日期、检查维修项目、保养方式、检修处理情况、备注、保养人员、验收人

}