/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.StockDetail;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * StockDetail DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class StockDetailDAO extends HibernateDAOImpl<StockDetail, Long> {
}