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
import cn.ccrise.spimp.spmi.schedule.entity.Record;
import cn.ccrise.spimp.spmi.schedule.service.RecordService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Record Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RecordService recordService;

	@RequestMapping(value = "/spmi/schedule/records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(recordService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/records/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, Long team, Long duty)
			throws Exception {
		Page<Record> page = new Page<Record>();
		page.setPageSize(100000);
		page = recordService.pageQuery(page, startDate, endDate, team, duty);

		String[] headers = { "日期", "时间", "队组", "班次", "地点", "汇报人", "接收人", "事故问题详情", "处理意见", "处理结果", "验收人" };

		HSSFWorkbook wb = new ExcelHelper<Record>().genExcel("调度记录 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("调度记录 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/schedule/records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(recordService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/record", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/record/index";
	}

	@RequestMapping(value = "/spmi/schedule/records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Record> page, Date startDate, Date endDate, Long team, Long duty) {
		page = recordService.pageQuery(page, startDate, endDate, team, duty);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Record record) {
		return new Response(recordService.save(record));
	}

	@RequestMapping(value = "/spmi/schedule/records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Record record, @PathVariable long id) {
		return new Response(recordService.update(record));
	}
}