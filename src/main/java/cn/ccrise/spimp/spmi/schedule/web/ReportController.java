/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;

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

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.schedule.entity.Report;
import cn.ccrise.spimp.spmi.schedule.service.ReportService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Report Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class ReportController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReportService reportService;

	@RequestMapping(value = "/spmi/schedule/reports/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(reportService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/reports/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, Long duty, String search)
			throws Exception {
		Page<Report> page = new Page<Report>();
		page.setPageSize(100000);
		page = reportService.pageQuery(page, startDate, endDate, duty, search);

		String[] headers = { "日期", "班次", "姓名", "职务", "汇报时间", "汇报地点", "班前汇报", "班中汇报", "班后汇报" };

		HSSFWorkbook wb = new ExcelHelper<Report>().genExcel("安全生产三汇报 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("安全生产三汇报 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/schedule/reports/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(reportService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/report", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/report/index";
	}

	@RequestMapping(value = "/spmi/schedule/reports", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Report> page, Date startDate, Date endDate, Long duty, String search) {
		page = reportService.pageQuery(page, startDate, endDate, duty, search);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/reports", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Report report) {
		return new Response(reportService.save(report));
	}

	@RequestMapping(value = "/spmi/schedule/reports/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Report report, @PathVariable long id) {
		return new Response(reportService.update(report));
	}
}