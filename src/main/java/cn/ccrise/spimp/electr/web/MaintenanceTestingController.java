/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;

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
import cn.ccrise.spimp.electr.entity.MaintenanceTesting;
import cn.ccrise.spimp.electr.service.MaintenanceTestingService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaintenanceTesting Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaintenanceTestingController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaintenanceTestingService maintenanceTestingService;

	@RequestMapping(value = "/electr/maintenance/maintenance-testings/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceTestingService.delete(id));
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testings/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, Long car, String search,
			HttpSession httpSession) throws Exception {
		Page<MaintenanceTesting> page = new Page<MaintenanceTesting>();
		page.setPageSize(100000);
		page = maintenanceTestingService.pageQuery(page, startDate, endDate, car, search, httpSession);

		String[] headers = { "维修日期", "维修车辆", "故障表现/原因", "处理方法", "备注", "维修工", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<MaintenanceTesting>().genExcel("故障管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceTestingService.get(id));
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testing", method = RequestMethod.GET)
	public String index() {
		return "electr/maintenance/maintenance-testing/index";
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testings", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaintenanceTesting> page, Date startDate, Date endDate, Long car, String search,
			HttpSession httpSession) {
		page = maintenanceTestingService.pageQuery(page, startDate, endDate, car, search, httpSession);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testings", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaintenanceTesting maintenanceTesting, HttpSession httpSession) {
		maintenanceTesting.setRecordDateTime(new Timestamp(System.currentTimeMillis()));
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		maintenanceTesting.setMaintenanceGroup(loginAccount.getGroupEntity());
		return new Response(maintenanceTestingService.save(maintenanceTesting));
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-testings/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaintenanceTesting maintenanceTesting, @PathVariable long id,
			HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		maintenanceTesting.setMaintenanceGroup(loginAccount.getGroupEntity());
		return new Response(maintenanceTestingService.update(maintenanceTesting));
	}
}