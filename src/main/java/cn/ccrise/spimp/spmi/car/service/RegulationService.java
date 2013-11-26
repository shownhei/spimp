/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.ercs.service.UploadedFileService;
import cn.ccrise.spimp.spmi.car.access.RegulationDAO;
import cn.ccrise.spimp.spmi.car.entity.Regulation;

/**
 * Regulation Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RegulationService extends HibernateDataServiceImpl<Regulation, Long> {
	@Autowired
	private RegulationDAO regulationDAO;
	@Autowired
	private UploadedFileService uploadedFileService;

	public boolean deleteRegulation(HttpSession httpSession, Long id) {
		Regulation temp = findUniqueBy("id", id);
		uploadedFileService.deleteFile(httpSession, temp.getAttachment().getId());
		this.delete(temp);
		return true;
	}

	@Override
	public HibernateDAO<Regulation, Long> getDAO() {
		return regulationDAO;
	}

	public Page<Regulation> pageQuery(Page<Regulation> page, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("title", search, MatchMode.ANYWHERE)));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}