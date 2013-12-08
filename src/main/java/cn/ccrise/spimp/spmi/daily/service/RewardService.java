/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.RewardDAO;
import cn.ccrise.spimp.spmi.daily.entity.Reward;

/**
 * Reward Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class RewardService extends HibernateDataServiceImpl<Reward, Long> {
	@Autowired
	private RewardDAO rewardDAO;

	@Override
	public HibernateDAO<Reward, Long> getDAO() {
		return rewardDAO;
	}
}