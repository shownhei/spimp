/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.ResponseTeamDAO;
import cn.ccrise.spimp.ercs.entity.ResponseTeam;

/**
 * ResponseTeam Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ResponseTeamService extends HibernateDataServiceImpl<ResponseTeam, Long> {
	@Autowired
	private ResponseTeamDAO responseTeamDAO;

	@Override
	public HibernateDAO<ResponseTeam, Long> getDAO() {
		return responseTeamDAO;
	}
}