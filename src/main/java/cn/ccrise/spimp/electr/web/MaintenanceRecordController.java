/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.sql.Timestamp;

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
import cn.ccrise.spimp.electr.entity.MaintenanceRecord;
import cn.ccrise.spimp.electr.service.MaintenanceRecordService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaintenanceRecord Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaintenanceRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaintenanceRecordService maintenanceRecordService;

	@RequestMapping(value = "/electr/maintenance/records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceRecordService.delete(id));
	}

	@RequestMapping(value = "/electr/maintenance/records/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, Long car) throws Exception {
		Page<MaintenanceRecord> page = new Page<MaintenanceRecord>();
		page.setPageSize(100000);
		page = maintenanceRecordService.pageQuery(page, startDate, endDate, car);

		String[] headers = { "维修日期", "维修车辆", "故障表现/原因", "处理方法", "备注", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<MaintenanceRecord>().genExcel("维修记录管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("维修记录管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/maintenance/records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceRecordService.get(id));
	}

	@RequestMapping(value = "/electr/maintenance/records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaintenanceRecord> page, Date startDate, Date endDate, Long car) {
		page = maintenanceRecordService.pageQuery(page, startDate, endDate, car);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/maintenance/records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaintenanceRecord maintenanceRecord) {
		maintenanceRecord.setRecordDateTime(new Timestamp(System.currentTimeMillis()));
		return new Response(maintenanceRecordService.save(maintenanceRecord));
	}

	@RequestMapping(value = "/electr/maintenance/records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaintenanceRecord maintenanceRecord, @PathVariable long id) {
		return new Response(maintenanceRecordService.update(maintenanceRecord));
	}
}