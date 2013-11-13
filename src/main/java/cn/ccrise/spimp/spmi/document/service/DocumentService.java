/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.service;

import java.sql.Timestamp;
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
import cn.ccrise.spimp.spmi.document.access.DocumentDAO;
import cn.ccrise.spimp.spmi.document.entity.Document;

/**
 * Document Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class DocumentService extends HibernateDataServiceImpl<Document, Long> {
	@Autowired
	private DocumentDAO documentDAO;

	@Override
	public HibernateDAO<Document, Long> getDAO() {
		return documentDAO;
	}
	
	public Page<Document> pageQuery(Page<Document> page, String office, String search, Long projectType, String documentName, String keyword, String startDate, String endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if(StringUtils.isNotBlank(office)){
			criterions.add(Restrictions.eq("office", office));
		}
		
		if(StringUtils.isNotBlank(documentName)){
			criterions.add(Restrictions.ilike("documentName", documentName, MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(keyword)){
			criterions.add(Restrictions.ilike("keyword", keyword, MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(startDate)){
			criterions.add(Restrictions.ge("createTime", Timestamp.valueOf(startDate + " 00:00:00")));
		}
		
		if(StringUtils.isNotBlank(endDate)){
			criterions.add(Restrictions.le("createTime", Timestamp.valueOf(endDate + " 23:59:59")));
		}
		
		if(StringUtils.isNotBlank(search)){
			criterions.add(Restrictions.or(Restrictions.ilike("documentName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("keyWord", search, MatchMode.ANYWHERE),
					Restrictions.ilike("createBy", search, MatchMode.ANYWHERE),
					Restrictions.ilike("updateBy", search, MatchMode.ANYWHERE)));
		}
		
		if(projectType != null){
			criterions.add(Restrictions.eq("projectType.id", projectType));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}