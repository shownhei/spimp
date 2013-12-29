/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.service.MaterialsPlanDetailService;
import cn.ccrise.spimp.electr.service.MaterialsPlanService;
import cn.ccrise.spimp.electr.service.StockDetailService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 材料申请计划
 * 
 */
@Controller
public class MaterialsPlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private StockDetailService stockDetailService;
	@Autowired
	private MaterialsPlanService materialsPlanService;
	@Autowired
	private MaterialsPlanDetailService materialsPlanDetailService;

	@RequestMapping(value = "/electr/material/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(materialsPlanService.deletePlan(id));
	}

	@RequestMapping(value = "/electr/material/plans/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, String year, String month)
			throws Exception {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("yearMonth", year + "年" + month + "月");
		List<MaterialsPlan> plans = materialsPlanService.find(Restrictions.eq("planDate", year + "-" + month),
				Restrictions.eq("planGroup", loginAccount.getGroupEntity()));
		if (plans != null && plans.size() > 0) {
			MaterialsPlan plan = plans.get(0);
			root.put("plan", plan);
			root.put("result", materialsPlanDetailService.find(Restrictions.eq("plan", plan)));
		}
		new ExcelHelper<MaterialsPlan>().genExcelWithTel(httpSession, response, "electr/material_plan.xls", root,
				"材料申请计划", new String[] { "材料计划" });
	}

	@RequestMapping(value = "/electr/material/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(materialsPlanService.get(id));
	}

	@RequestMapping(value = "/electr/material/getplan", method = RequestMethod.GET)
	public ModelAndView getPlan(Long planId, String yearMonth, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		HashMap<String, Object> root = new HashMap<String, Object>();
		Long mainId = null;
		if (planId == null) {
			if (StringUtils.isNotBlank(yearMonth)) {
				List<MaterialsPlan> plans = materialsPlanService.find(Restrictions.eq("planDate", yearMonth),
						Restrictions.eq("planGroup", loginAccount.getGroupEntity()));
				if (plans != null && plans.size() > 0) {
					mainId = plans.get(0).getId();
				}
			}
		} else {
			mainId = planId;
		}
		root.put("plan", materialsPlanService.findUniqueBy("id", mainId));
		root.put("planId", mainId);
		root.put("details", materialsPlanDetailService.find(Restrictions.eq("plan.id", mainId)));
		return new ModelAndView("electr/material/plan/plan", root);
	}

	@RequestMapping(value = "/electr/material/plan", method = RequestMethod.GET)
	public String index() {
		return "electr/material/plan/index";
	}

	@RequestMapping(value = "/electr/material/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaterialsPlan> page, Date startDate, Date endDate, String search, HttpSession httpSession) {
		page = materialsPlanService.pageQuery(page, startDate, endDate, search, httpSession);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/material/planslist", method = RequestMethod.GET)
	@ResponseBody
	public Response pageList(Page<MaterialsPlan> page, Date startDate, Date endDate, String search,
			HttpSession httpSession) {
		page = materialsPlanService.pageQuery(page, startDate, endDate, search, httpSession);
		return new Response(page.getResult());
	}

	@RequestMapping(value = "/electr/material/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaterialsPlan materialsPlan, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		materialsPlan.setPlanGroup(loginAccount.getGroupEntity());
		materialsPlanService.save(materialsPlan);
		return new Response(materialsPlan);
	}

	@RequestMapping(value = "/electr/material/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaterialsPlan materialsPlan, @PathVariable long id) {
		return new Response(materialsPlanService.update(materialsPlan));
	}
}