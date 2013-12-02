/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

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
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.car.entity.MaterialsPlan;
import cn.ccrise.spimp.spmi.car.service.MaterialsPlanDetailService;
import cn.ccrise.spimp.spmi.car.service.MaterialsPlanService;
import cn.ccrise.spimp.spmi.car.service.StockDetailService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaterialsPlan Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
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

	@RequestMapping(value = "/car/material/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(materialsPlanService.deletePlan(id));
	}

	@RequestMapping(value = "/car/material/plans/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, String year, String month)
			throws Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("yearMonth", year + "年" + month + "月");
		MaterialsPlan plan = materialsPlanService.findUniqueBy("planDate", year + "-" + month);
		root.put("plan", plan);
		root.put("result", materialsPlanDetailService.find(Restrictions.eq("plan", plan)));
		new ExcelHelper<MaterialsPlan>().genExcelWithTel(httpSession, response,
				"WEB-INF/template/car/material_plan.xls", root, "导出文档", new String[] { "第一个sheet" });
	}

	@RequestMapping(value = "/car/material/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(materialsPlanService.get(id));
	}

	@RequestMapping(value = "/car/material/getplan", method = RequestMethod.GET)
	public ModelAndView getPlan(Long planId, String yearMonth) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		Long mainId = null;
		if (planId == null) {
			if (StringUtils.isNotBlank(yearMonth)) {
				List<MaterialsPlan> plans = materialsPlanService.find(Restrictions.eq("planDate", yearMonth));
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
		return new ModelAndView("car/material/plan/plan", root);
	}

	@RequestMapping(value = "/car/material/plan", method = RequestMethod.GET)
	public String index() {
		return "car/material/plan/index";
	}

	@RequestMapping(value = "/car/material/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaterialsPlan> page, Date startDate, Date endDate, String search) {
		page = materialsPlanService.pageQuery(page, startDate, endDate, search);
		return new Response(page);
	}

	@RequestMapping(value = "/car/material/planslist", method = RequestMethod.GET)
	@ResponseBody
	public Response pageList(Page<MaterialsPlan> page, Date startDate, Date endDate, String search) {
		page = materialsPlanService.pageQuery(page, startDate, endDate, search);
		return new Response(page.getResult());
	}

	@RequestMapping(value = "/car/material/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaterialsPlan materialsPlan) {
		materialsPlanService.save(materialsPlan);
		return new Response(materialsPlan);
	}

	@RequestMapping(value = "/car/material/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaterialsPlan materialsPlan, @PathVariable long id) {
		return new Response(materialsPlanService.update(materialsPlan));
	}
}