/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.service;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.spmi.document.access.DocumentFolderDAO;
import cn.ccrise.spimp.spmi.document.entity.DocumentFolder;

/**
 * DocumentFolder Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class DocumentFolderService extends HibernateDataServiceImpl<DocumentFolder, Long> {
	@Autowired
	private DocumentFolderDAO documentFolderDAO;
	@Autowired
	private DocumentService documentService;

	public boolean deleteFolder(Long id){
		@SuppressWarnings("unchecked")
		List<Object> docCountResult=documentService.getDAO().getSession().createQuery("select count(*) from Document a where a.folderId="+id).list();
		Long docCount=(Long)docCountResult.get(0);
		@SuppressWarnings("unchecked")
		List<Object> folerCountResult=documentService.getDAO().getSession().createQuery("select count(*) from DocumentFolder a where a.parentId="+id).list();
		Long folderCount=(Long)folerCountResult.get(0);
		
		if(docCount==0 && folderCount==0){
			return delete(id);
		}
		return false;
	}
	@Override
	public HibernateDAO<DocumentFolder, Long> getDAO() {
		return documentFolderDAO;
	}
	
	public Page<DocumentFolder> pageQuery(Page<DocumentFolder> page,Long parentId,HttpSession httpSession) {
		Long rootPid=-1l;
		if(parentId==null){
			parentId=rootPid;
		}
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (parentId!=null) {
			criterions.add(Restrictions.eq("parentId", parentId));
		}
		
		page=getPage(page, criterions.toArray(new Criterion[0]));
		if( page.getTotalCount()==0 && parentId==rootPid){
			DocumentFolder folder = new DocumentFolder();
			folder.setParentId(rootPid);
			folder.setFolderName("根目录");
			folder.setAccount(WebUtils.getAccount(httpSession));
			this.save(folder);
			page=getPage(page, criterions.toArray(new Criterion[0]));
		}
		return page;
	}
}