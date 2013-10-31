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
import cn.ccrise.spimp.spmi.schedule.entity.Work;
import cn.ccrise.spimp.spmi.schedule.service.WorkService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Work Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class WorkController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkService workService;

	@RequestMapping(value = "/spmi/schedule/works/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(workService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/works/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, String search) throws Exception {
		Page<Work> page = new Page<Work>();
		page.setPageSize(100000);
		page = workService.pageQuery(page, startDate, endDate, search);

		String[] headers = { "日期", "领导名称", "发现问题", "问题处理" };

		HSSFWorkbook wb = new ExcelHelper<Work>().genExcel("矿值班情况 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("矿值班情况 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/schedule/works/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(workService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/work", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/work/index";
	}

	@RequestMapping(value = "/spmi/schedule/works", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Work> page, Date startDate, Date endDate, String search) {
		page = workService.pageQuery(page, startDate, endDate, search);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/works", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Work work) {
		return new Response(workService.save(work));
	}

	@RequestMapping(value = "/spmi/schedule/works/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Work work, @PathVariable long id) {
		return new Response(workService.update(work));
	}
}