/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.quality.entity.ElectroGrade;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * ElectroGrade DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class ElectroGradeDAO extends HibernateDAOImpl<ElectroGrade, Long> {
}