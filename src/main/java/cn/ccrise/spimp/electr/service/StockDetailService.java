/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.StockDetailDAO;
import cn.ccrise.spimp.electr.entity.Blotters;
import cn.ccrise.spimp.electr.entity.Stock;
import cn.ccrise.spimp.electr.entity.StockDetail;

/**
 * StockDetail Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class StockDetailService extends HibernateDataServiceImpl<StockDetail, Long> {
	@Autowired
	private StockDetailDAO stockDetailDAO;
	@Autowired
	private StockService stockService;

	@Override
	public HibernateDAO<StockDetail, Long> getDAO() {
		return stockDetailDAO;
	}

	public Page<StockDetail> pageQuery(Page<StockDetail> page, String search) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("materialName", search, MatchMode.ANYWHERE)));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}

	public void putIn(Blotters instance) {
		Long originalId = instance.getOriginalId();
		StringBuilder buff = new StringBuilder();
		buff.append("update StockDetail a set a.quantity1=a.quantity1+" + instance.getAmount());
		buff.append(",a.quantity3=a.quantity3+" + instance.getAmount());
		buff.append(" where a.materialId=" + originalId);
		// buff.append(" and a.opertionType=" + Blotters.OPERTION_TYPE_PUTIN);
		String updateHql = buff.toString();
		Query updateQuery = getDAO().createQuery(updateHql);
		int count = updateQuery.executeUpdate();
		if (count <= 0) {
			StockDetail temp = new StockDetail();
			temp.setMaterialName1(instance.getMaterialName());
			temp.setMeasureUnit1(instance.getMeasureUnit());
			temp.setQuantity1(instance.getAmount());
			temp.setYearMonth(new Date(System.currentTimeMillis()));
			// temp.setOpertionType(Blotters.OPERTION_TYPE_PUTIN);
			Stock stock = stockService.findUniqueBy("id", originalId);
			temp.setMaterialName3(stock.getMaterialName());
			temp.setMeasureUnit3(stock.getMeasureUnit());
			temp.setQuantity3(stock.getAmount());
			temp.setMaterialId(stock.getId());
			save(temp);
		}
	}

	public void sendOut(Blotters instance) {
		Long originalId = instance.getOriginalId();
		StringBuilder buff = new StringBuilder();
		buff.append("update StockDetail a set a.quantity2=coalesce(a.quantity2,0)+" + instance.getAmount());
		buff.append(",a.quantity3=a.quantity3-" + instance.getAmount());
		buff.append(",a.measureUnit2=a.measureUnit3");
		buff.append(",a.materialName2=a.materialName3");
		buff.append(" where a.materialId=" + originalId);
		// buff.append(" and a.opertionType=" + Blotters.OPERTION_TYPE_SENDOUT);
		String updateHql = buff.toString();
		Query updateQuery = getDAO().createQuery(updateHql);
		int count = updateQuery.executeUpdate();
		if (count <= 0) {
			/**
			 * 看看是否存在该产品的使用记录 如果存在 就更新 否则 就创建
			 */
			StockDetail temp = new StockDetail();
			temp.setYearMonth(new Date(System.currentTimeMillis()));
			// temp.setOpertionType(Blotters.OPERTION_TYPE_SENDOUT);
			Stock stock = stockService.findUniqueBy("id", originalId);
			temp.setMaterialName3(stock.getMaterialName());
			temp.setMaterialName2(instance.getMaterialName());
			temp.setMeasureUnit3(stock.getMeasureUnit());
			temp.setMeasureUnit2(instance.getMeasureUnit());
			temp.setQuantity3(stock.getAmount());
			temp.setQuantity2(instance.getAmount());
			temp.setMaterialId(originalId);
			save(temp);
		}
	}
}