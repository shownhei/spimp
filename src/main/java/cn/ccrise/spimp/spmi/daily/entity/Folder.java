/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * Folder。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_folders")
public class Folder extends IDEntity {
	private String name;
	private List<Folder> folders;
	/**
	 * 父节点ID。
	 */
	protected Long parentId;

	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SUBSELECT)
	public List<Folder> getFolders() {
		return folders;
	}

	public String getName() {
		return name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setFolders(List<Folder> folders) {
		this.folders = folders;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
}