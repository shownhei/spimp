/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.TeamCultureDAO;
import cn.ccrise.spimp.electr.entity.TeamCulture;

/**
 * TeamCulture Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class TeamCultureService extends HibernateDataServiceImpl<TeamCulture, Long> {
	@Autowired
	private TeamCultureDAO teamCultureDAO;

	@Override
	public HibernateDAO<TeamCulture, Long> getDAO() {
		return teamCultureDAO;
	}
	public boolean deleteCulture(Long id,HttpSession httpSession){
		TeamCulture culture = findUniqueBy("id", id);
		deleteFile(culture.getAttachment(),httpSession);
		return delete(culture);
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
	public Page<TeamCulture> pageQuery(Page<TeamCulture> page,String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("topic", search, MatchMode.ANYWHERE)));
		}
		
		if (startDate != null) {
			criterions.add(Restrictions.ge("recordDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("recordDate", endDate));
		}
		
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}