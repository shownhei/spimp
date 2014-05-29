/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.web;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.document.entity.BasicInfomation;
import cn.ccrise.spimp.spmi.document.service.BasicInfomationService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * BasicInfomation Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class BasicInfomationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BasicInfomationService basicInfomationService;

	@RequestMapping(value = "/spmi/document/basic-infomations/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(basicInfomationService.delete(id));
	}

	@RequestMapping(value = "/spmi/document/basic-infomations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(basicInfomationService.get(id));
	}

	@RequestMapping(value = "/spmi/document/basic-infomation", method = RequestMethod.GET)
	public String index() {
		return "spmi/document/basic-infomation/index";
	}

	@RequestMapping(value = "/ignore/document/basic-infomations", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView page(Page<BasicInfomation> page) {
		HashMap<String,Object> root = new HashMap<String,Object>();
		page.setPageSize(20);
		page.setPageNumber(1);
		page.setOrder("desc");
		page.setOrderBy("id");
		page = basicInfomationService.pageQuery(page);
		if(page.getResult().size()>0){
			String temp=page.getResult().get(0).getBasicContent();
			root.put("content", temp);
		}else{
			root.put("content", "");
		}
		return new ModelAndView("3d/basicInfoContent",root);
	}

	@RequestMapping(value = "/ignore/document/basic-infomations", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@RequestBody String content) {
		BasicInfomation instance = new BasicInfomation();
		try {
			String decodeStr = java.net.URLDecoder.decode(content, "UTF-8");
			instance.setBasicContent(decodeStr);
			instance.setAddTime(new Timestamp(System.currentTimeMillis()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new Response(basicInfomationService.save(instance));
	}

	@RequestMapping(value = "/spmi/document/basic-infomations/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody BasicInfomation basicInfomation, @PathVariable long id) {
		return new Response(basicInfomationService.update(basicInfomation));
	}
	
	@RequestMapping(value = "/spmi/document/basic-infomations/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		Page<BasicInfomation> page = new Page<BasicInfomation>();
		page.setPageSize(100000);
		page = basicInfomationService.pageQuery(page);
		
		String[] headers = {"内容","更新时间"};
		
		HSSFWorkbook wb = new ExcelHelper<BasicInfomation>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");    
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