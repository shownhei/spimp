/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

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
import cn.ccrise.spimp.spmi.car.entity.Problem;
import cn.ccrise.spimp.spmi.car.service.ProblemService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Problem Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class ProblemController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ProblemService problemService;

	@RequestMapping(value = "/car/maintenance/problems/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(problemService.delete(id));
	}

	@RequestMapping(value = "/car/maintenance/problems/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate) throws Exception {
		Page<Problem> page = new Page<Problem>();
		page.setPageSize(100000);
		page = problemService.pageQuery(page, startDate, endDate);

		String[] headers = { "上报日期", "故障车辆", "故障说明", "上报人" };

		HSSFWorkbook wb = new ExcelHelper<Problem>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/car/maintenance/problems/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(problemService.get(id));
	}

	@RequestMapping(value = "/car/maintenance/problem", method = RequestMethod.GET)
	public String index() {
		return "car/maintenance/problem/index";
	}

	@RequestMapping(value = "/car/maintenance/problems", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Problem> page, Date startDate, Date endDate) {
		page = problemService.pageQuery(page, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/car/maintenance/problems", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Problem problem) {
		return new Response(problemService.save(problem));
	}

	@RequestMapping(value = "/car/maintenance/problems/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Problem problem, @PathVariable long id) {
		return new Response(problemService.update(problem));
	}
}