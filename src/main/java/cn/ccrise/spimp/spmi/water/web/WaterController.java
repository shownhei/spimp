/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.water.web;

import java.io.OutputStream;
import java.net.URLEncoder;

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
import cn.ccrise.spimp.spmi.water.entity.Water;
import cn.ccrise.spimp.spmi.water.service.WaterService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Water Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class WaterController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WaterService waterService;

	@RequestMapping(value = "/spmi/waters/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(waterService.delete(id));
	}

	@RequestMapping(value = "/spmi/waters/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String startDate, String endDate, String search)
			throws Exception {
		Page<Water> page = new Page<Water>();
		page.setPageSize(100000);
		page = waterService.pageQuery(page, startDate, endDate, search);

		String[] headers = { "时间", "地点", "涌出量", "状况", "组织处理情况" };

		HSSFWorkbook wb = new ExcelHelper<Water>().genExcel("防治水信息管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("防治水信息管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/waters/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(waterService.get(id));
	}

	@RequestMapping(value = "/spmi/water", method = RequestMethod.GET)
	public String index() {
		return "spmi/water/index";
	}

	@RequestMapping(value = "/spmi/waters", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Water> page, String startDate, String endDate, String search) {
		page = waterService.pageQuery(page, startDate, endDate, search);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/waters", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Water water) {
		return new Response(waterService.save(water));
	}

	@RequestMapping(value = "/spmi/waters/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Water water, @PathVariable long id) {
		return new Response(waterService.update(water));
	}
}