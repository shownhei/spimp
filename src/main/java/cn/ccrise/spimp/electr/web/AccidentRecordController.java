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
import cn.ccrise.spimp.electr.entity.AccidentRecord;
import cn.ccrise.spimp.electr.service.AccidentRecordService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * AccidentRecord Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class AccidentRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccidentRecordService accidentRecordService;

	@RequestMapping(value = "/electr/accident/accident-records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(accidentRecordService.delete(id));
	}

	@RequestMapping(value = "/electr/accident/accident-records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(accidentRecordService.get(id));
	}

	@RequestMapping(value = "/electr/accident/accident-records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<AccidentRecord> page, Date startDate, Date endDate) {
		page = accidentRecordService.pageQuery(page, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/accident/accident-records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody AccidentRecord accidentRecord) {
		accidentRecord.setRecordTime(new Timestamp(System.currentTimeMillis()));
		return new Response(accidentRecordService.save(accidentRecord));
	}

	@RequestMapping(value = "/electr/accident/accident-records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody AccidentRecord accidentRecord, @PathVariable long id) {
		return new Response(accidentRecordService.update(accidentRecord));
	}

	@RequestMapping(value = "/electr/accident/accident-records/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate) throws Exception {
		Page<AccidentRecord> page = new Page<AccidentRecord>();
		page.setPageSize(100000);
		page = accidentRecordService.pageQuery(page, startDate, endDate);

		String[] headers = { "事故地点", "事故描述", "上报人", "事故类型", "事故日期", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<AccidentRecord>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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
}