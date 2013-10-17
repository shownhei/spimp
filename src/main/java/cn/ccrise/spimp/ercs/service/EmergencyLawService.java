/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.EmergencyLawDAO;
import cn.ccrise.spimp.ercs.entity.EmergencyLaw;

/**
 * EmergencyLaw Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class EmergencyLawService extends HibernateDataServiceImpl<EmergencyLaw, Long> {
	@Autowired
	private EmergencyLawDAO emergencyLawDAO;
	@Autowired
	private UploadedFileService uploadedFileService;

	public boolean deleteEmergencyLaw(HttpSession httpSession, Long id) {
		EmergencyLaw temp = findUniqueBy("id", id);
		uploadedFileService.deleteFile(httpSession, temp.getAttachment().getId());
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<EmergencyLaw, Long> getDAO() {
		return emergencyLawDAO;
	}
}