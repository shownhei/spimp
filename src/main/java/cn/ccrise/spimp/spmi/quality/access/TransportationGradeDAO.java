/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.quality.entity.TransportationGrade;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * TransportationGrade DAO。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class TransportationGradeDAO extends HibernateDAOImpl<TransportationGrade, Long> {
}