/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.ccrise.spimp.spmi.car.entity.Blotters;
import cn.ccrise.spimp.spmi.car.service.BlottersService;
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

	@RequestMapping(value = "/car/material/blotters/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(blottersService.delete(id));
	}

	@RequestMapping(value = "/car/material/blotters/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search) throws Exception {
		Page<Blotters> page = new Page<Blotters>();
		page.setPageSize(100000);
		page = blottersService.pageQuery(page, search, 1);

		String[] headers = { "材料名称", "规格型号/设备号", "度量单位", "数量", "单价（元）", "操作类型", "经办人", "备注", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<Blotters>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/car/material/blotters/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(blottersService.get(id));
	}

	@RequestMapping(value = "/car/material/blotter", method = RequestMethod.GET)
	public String index() {
		return "car/material/blotters/index";
	}

	/**
	 * 入库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/material/stock_putin", method = RequestMethod.GET)
	public String putIn() {
		return "car/material/stock_putin/index";
	}

	@RequestMapping(value = "/car/material/putin", method = RequestMethod.GET)
	@ResponseBody
	public Response putinPage(Page<Blotters> page, String search) {
		page = blottersService.pageQuery(page, search, Blotters.OPERTION_TYPE_PUTIN);
		return new Response(page);
	}

	@RequestMapping(value = "/car/material/blotters", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Blotters blotters) {
		blotters.setRecordTime(new Timestamp(System.currentTimeMillis()));
		if (blotters.getOpertionType() == Blotters.OPERTION_TYPE_PUTIN) {
			return new Response(blottersService.putIn(blotters));
		} else {
			return new Response(blottersService.sendOut(blotters));
		}
	}

	/**
	 * 出库
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/material/stock_sendout", method = RequestMethod.GET)
	public String sendOut() {
		return "car/material/stock_sendout/index";
	}

	@RequestMapping(value = "/car/material/sendout", method = RequestMethod.GET)
	@ResponseBody
	public Response sendoutPage(Page<Blotters> page, String search) {
		page = blottersService.pageQuery(page, search, Blotters.OPERTION_TYPE_SENDOUT);
		return new Response(page);
	}

	/**
	 * 进销存统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/material/statistics", method = RequestMethod.GET)
	public ModelAndView statistics() {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", blottersService.staticsCurrentYearMonth());
		return new ModelAndView("car/material/statistics/index", root);
	}

	@RequestMapping(value = "/car/material/statistics_query", method = RequestMethod.GET)
	public ModelAndView statisticsQuery() {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", blottersService.staticsCurrentYearMonth());
		return new ModelAndView("car/material/statistics/result", root);
	}

	@RequestMapping(value = "/car/material/blotters/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Blotters blotters, @PathVariable long id) {
		return new Response(blottersService.update(blotters));
	}
}