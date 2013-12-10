/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

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
import cn.ccrise.spimp.electr.entity.Overhaul;
import cn.ccrise.spimp.electr.service.OverhaulService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Overhaul Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class OverhaulController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private OverhaulService overhaulService;

	@RequestMapping(value = "/electr/equipment/overhauls/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(overhaulService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/overhauls/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(overhaulService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/overhauls", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Overhaul> page, Date startDate, Date endDate, String search) {
		page = overhaulService.pageQuery(page, startDate, endDate, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/overhauls", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Overhaul overhaul) {
		return new Response(overhaulService.save(overhaul));
	}

	@RequestMapping(value = "/electr/equipment/overhauls/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Overhaul overhaul, @PathVariable long id) {
		return new Response(overhaulService.update(overhaul));
	}

	@RequestMapping(value = "/electr/equipment/overhauls/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, String search) throws Exception {
		Page<Overhaul> page = new Page<Overhaul>();
		page.setPageSize(100000);
		page = overhaulService.pageQuery(page, startDate, endDate, search);

		String[] headers = { "检修日期", "检修位置", "负责人", "材料名称", "存在问题", "遗留问题", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<Overhaul>().genExcel("检修记录管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("检修记录管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}