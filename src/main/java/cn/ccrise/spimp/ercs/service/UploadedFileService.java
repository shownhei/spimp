/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.service;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.UploadedFileDAO;
import cn.ccrise.spimp.ercs.entity.UploadedFile;

/**
 * UploadedFile Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class UploadedFileService extends HibernateDataServiceImpl<UploadedFile, Long> {
	@Autowired
	private UploadedFileDAO uploadedFileDAO;

	public boolean deleteFile(HttpSession httpSession, Long id) {
		UploadedFile temp = findUniqueBy("id", id);
		String uploadRealPath = httpSession.getServletContext().getRealPath("/WEB-INF/");
		File file = new File(uploadRealPath + "/" + temp.getPdfPath());
		if (file.exists()) {
			file.delete();
		}
		file = new File(uploadRealPath + "/" + temp.getSwfPath());
		if (file.exists()) {
			file.delete();
		}
		file = new File(uploadRealPath + "/" + temp.getFilePath());
		if (file.exists()) {
			file.delete();
		}
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<UploadedFile, Long> getDAO() {
		return uploadedFileDAO;
	}
}