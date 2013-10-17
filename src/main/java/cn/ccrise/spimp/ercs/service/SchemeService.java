/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.SchemeDAO;
import cn.ccrise.spimp.ercs.entity.Scheme;

/**
 * Scheme Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class SchemeService extends HibernateDataServiceImpl<Scheme, Long> {
	@Autowired
	private SchemeDAO schemeDAO;
	@Autowired
	private UploadedFileService uploadedFileService;

	public boolean deleteScheme(HttpSession httpSession, Long id) {
		Scheme temp = findUniqueBy("id", id);
		uploadedFileService.deleteFile(httpSession, temp.getAttachment().getId());
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<Scheme, Long> getDAO() {
		return schemeDAO;
	}
}