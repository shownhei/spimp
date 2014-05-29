/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.document.entity.BasicInfomation;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * BasicInfomation DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class BasicInfomationDAO extends HibernateDAOImpl<BasicInfomation, Long> {
}