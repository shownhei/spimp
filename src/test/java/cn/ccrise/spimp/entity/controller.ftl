/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package ${packageName}.web;

import java.io.OutputStream;
import java.net.URLEncoder;
<#if dateQuery>
import java.sql.Date;
</#if>

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
import ${packageName}.entity.${entityName};
import ${packageName}.service.${entityName}Service;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * ${entityName} Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class ${entityName}Controller {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ${entityName}Service ${entityName?uncap_first}Service;

	@RequestMapping(value = "${restPath}/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(${entityName?uncap_first}Service.delete(id));
	}

	@RequestMapping(value = "${restPath}/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(${entityName?uncap_first}Service.get(id));
	}

	@RequestMapping(value = "/${path}", method = RequestMethod.GET)
	public String index() {
		return "${path}/index";
	}

	@RequestMapping(value = "${restPath}", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<${entityName}> page${queryParamsWithType}) {
		page = ${entityName?uncap_first}Service.pageQuery(page${queryParam});
		return new Response(page);
	}

	@RequestMapping(value = "${restPath}", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ${entityName} ${entityName?uncap_first}) {
		return new Response(${entityName?uncap_first}Service.save(${entityName?uncap_first}));
	}

	@RequestMapping(value = "${restPath}/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ${entityName} ${entityName?uncap_first}, @PathVariable long id) {
		return new Response(${entityName?uncap_first}Service.update(${entityName?uncap_first}));
	}
	
	@RequestMapping(value = "${restPath}/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response${queryParamsWithType}) throws Exception {
		Page<${entityName}> page = new Page<${entityName}>();
		page.setPageSize(100000);
		page = ${entityName?uncap_first}Service.pageQuery(page${queryParam});
		
		String[] headers = {${headers}};
		
		HSSFWorkbook wb = new ExcelHelper<${entityName}>().genExcel("${pageTitle}", headers, page.getResult(), "yyyy-MM-dd");    
        response.setContentType("application/force-download");
        response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("${pageTitle}", "UTF-8")
				+ ".xls");
		
        OutputStream ouputStream = response.getOutputStream();    
        wb.write(ouputStream);    
        ouputStream.flush();    
        ouputStream.close();  
	}
}