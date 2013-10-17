/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyPlanDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyPlan;

/**
 * EmergencyPlan Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyPlanService extends HibernateDataServiceImpl<EmergencyPlan, Long> {
	@Autowired
	private EmergencyPlanDAO emergencyPlanDAO;
	@Autowired
	private UploadedFileService uploadedFileService;

	public boolean deletePlan(HttpSession httpSession, Long id) {
		EmergencyPlan temp = findUniqueBy("id", id);
		uploadedFileService.deleteFile(httpSession, temp.getAttachment().getId());
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<EmergencyPlan, Long> getDAO() {
		return emergencyPlanDAO;
	}
}