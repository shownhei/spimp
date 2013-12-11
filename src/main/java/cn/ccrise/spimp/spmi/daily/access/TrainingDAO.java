/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.access;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.spmi.daily.entity.Training;
import org.springframework.stereotype.Repository;

/**
 * Training DAO。
 *
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Repository
public class TrainingDAO extends HibernateDAOImpl<Training, Long> {
}