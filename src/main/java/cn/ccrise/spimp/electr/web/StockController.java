/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.criterion.MatchMode;
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

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Stock;
import cn.ccrise.spimp.electr.service.StockService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Stock Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class StockController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockService stockService;

	@RequestMapping(value = "/electr/material/stocks/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(stockService.delete(id));
	}

	@RequestMapping(value = "/electr/material/stocks/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search) throws Exception {
		Page<Stock> page = new Page<Stock>();
		page.setPageSize(100000);
		page = stockService.pageQuery(page, search);

		String[] headers = { "材料名称", "规格型号/设备号", "度量单位", "数量", "单价（元）", "备注", "更新时间" };

		HSSFWorkbook wb = new ExcelHelper<Stock>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/electr/material/stocks/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(stockService.get(id));
	}

	@RequestMapping(value = "/electr/material/stocks", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Stock> page, String search) {
		page = stockService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/material/stocks/listname", method = RequestMethod.GET)
	@ResponseBody
	public Response pageListName(Page<Stock> page, String q) {
		return new Response(stockService.getPage(page, Restrictions.ilike("materialName", q, MatchMode.ANYWHERE)));
	}

	@RequestMapping(value = "/electr/material/stocks", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Stock stock) {
		return new Response(stockService.save(stock));
	}

	@RequestMapping(value = "/electr/material/stocks/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Stock stock, @PathVariable long id) {
		return new Response(stockService.update(stock));
	}
}