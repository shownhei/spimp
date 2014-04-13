/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.security.entity.AccountEntity;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
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

	public Page<Document> pageQuery(Page<Document> page, String office, String search,
			String documentName, String keyword, String startDate, String endDate,Long folderId,HttpSession httpSession) {
		AccountEntity account=WebUtils.getAccount(httpSession);
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		SimpleExpression case1=Restrictions.eq("securityLevel", 1);
		SimpleExpression accountEq=Restrictions.eq("account", account);
		LogicalExpression level1=Restrictions.and(case1,accountEq);
		//2
		SimpleExpression eq=Restrictions.eq("uploadGroup", account.getGroupEntity());
		SimpleExpression case2=Restrictions.eq("securityLevel", 2);
		
		LogicalExpression level2=Restrictions.and(eq,case2);
		
		//3
		SimpleExpression case3=Restrictions.eq("securityLevel", 3);
		criterions.add(Restrictions.or(level1,level2,case3));
		if(folderId!=null&&folderId==1l){
			return getPage(page, criterions.toArray(new Criterion[0]));
		}
		if (StringUtils.isNotBlank(office)) {
			criterions.add(Restrictions.eq("office", office));
		}

		if (StringUtils.isNotBlank(documentName)) {
			criterions.add(Restrictions.ilike("documentName", documentName, MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(keyword)) {
			criterions.add(Restrictions.ilike("keyWord", keyword, MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(startDate)) {
			criterions.add(Restrictions.ge("createTime", Timestamp.valueOf(startDate + " 00:00:00")));
		}

		if (StringUtils.isNotBlank(endDate)) {
			criterions.add(Restrictions.le("createTime", Timestamp.valueOf(endDate + " 23:59:59")));
		}

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("documentName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("keyWord", search, MatchMode.ANYWHERE),
					Restrictions.ilike("createBy", search, MatchMode.ANYWHERE),
					Restrictions.ilike("updateBy", search, MatchMode.ANYWHERE)));
		}

		if (folderId!=null) {
			criterions.add(Restrictions.eq("folderId",folderId));
		}
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}