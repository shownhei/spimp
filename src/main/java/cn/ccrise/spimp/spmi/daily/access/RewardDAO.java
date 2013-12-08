/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.Reward;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Reward DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class RewardDAO extends HibernateDAOImpl<Reward, Long> {
}