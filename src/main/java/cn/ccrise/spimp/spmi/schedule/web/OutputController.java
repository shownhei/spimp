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
import cn.ccrise.spimp.spmi.schedule.entity.Output;
import cn.ccrise.spimp.spmi.schedule.service.OutputService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Output Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class OutputController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OutputService outputService;

	@RequestMapping(value = "/spmi/schedule/outputs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(outputService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/outputs/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, Long duty) throws Exception {
		Page<Output> page = new Page<Output>();
		page.setPageSize(100000);
		page = outputService.pageQuery(page, startDate, endDate, duty);

		String[] headers = { "开采日期", "班次", "队组", "开采方式", "工作面", "工作地点", "跟班队干", "当班班长", "安全员", "计划出勤人数", "实际出勤人数",
				"产量计划吨数", "产量实际吨数", "开机时间" };

		HSSFWorkbook wb = new ExcelHelper<Output>().genExcel("矿井原煤产量 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("矿井原煤产量 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/schedule/outputs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(outputService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/output", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/output/index";
	}

	@RequestMapping(value = "/spmi/schedule/outputs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Output> page, Date startDate, Date endDate, Long duty) {
		page = outputService.pageQuery(page, startDate, endDate, duty);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/outputs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Output output) {
		return new Response(outputService.save(output));
	}

	@RequestMapping(value = "/spmi/schedule/outputs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Output output, @PathVariable long id) {
		return new Response(outputService.update(output));
	}
}