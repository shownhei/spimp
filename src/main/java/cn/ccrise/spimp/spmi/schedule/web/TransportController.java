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
import cn.ccrise.spimp.spmi.schedule.entity.Transport;
import cn.ccrise.spimp.spmi.schedule.service.TransportService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Transport Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class TransportController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TransportService transportService;

	@RequestMapping(value = "/spmi/schedule/transports/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(transportService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/transports/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(transportService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/transport", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/transport/index";
	}

	@RequestMapping(value = "/spmi/schedule/transports", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Transport> page, Date startDate, Date endDate,Long coalType) {
		page = transportService.pageQuery(page, startDate, endDate, coalType);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/transports", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Transport transport) {
		return new Response(transportService.save(transport));
	}

	@RequestMapping(value = "/spmi/schedule/transports/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Transport transport, @PathVariable long id) {
		return new Response(transportService.update(transport));
	}
	
	@RequestMapping(value = "/spmi/schedule/transports/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate,Long coalType) throws Exception {
		Page<Transport> page = new Page<Transport>();
		page.setPageSize(100000);
		page = transportService.pageQuery(page, startDate, endDate, coalType);
		
		String[] headers = {"日期","煤种","铁路运输车数","铁路运输吨数","铁路装车时间","铁路装完时间","铁路运输备注","公路运输车数","公路运输吨数","公路外运合计","公路运输库存","公路运输备注"};
		
		HSSFWorkbook wb = new ExcelHelper<Transport>().genExcel("煤炭外运情况 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");    
        response.setContentType("application/force-download");
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("煤炭外运情况 - 安全生产综合管理平台", "UTF-8")
				+ ".xls");
		
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();  
	}
}