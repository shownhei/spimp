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
import cn.ccrise.spimp.spmi.schedule.entity.Dig;
import cn.ccrise.spimp.spmi.schedule.service.DigService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Dig Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class DigController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DigService digService;

	@RequestMapping(value = "/spmi/schedule/digs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(digService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/digs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(digService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/dig", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/dig/index";
	}

	@RequestMapping(value = "/spmi/schedule/digs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Dig> page, Date startDate, Date endDate,Long duty,Long team) {
		page = digService.pageQuery(page, startDate, endDate, duty, team);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/digs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Dig dig) {
		return new Response(digService.save(dig));
	}

	@RequestMapping(value = "/spmi/schedule/digs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Dig dig, @PathVariable long id) {
		return new Response(digService.update(dig));
	}
	
	@RequestMapping(value = "/spmi/schedule/digs/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate,Long duty,Long team) throws Exception {
		Page<Dig> page = new Page<Dig>();
		page.setPageSize(100000);
		page = digService.pageQuery(page, startDate, endDate, duty, team);
		
		String[] headers = {"日期","班次","队组","巷道类型","工作地点","跟班队干","当班班长","安全员","计划出勤人数","实际出勤人数","产量计划吨数","产量实际吨数"};
		
		HSSFWorkbook wb = new ExcelHelper<Dig>().genExcel("矿井掘进进尺 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");    
        response.setContentType("application/force-download");
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("矿井掘进进尺 - 安全生产综合管理平台", "UTF-8")
				+ ".xls");
		
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();  
	}
}