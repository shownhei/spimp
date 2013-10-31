/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.web;

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
import cn.ccrise.spimp.spmi.instruction.entity.Focus;
import cn.ccrise.spimp.spmi.instruction.service.FocusService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Focus Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class FocusController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FocusService focusService;

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(focusService.delete(id));
	}

	@RequestMapping(value = "/spmi/instruction/focuses/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Date startDate, Date endDate) throws Exception {
		Page<Focus> page = new Page<Focus>();
		page.setPageSize(100000);
		page = focusService.pageQuery(page, search, startDate, endDate);

		String[] headers = { "工作名称", "开始时间", "结束时间", "地点", "现场负责人", "工作人员", "工作进度", "工作总结情况", "工作简述", "记录人" };

		HSSFWorkbook wb = new ExcelHelper<Focus>().genExcel("重点工作 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("重点工作 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(focusService.get(id));
	}

	@RequestMapping(value = "/spmi/instruction/focus", method = RequestMethod.GET)
	public String index() {
		return "spmi/instruction/focus/index";
	}

	@RequestMapping(value = "/spmi/instruction/focuses", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Focus> page, String search, Date startDate, Date endDate) {
		page = focusService.pageQuery(page, search, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/instruction/focuses", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Focus focus) {
		return new Response(focusService.save(focus));
	}

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Focus focus, @PathVariable long id) {
		return new Response(focusService.update(focus));
	}
}