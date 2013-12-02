/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

import java.io.OutputStream;
import java.net.URLEncoder;

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
import cn.ccrise.spimp.spmi.car.entity.MaintenanceDetail;
import cn.ccrise.spimp.spmi.car.service.MaintenanceDetailService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaintenanceDetail Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaintenanceDetailController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaintenanceDetailService maintenanceDetailService;

	@RequestMapping(value = "/car/maintenance-details/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceDetailService.delete(id));
	}

	@RequestMapping(value = "/car/maintenance/maintenance-details/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		Page<MaintenanceDetail> page = new Page<MaintenanceDetail>();
		page.setPageSize(100000);
		page = maintenanceDetailService.pageQuery(page);

		String[] headers = { "检查维修项目", "保养方式", "检修处理情况", "备注" };

		HSSFWorkbook wb = new ExcelHelper<MaintenanceDetail>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/car/maintenance-details/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceDetailService.get(id));
	}

	@RequestMapping(value = "/car/maintenance-detail", method = RequestMethod.GET)
	public String index() {
		return "car/maintenance-detail/index";
	}

	@RequestMapping(value = "/car/maintenance/maintenance-details", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaintenanceDetail> page) {
		page = maintenanceDetailService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/car/maintenance/maintenance-details", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaintenanceDetail maintenanceDetail) {
		return new Response(maintenanceDetailService.save(maintenanceDetail));
	}

	@RequestMapping(value = "/car/maintenance/maintenance-details/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaintenanceDetail maintenanceDetail, @PathVariable long id) {
		return new Response(maintenanceDetailService.update(maintenanceDetail));
	}
}