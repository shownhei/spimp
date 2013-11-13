/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.spmi.document.entity.Document;

/**
 * Document DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class DocumentDAO extends HibernateDAOImpl<Document, Long> {
}