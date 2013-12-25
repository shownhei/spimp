/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.StockDetail;
import cn.ccrise.spimp.electr.service.StockDetailService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 库存明细
 * 
 */
@Controller
public class StockDetailController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StockDetailService stockDetailService;

	@RequestMapping(value = "/electr/stock-details/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(stockDetailService.delete(id));
	}

	@RequestMapping(value = "/electr/stock-details/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, HttpSession httpSession) throws Exception {
		Page<StockDetail> page = new Page<StockDetail>();
		page.setPageSize(100000);
		page = stockDetailService.pageQuery(page, search, httpSession);

		String[] headers = { "材料名称", "度量单位", "数量" };

		HSSFWorkbook wb = new ExcelHelper<StockDetail>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/electr/stock-details/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(stockDetailService.get(id));
	}

	@RequestMapping(value = "/electr/stock-detail", method = RequestMethod.GET)
	public String index() {
		return "spmi/electr/stockdetail/index";
	}

	@RequestMapping(value = "/electr/stock-details", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<StockDetail> page, String search, HttpSession httpSession) {
		page = stockDetailService.pageQuery(page, search, httpSession);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/stock-details", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody StockDetail stockDetail, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		stockDetail.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(stockDetailService.save(stockDetail));
	}

	@RequestMapping(value = "/electr/stock-details/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody StockDetail stockDetail, @PathVariable long id, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		stockDetail.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(stockDetailService.update(stockDetail));
	}
}