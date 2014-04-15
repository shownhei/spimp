/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.electr.entity.TeamCulture;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * TeamCulture DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class TeamCultureDAO extends HibernateDAOImpl<TeamCulture, Long> {
}