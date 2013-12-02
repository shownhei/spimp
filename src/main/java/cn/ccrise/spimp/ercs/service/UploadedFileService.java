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
		deleteFile(temp.getPdfPath(), httpSession);
		deleteFile(temp.getSwfPath(), httpSession);
		deleteFile(temp.getFilePath(), httpSession);
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<UploadedFile, Long> getDAO() {
		return uploadedFileDAO;
	}

	private void deleteFile(String deleteFilePath, HttpSession httpSession) {
		if (deleteFilePath.indexOf("/uploads/") == 0) {
			String uploadRealPath = httpSession.getServletContext().getRealPath("/WEB-INF" + deleteFilePath);
			File file = new File(uploadRealPath);
			// 删除记录和文档，并移除共享中的记录
			// 批量删除共享文档中的记录
			if (file.exists()) {
				file.delete();
			}
		}
	}
}