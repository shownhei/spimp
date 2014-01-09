/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.PictureDAO;
import cn.ccrise.spimp.spmi.daily.entity.Picture;

/**
 * Picture Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class PictureService extends HibernateDataServiceImpl<Picture, Long> {
	@Autowired
	private PictureDAO pictureDAO;

	@Override
	public HibernateDAO<Picture, Long> getDAO() {
		return pictureDAO;
	}
}