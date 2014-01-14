/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.spmi.daily.access.FolderDAO;
import cn.ccrise.spimp.spmi.daily.entity.Folder;

/**
 * Folder Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class FolderService extends HibernateDataServiceImpl<Folder, Long> {
	@Autowired
	private FolderDAO folderDAO;

	@Override
	public HibernateDAO<Folder, Long> getDAO() {
		return folderDAO;
	}

	/**
	 * 关联保存。保存组，如果有parentId属性一并保存与其他组之间的关联。
	 * 
	 * @param folder
	 *            需要保存的组
	 * @return true 保存成功 false 保存失败
	 */
	public boolean associatedSave(Folder folder) {
		if (folder.getParentId() != null) {
			Folder parentGroupEntity = get(folder.getParentId());
			return save(folder, parentGroupEntity);
		} else {
			return save(folder);
		}
	}

	private boolean save(Folder folder, Folder parentFolder) {
		boolean result1 = super.save(folder);
		parentFolder.getFolders().add(folder);
		boolean result2 = super.save(parentFolder);
		return result1 && result2;
	}

	/**
	 * 
	 * @param folder
	 *            需要更新的组
	 * @return true 更新成功 false 更新失败
	 */
	public boolean associatedUpdate(Folder folder) {
		Folder folderInDB = get(folder.getId());
		// 保留之前的子节点
		folder.setFolders(folderInDB.getFolders());
		folder.setParentId(folderInDB.getParentId());
		merge(folder);
		return true;
	}

	/**
	 * 关联删除。删除组并移除与其他组之间的关联。
	 * 
	 * @param folder
	 *            需要删除的组
	 * @return true 删除成功 false 删除失败
	 */
	public boolean associatedDelete(Folder folder) {
		Folder parentFolder = get(folder.getParentId());
		Folder removeFolder = get(folder.getId());
		parentFolder.getFolders().remove(removeFolder);
		return delete(removeFolder);
	}
}