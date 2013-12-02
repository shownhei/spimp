/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

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
import cn.ccrise.spimp.spmi.car.entity.RunLog;
import cn.ccrise.spimp.spmi.car.service.RunLogService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * RunLog Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RunLogController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RunLogService runLogService;

	@RequestMapping(value = "/car/runlog/run-logs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(runLogService.delete(id));
	}

	@RequestMapping(value = "/car/runlog/run-logs/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long car, String search, Date startDate, Date endDate)
			throws Exception {
		Page<RunLog> page = new Page<RunLog>();
		page.setPageSize(100000);
		page = runLogService.pageQuery(page, car, search, startDate, endDate);

		String[] headers = { "车号", "班次 ", "车次 ", "路程 ", "加油数 ", "备注 ", "记录日期 " };

		HSSFWorkbook wb = new ExcelHelper<RunLog>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/car/runlog/run-logs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(runLogService.get(id));
	}

	@RequestMapping(value = "/car/runlog/runlog", method = RequestMethod.GET)
	public String index() {
		return "car/runlog/index";
	}

	@RequestMapping(value = "/car/runlog/run-logs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<RunLog> page, Long car, String search, Date startDate, Date endDate) {
		page = runLogService.pageQuery(page, car, search, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/car/runlog/run-logs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RunLog runLog) {
		runLog.setRecordTime(new Timestamp(System.currentTimeMillis()));
		return new Response(runLogService.save(runLog));
	}

	@RequestMapping(value = "/car/runlog/run-logs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RunLog runLog, @PathVariable long id) {
		return new Response(runLogService.update(runLog));
	}
}