/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.BlottersDAO;
import cn.ccrise.spimp.electr.entity.Blotters;
import cn.ccrise.spimp.electr.entity.Stock;
import cn.ccrise.spimp.electr.entity.StockDetail;

/**
 * 
 */
@Service
public class BlottersService extends HibernateDataServiceImpl<Blotters, Long> {
	@Autowired
	private BlottersDAO blottersDAO;
	@Autowired
	private StockService stockService;
	@Autowired
	private StockDetailService stockDetailService;

	@Override
	public HibernateDAO<Blotters, Long> getDAO() {
		return blottersDAO;
	}

	public Page<Blotters> pageQuery(Page<Blotters> page, String search, Integer operationType) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("materialName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("model", search, MatchMode.ANYWHERE)));
		}
		criterions.add(Restrictions.eq("opertionType", operationType));
		return getPage(page, criterions.toArray(new Criterion[0]));
	}

	public boolean putIn(Blotters instance) {
		if (instance.getOriginalId() == null) {// 证明没有对应的产品记录
			Stock stock = new Stock();
			stock.setAmount(instance.getAmount());
			stock.setMaterialName(instance.getMaterialName());
			stock.setMeasureUnit(instance.getMeasureUnit());
			stock.setModel(instance.getModel());
			stock.setPrice(instance.getPrice());
			stock.setRemark(instance.getRemark());
			stock.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			stockService.save(stock);
			instance.setOriginalId(stock.getId());
			stockDetailService.putIn(instance);
		} else {
			stockService.putIn(instance.getOriginalId(), instance.getAmount());
			stockDetailService.putIn(instance);
		}
		return save(instance);
	}

	public boolean sendOut(Blotters instance) {
		boolean result = false;
		if (instance.getOpertionType().intValue() == Blotters.OPERTION_TYPE_SENDOUT) {
			result = stockService.sendOut(instance.getOriginalId(), instance.getAmount());
			stockDetailService.sendOut(instance);
			if (result) {
				save(instance);
			}
		}
		return result;
	}

	/**
	 * 统计当前年月的情况
	 * 
	 * @return
	 */
	public List<StockDetail> staticsCurrentYearMonth() {
		return stockDetailService.find();
	}
}