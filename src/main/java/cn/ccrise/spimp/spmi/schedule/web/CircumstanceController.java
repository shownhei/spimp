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
import cn.ccrise.spimp.spmi.schedule.entity.Circumstance;
import cn.ccrise.spimp.spmi.schedule.service.CircumstanceService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Circumstance Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class CircumstanceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CircumstanceService circumstanceService;

	@RequestMapping(value = "/spmi/schedule/circumstances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(circumstanceService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/circumstances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(circumstanceService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/circumstance", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/circumstance/index";
	}

	@RequestMapping(value = "/spmi/schedule/circumstances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Circumstance> page, Date startDate, Date endDate,Long duty,String search) {
		page = circumstanceService.pageQuery(page, startDate, endDate, duty, search);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/circumstances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Circumstance circumstance) {
		return new Response(circumstanceService.save(circumstance));
	}

	@RequestMapping(value = "/spmi/schedule/circumstances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Circumstance circumstance, @PathVariable long id) {
		return new Response(circumstanceService.update(circumstance));
	}
	
	@RequestMapping(value = "/spmi/schedule/circumstances/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate,Long duty,String search) throws Exception {
		Page<Circumstance> page = new Page<Circumstance>();
		page.setPageSize(100000);
		page = circumstanceService.pageQuery(page, startDate, endDate, duty, search);
		
		String[] headers = {"日期","班次","姓名","职务","下井时间","升井时间","汇报地点","内容","发现问题","处理意见"};
		
		HSSFWorkbook wb = new ExcelHelper<Circumstance>().genExcel("基层单位干部跟班情况 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");    
        response.setContentType("application/force-download");
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("基层单位干部跟班情况 - 安全生产综合管理平台", "UTF-8")
				+ ".xls");
		
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();  
	}
}