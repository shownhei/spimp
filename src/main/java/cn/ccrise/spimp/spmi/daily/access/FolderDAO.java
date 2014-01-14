/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.daily.entity.Folder;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Folder DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class FolderDAO extends HibernateDAOImpl<Folder, Long> {
}