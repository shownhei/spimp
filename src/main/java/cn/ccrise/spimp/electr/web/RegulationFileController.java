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
import cn.ccrise.spimp.electr.entity.RegulationFile;
import cn.ccrise.spimp.electr.service.RegulationFileService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * RegulationFile Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RegulationFileController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegulationFileService regulationFileService;

	@RequestMapping(value = "/electr/regulation/regulation-files/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(regulationFileService.delete(id));
	}

	@RequestMapping(value = "/electr/regulation/regulation-files/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(regulationFileService.get(id));
	}

	@RequestMapping(value = "/electr/regulation/regulation-files", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<RegulationFile> page, String search, Long fileType, Date startDate, Date endDate) {
		page = regulationFileService.pageQuery(page, search, fileType, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/regulation/regulation-files", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RegulationFile regulationFile, HttpSession session) {
		// regulationFile.
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		regulationFile.setUploader(loginAccount);
		regulationFile.setUploadGroup(loginAccount.getGroupEntity());
		return new Response(regulationFileService.save(regulationFile));
	}

	@RequestMapping(value = "/electr/regulation/regulation-files/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RegulationFile regulationFile, @PathVariable long id, HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		regulationFile.setUploader(loginAccount);
		regulationFile.setUploadGroup(loginAccount.getGroupEntity());
		return new Response(regulationFileService.update(regulationFile));
	}

	@RequestMapping(value = "/electr/regulation/regulation-files/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Long fileType, Date startDate, Date endDate)
			throws Exception {
		Page<RegulationFile> page = new Page<RegulationFile>();
		page.setPageSize(100000);
		page = regulationFileService.pageQuery(page, search, fileType, startDate, endDate);

		String[] headers = { "文档名称", "制度类型", "附件", "上传日期", "上传日期", "上传日期" };

		HSSFWorkbook wb = new ExcelHelper<RegulationFile>().genExcel("制度文件管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("制度文件管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}