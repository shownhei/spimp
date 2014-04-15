/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;

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
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.TeamCulture;
import cn.ccrise.spimp.electr.service.TeamCultureService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * TeamCulture Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class TeamCultureController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TeamCultureService teamCultureService;

	@RequestMapping(value = "/electr/team-cultures/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id,HttpSession httpSession) {
		return new Response(teamCultureService.deleteCulture(id,httpSession));
	}

	@RequestMapping(value = "/electr/team-cultures/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(teamCultureService.get(id));
	}



	@RequestMapping(value = "/electr/team-cultures", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<TeamCulture> page,String search, Date startDate, Date endDate) {
		page = teamCultureService.pageQuery(page, search, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/team-cultures", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody TeamCulture teamCulture) {
		return new Response(teamCultureService.save(teamCulture));
	}

	@RequestMapping(value = "/electr/team-cultures/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody TeamCulture teamCulture, @PathVariable long id) {
		return new Response(teamCultureService.update(teamCulture));
	}
	
	@RequestMapping(value = "/electr/team-cultures/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response,String search, Date startDate, Date endDate) throws Exception {
		Page<TeamCulture> page = new Page<TeamCulture>();
		page.setPageSize(100000);
		page = teamCultureService.pageQuery(page, search, startDate, endDate);
		
		String[] headers = {"主题","内容","班组","日期","附件","备注","参与人员"};
		
		HSSFWorkbook wb = new ExcelHelper<TeamCulture>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");    
        response.setContentType("application/force-download");
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8")
				+ ".xls");
		
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();  
	}
}