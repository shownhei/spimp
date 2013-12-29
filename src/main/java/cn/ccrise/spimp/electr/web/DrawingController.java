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
import cn.ccrise.spimp.electr.entity.Drawing;
import cn.ccrise.spimp.electr.service.DrawingService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Drawing Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class DrawingController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DrawingService drawingService;

	@RequestMapping(value = "/electr/regulation/drawings/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(drawingService.delete(id));
	}

	@RequestMapping(value = "/electr/regulation/drawings/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Date startDate, Date endDate,
			HttpSession session) throws Exception {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		Page<Drawing> page = new Page<Drawing>();
		page.setPageSize(100000);
		page = drawingService.pageQuery(page, search, startDate, endDate, loginAccount, loginAccount.getGroupEntity());

		String[] headers = { "标题", "图纸类型", "工作安排", "上传日期", "上传人", "上传组织" };

		HSSFWorkbook wb = new ExcelHelper<Drawing>().genExcel("工作安排管理 - 安全生产综合管理平台", headers, page.getResult(),
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

	@RequestMapping(value = "/electr/regulation/drawings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(drawingService.get(id));
	}

	@RequestMapping(value = "/electr/regulation/drawings", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Drawing> page, String search, Date startDate, Date endDate, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		page = drawingService.pageQuery(page, search, startDate, endDate, loginAccount, loginAccount.getGroupEntity());
		return new Response(page);
	}

	@RequestMapping(value = "/electr/regulation/drawings", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Drawing drawing, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		drawing.setUploader(loginAccount);
		drawing.setUploadGroup(loginAccount.getGroupEntity());
		return new Response(drawingService.save(drawing));
	}

	@RequestMapping(value = "/electr/regulation/drawings/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Drawing drawing, @PathVariable long id, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		drawing.setUploader(loginAccount);
		drawing.setUploadGroup(loginAccount.getGroupEntity());
		return new Response(drawingService.update(drawing));
	}
}