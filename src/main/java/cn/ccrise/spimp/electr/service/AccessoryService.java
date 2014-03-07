/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.io.File;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.electr.access.AccessoryDAO;
import cn.ccrise.spimp.electr.entity.Accessory;

/**
 * Accessory Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class AccessoryService extends HibernateDataServiceImpl<Accessory, Long> {
	@Autowired
	private AccessoryDAO accessoryDAO;

	public boolean deleteAccessory(Long id, HttpSession httpSession) {
		Accessory temp = findUniqueBy("id", id);
		deleteFile(temp.getInstructions().getFilePath(), httpSession);
		deleteFile(temp.getInstructions().getPdfPath(), httpSession);
		deleteFile(temp.getInstructions().getSwfPath(), httpSession);
		return delete(id);
	}

	@Override
	public HibernateDAO<Accessory, Long> getDAO() {
		return accessoryDAO;
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