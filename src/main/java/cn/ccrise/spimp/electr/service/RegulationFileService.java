/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.RegulationFileDAO;
import cn.ccrise.spimp.electr.entity.RegulationFile;

/**
 * RegulationFile Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RegulationFileService extends HibernateDataServiceImpl<RegulationFile, Long> {
	@Autowired
	private RegulationFileDAO regulationFileDAO;

	@Override
	public HibernateDAO<RegulationFile, Long> getDAO() {
		return regulationFileDAO;
	}

	public Page<RegulationFile> pageQuery(Page<RegulationFile> page, String search, Long fileType, Date startDate,
			Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("fileTitle", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("uploadDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("uploadDate", endDate));
		}

		if (fileType != null) {
			criterions.add(Restrictions.eq("fileType.id", fileType));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}