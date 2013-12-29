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
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.WorkArrange;
import cn.ccrise.spimp.electr.service.WorkArrangeService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 工作安排
 */
@Controller
public class WorkArrangeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WorkArrangeService workArrangeService;

	@RequestMapping(value = "/electr/regulation/work-arranges/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(workArrangeService.delete(id));
	}

	@RequestMapping(value = "/electr/regulation/work-arranges/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Date startDate, Date endDate) throws Exception {
		Page<WorkArrange> page = new Page<WorkArrange>();
		page.setPageSize(100000);
		page = workArrangeService.pageQuery(page, search, startDate, endDate);

		String[] headers = { "标题", "工作安排", "上传日期", "上传人", "上传组织" };

		HSSFWorkbook wb = new ExcelHelper<WorkArrange>().genExcel("工作安排管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("工作安排管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/regulation/work-arranges/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(workArrangeService.get(id));
	}

	@RequestMapping(value = "/electr/regulation/work-arranges", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<WorkArrange> page, String search, Date startDate, Date endDate) {
		page = workArrangeService.pageQuery(page, search, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/regulation/work-arranges", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody WorkArrange workArrange, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		workArrange.setUploader(loginAccount);
		workArrange.setUploadGroup(loginAccount.getGroupEntity());
		workArrange.setUploadDate(new Date(System.currentTimeMillis()));
		return new Response(workArrangeService.save(workArrange));
	}

	@RequestMapping(value = "/electr/regulation/work-arranges/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody WorkArrange workArrange, @PathVariable long id, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		workArrange.setUploader(loginAccount);
		workArrange.setUploadGroup(loginAccount.getGroupEntity());
		return new Response(workArrangeService.update(workArrange));
	}
}