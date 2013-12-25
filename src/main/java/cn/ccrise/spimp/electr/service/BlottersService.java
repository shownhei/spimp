/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

	/**
	 * 入库、出库、库存明细
	 */
	public void stockChangeDetail(HashMap<String, Object> root, Integer year, Integer month) {
		// 入库的
		HashMap<Long, StockDetail> detailMap = new HashMap<Long, StockDetail>();
		ArrayList<StockDetail> tempList = null;
		ArrayList<StockDetail> resultList = new ArrayList<StockDetail>();
		tempList = putInQueryByYearMonth(root, year, month);
		StockDetail currentDetail = null;
		StockDetail temp = null;
		Iterator<StockDetail> putInIt = tempList.iterator();
		while (putInIt.hasNext()) {
			currentDetail = putInIt.next();
			detailMap.put(currentDetail.getMaterialId(), currentDetail);
			resultList.add(currentDetail);
		}
		// 出库
		tempList = sendOutQueryByYearMonth(root, year, month);
		Iterator<StockDetail> sendOutIt = tempList.iterator();
		while (sendOutIt.hasNext()) {
			currentDetail = sendOutIt.next();
			if (detailMap.containsKey(currentDetail.getMaterialId())) {
				temp = detailMap.get(currentDetail.getMaterialId());
				temp.setMaterialName2(currentDetail.getMaterialName2());
				temp.setMeasureUnit2(currentDetail.getMeasureUnit2());
				temp.setQuantity2((temp.getQuantity2() == null ? 0 : temp.getQuantity2())
						+ currentDetail.getQuantity2());
			} else {
				detailMap.put(currentDetail.getMaterialId(), currentDetail);
				resultList.add(currentDetail);
			}
		}
		tempList = noChangeQueryByYearMonth(root, year, month);
		Iterator<StockDetail> otherIt = tempList.iterator();
		while (otherIt.hasNext()) {
			currentDetail = otherIt.next();
			resultList.add(currentDetail);
		}
		root.put("result", resultList);
	}

	/**
	 * 入库情况
	 * 
	 * @param root
	 */
	private ArrayList<StockDetail> putInQueryByYearMonth(HashMap<String, Object> root, Integer year, Integer month) {
		StringBuilder buff = new StringBuilder();
		buff.append(" select a.originalId, a.materialName,  a.amount,   a.measureUnit,");
		buff.append(" b.materialId,b.materialName3, b.quantity3,b.measureUnit3 ");
		buff.append(" from Blotters a ,StockDetail b  ");
		buff.append(" where a.originalId=b.materialId and a.opertionType=1 and year(a.recordTime)=:year and month(a.recordTime)=:month ");
		Query query = getDAO().getSession().createQuery(buff.toString());
		query.setInteger("year", year);
		query.setInteger("month", month);
		Iterator<?> detailList = query.list().iterator();
		ArrayList<StockDetail> result = new ArrayList<StockDetail>();
		Object raw[] = null;
		StockDetail tempDetail = null;
		int i = 0;
		while (detailList.hasNext()) {
			i = 0;
			raw = (Object[]) detailList.next();
			tempDetail = new StockDetail();
			tempDetail.setMaterialId((Long) raw[i++]);
			tempDetail.setMaterialName1((String) raw[i++]);
			tempDetail.setQuantity1((Integer) raw[i++]);
			tempDetail.setMeasureUnit1((String) raw[i++]);
			i++;
			tempDetail.setMaterialName3((String) raw[i++]);
			tempDetail.setQuantity3((Integer) raw[i++]);
			tempDetail.setMeasureUnit3((String) raw[i++]);
			result.add(tempDetail);
		}
		return result;
	}

	/**
	 * 出库情况
	 * 
	 * @param root
	 */
	private ArrayList<StockDetail> sendOutQueryByYearMonth(HashMap<String, Object> root, Integer year, Integer month) {
		StringBuilder buff = new StringBuilder();
		buff.append(" select a.originalId, a.materialName,  a.amount,   a.measureUnit,");
		buff.append(" b.materialId,b.materialName3, b.quantity3,b.measureUnit3 ");
		buff.append(" from Blotters a ,StockDetail b  ");
		buff.append(" where a.originalId=b.materialId and a.opertionType=:opertionType and year(a.recordTime)=:year and month(a.recordTime)=:month  ");
		Query query = getDAO().getSession().createQuery(buff.toString());
		query.setInteger("opertionType", Blotters.OPERTION_TYPE_SENDOUT);
		query.setInteger("year", year);
		query.setInteger("month", month);
		Iterator<?> detailList = query.list().iterator();
		ArrayList<StockDetail> result = new ArrayList<StockDetail>();
		Object raw[] = null;
		StockDetail tempDetail = null;
		int i = 0;
		while (detailList.hasNext()) {
			i = 0;
			raw = (Object[]) detailList.next();
			tempDetail = new StockDetail();
			tempDetail.setMaterialId((Long) raw[i++]);
			tempDetail.setMaterialName2((String) raw[i++]);
			tempDetail.setQuantity2((Integer) raw[i++]);
			tempDetail.setMeasureUnit2((String) raw[i++]);
			i++;
			tempDetail.setMaterialName3((String) raw[i++]);
			tempDetail.setQuantity3((Integer) raw[i++]);
			tempDetail.setMeasureUnit3((String) raw[i++]);
			result.add(tempDetail);
		}
		return result;
	}

	/**
	 * 未发生变化的情况
	 * 
	 * @param root
	 */
	private ArrayList<StockDetail> noChangeQueryByYearMonth(HashMap<String, Object> root, Integer year, Integer month) {
		StringBuilder buff = new StringBuilder();
		buff.append(" select ");
		buff.append(" b.materialId,b.materialName3, b.quantity3,b.measureUnit3 ");
		buff.append(" from StockDetail b  ");
		buff.append(" where not exists (select a.originalId from Blotters a where a.originalId=b.materialId and year(a.recordTime)=:year and month(a.recordTime)=:month  )  ");
		Query query = getDAO().getSession().createQuery(buff.toString());
		query.setInteger("year", year);
		query.setInteger("month", month);
		Iterator<?> detailList = query.list().iterator();
		ArrayList<StockDetail> result = new ArrayList<StockDetail>();
		Object raw[] = null;
		StockDetail tempDetail = null;
		int i = 0;
		while (detailList.hasNext()) {
			i = 0;
			raw = (Object[]) detailList.next();
			tempDetail = new StockDetail();
			tempDetail.setMaterialId((Long) raw[i++]);
			tempDetail.setMaterialName3((String) raw[i++]);
			tempDetail.setQuantity3((Integer) raw[i++]);
			tempDetail.setMeasureUnit3((String) raw[i++]);
			result.add(tempDetail);
		}
		return result;
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
			if (result) {
				stockDetailService.sendOut(instance);
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