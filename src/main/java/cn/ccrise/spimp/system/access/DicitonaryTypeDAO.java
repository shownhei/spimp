/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.system.entity.DicitonaryType;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * DicitonaryType DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class DicitonaryTypeDAO extends HibernateDAOImpl<DicitonaryType, Long> {
}