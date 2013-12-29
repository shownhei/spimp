/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.spimp.electr.access.StockDAO;
import cn.ccrise.spimp.electr.entity.Stock;
import cn.ccrise.spimp.system.entity.Account;

/**
 * Stock Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class StockService extends HibernateDataServiceImpl<Stock, Long> {
	@Autowired
	private StockDAO stockDAO;
	@Autowired
	private BlottersService blottersService;
	@Autowired
	private StockDetailService stockDetailService;

	public boolean deleteStock(Long id) {

		// 删除stockdetail
		// 删除流水表Blotters
		// 删除库存表
		stockDetailService.getDAO().getSession().createQuery("delete from StockDetail d where d.materialId=" + id)
				.executeUpdate();
		stockDetailService.getDAO().getSession().createQuery("delete from Blotters b where b.originalId=" + id)
				.executeUpdate();
		return delete(id);
	}

	@Override
	public HibernateDAO<Stock, Long> getDAO() {
		return stockDAO;
	}

	public Page<Stock> pageQuery(Page<Stock> page, String search, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("materialName", search, MatchMode.ANYWHERE),
					Restrictions.ilike("model", search, MatchMode.ANYWHERE)));
		}
		criterions.add(Restrictions.eq("recordGroup", loginAccount.getGroupEntity()));
		return getPage(page, criterions.toArray(new Criterion[0]));
	}

	/**
	 * 入库
	 * 
	 * @return
	 */
	public boolean putIn(Long id, Integer count) {// + new
													// Date(System.currentTimeMillis())
		String sql = "update Stock a set a.amount=a.amount+" + count + ", a.updateTime=:updateTime" + " where a.id="
				+ id;
		logger.debug(sql);
		Query query = getDAO().getSession().createQuery(sql);
		query.setDate("updateTime", new Timestamp(System.currentTimeMillis()));
		return query.executeUpdate() > 0 ? true : false;
	}

	/***
	 * 出库
	 * 
	 * @return
	 */
	public boolean sendOut(Long id, Integer count) {
		String sql = "update Stock a set a.amount=a.amount-" + count + ",a.updateTime=:updateTime where a.id=" + id
				+ " and a.amount>=" + count;
		Query tQuery = getDAO().getSession().createQuery(sql);
		tQuery.setTimestamp("updateTime", new Timestamp(System.currentTimeMillis()));
		return tQuery.executeUpdate() > 0 ? true : false;
	}
}