/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Blotters;
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.service.BlottersService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Blotters Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class BlottersController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BlottersService blottersService;

	@RequestMapping(value = "/electr/material/blotters/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(blottersService.delete(id));
	}

	@RequestMapping(value = "/electr/material/blotters/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, Integer year, Integer month)
			throws Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		root.put("month", month);
		blottersService.stockChangeDetail(root, year, month, httpSession);
		new ExcelHelper<MaterialsPlan>().genExcelWithTel(httpSession, response, "electr/blotters.xls", root,
				"进货使用剩余量明细表", new String[] { "进货使用剩余量明细表" });
	}

	@RequestMapping(value = "/electr/material/blotters/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(blottersService.get(id));
	}

	@RequestMapping(value = "/electr/material/blotter", method = RequestMethod.GET)
	public String index() {
		return "electr/material/blotters/index";
	}

	/**
	 * 入库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/material/stock_putin", method = RequestMethod.GET)
	public String putIn() {
		return "electr/material/stock_putin/index";
	}

	@RequestMapping(value = "/electr/material/putin", method = RequestMethod.GET)
	@ResponseBody
	public Response putinPage(Page<Blotters> page, String search, HttpSession httpSession) {
		page = blottersService.pageQuery(page, search, Blotters.OPERTION_TYPE_PUTIN, httpSession);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/material/blotters", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Blotters blotters, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		blotters.setRecordGroup(loginAccount.getGroupEntity());
		blotters.setRecordTime(new Timestamp(System.currentTimeMillis()));
		if (StringUtils.isBlank(blotters.getOperator())) {
			blotters.setOperator(loginAccount.getRealName());
		}
		if (blotters.getOpertionType() == Blotters.OPERTION_TYPE_PUTIN) {
			return new Response(blottersService.putIn(blotters, httpSession));
		} else {
			return new Response(blottersService.sendOut(blotters, httpSession));
		}
	}

	/**
	 * 出库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/material/stock_sendout", method = RequestMethod.GET)
	public String sendOut() {
		return "electr/material/stock_sendout/index";
	}

	@RequestMapping(value = "/electr/material/sendout", method = RequestMethod.GET)
	@ResponseBody
	public Response sendoutPage(Page<Blotters> page, String search, HttpSession httpSession) {
		page = blottersService.pageQuery(page, search, Blotters.OPERTION_TYPE_SENDOUT, httpSession);
		return new Response(page);
	}

	/**
	 * 进销存统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/material/statistics", method = RequestMethod.GET)
	public ModelAndView statistics(HttpSession httpSession) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", blottersService.staticsCurrentYearMonth());
		return new ModelAndView("electr/material/statistics/index", root);
	}

	@RequestMapping(value = "/electr/material/statistics_query", method = RequestMethod.GET)
	public ModelAndView statisticsQuery(Integer year, Integer month, HttpSession httpSession) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		root.put("month", month);
		blottersService.stockChangeDetail(root, year, month, httpSession);
		// 出库的
		return new ModelAndView("electr/material/statistics/result", root);
	}

	@RequestMapping(value = "/electr/material/blotters/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Blotters blotters, @PathVariable long id, HttpSession httpSession) {
		return new Response(blottersService.update(blotters));
	}
}