/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Summary;
import cn.ccrise.spimp.electr.service.SummaryService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Summary Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
// @Controller
public class SummaryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SummaryService summaryService;

	@RequestMapping(value = "/electr/regulation/summaries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(summaryService.delete(id));
	}

	@RequestMapping(value = "/electr/regulation/summaries/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Long summaryType, Date startDate, Date endDate)
			throws Exception {
		Page<Summary> page = new Page<Summary>();
		page.setPageSize(100000);
		page = summaryService.pageQuery(page, search, summaryType, startDate, endDate);

		String[] headers = { "材料名称", "附件", "总结分类", "上传日期", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<Summary>().genExcel("每月总结 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("每月总结 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/regulation/summaries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(summaryService.get(id));
	}

	@RequestMapping(value = "/electr/regulation/summary", method = RequestMethod.GET)
	public String index() {
		return "electr/regulation/summary/index";
	}

	@RequestMapping(value = "/electr/regulation/summaries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Summary> page, String search, Long summaryType, Date startDate, Date endDate) {
		page = summaryService.pageQuery(page, search, summaryType, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/regulation/summaries", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Summary summary) {
		return new Response(summaryService.save(summary));
	}

	@RequestMapping(value = "/electr/regulation/summaries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Summary summary, @PathVariable long id) {
		return new Response(summaryService.update(summary));
	}
}