/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Plan DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class PlanDAO extends HibernateDAOImpl<Plan, Long> {
}