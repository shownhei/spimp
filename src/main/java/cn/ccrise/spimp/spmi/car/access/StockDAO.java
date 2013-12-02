/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.spimp.spmi.car.entity.Stock;
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * Stock DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class StockDAO extends HibernateDAOImpl<Stock, Long> {
}