/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.instruction.entity.Focus;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Focus DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class FocusDAO extends HibernateDAOImpl<Focus, Long> {
}