/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.ResponseTeamMemberDAO;
import cn.ccrise.spimp.ercs.entity.ResponseTeamMember;

/**
 * ResponseTeamMember Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class ResponseTeamMemberService extends HibernateDataServiceImpl<ResponseTeamMember, Long> {
	@Autowired
	private ResponseTeamMemberDAO responseTeamMemberDAO;

	@Override
	public HibernateDAO<ResponseTeamMember, Long> getDAO() {
		return responseTeamMemberDAO;
	}
}