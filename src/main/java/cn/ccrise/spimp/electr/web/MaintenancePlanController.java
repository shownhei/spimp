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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.MaintenancePlan;
import cn.ccrise.spimp.electr.service.MaintenancePlanService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaintenancePlan Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaintenancePlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaintenancePlanService maintenancePlanService;

	@RequestMapping(value = "/electr/equipment/maintenance-plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenancePlanService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/maintenance-plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenancePlanService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/maintenance-plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaintenancePlan> page, Long project, Date startDate, Date endDate) {
		page = maintenancePlanService.pageQuery(page, project, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/maintenance-plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaintenancePlan maintenancePlan) {
		return new Response(maintenancePlanService.save(maintenancePlan));
	}

	@RequestMapping(value = "/electr/equipment/maintenance-plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaintenancePlan maintenancePlan, @PathVariable long id) {
		return new Response(maintenancePlanService.update(maintenancePlan));
	}

	@RequestMapping(value = "/electr/equipment/maintenance-plans/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long project, Date startDate, Date endDate) throws Exception {
		Page<MaintenancePlan> page = new Page<MaintenancePlan>();
		page.setPageSize(100000);
		page = maintenancePlanService.pageQuery(page, project, startDate, endDate);

		String[] headers = { "检修内容", "检修内容", "检修标准", "检查时间", "负责人" };

		HSSFWorkbook wb = new ExcelHelper<MaintenancePlan>().genExcel("检修计划管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("检修计划管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}