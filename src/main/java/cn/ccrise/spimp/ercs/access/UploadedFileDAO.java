/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.ercs.entity.UploadedFile;

/**
 * UploadedFile DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class UploadedFileDAO extends HibernateDAOImpl<UploadedFile, Long> {
}