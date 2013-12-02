/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;

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
import cn.ccrise.spimp.ercs.service.UploadedFileService;
import cn.ccrise.spimp.spmi.car.entity.Regulation;
import cn.ccrise.spimp.spmi.car.service.RegulationService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Regulation Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RegulationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegulationService regulationService;
	@Autowired
	private UploadedFileService uploadedFileService;

	@RequestMapping(value = "/car/regulations/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(HttpSession httpSession, @PathVariable Long id) {
		return new Response(regulationService.deleteRegulation(httpSession, id));
	}

	@RequestMapping(value = "/car/regulations/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search) throws Exception {
		Page<Regulation> page = new Page<Regulation>();
		page.setPageSize(100000);
		page = regulationService.pageQuery(page, search);

		String[] headers = { "文档名称", "附件", "上传时间", "上传者" };

		HSSFWorkbook wb = new ExcelHelper<Regulation>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/car/regulations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(regulationService.get(id));
	}

	@RequestMapping(value = "/car/regulation", method = RequestMethod.GET)
	public String index() {
		return "car/regulation/index";
	}

	@RequestMapping(value = "/car/regulations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Regulation> page, String search) {
		page = regulationService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/car/regulations", method = RequestMethod.POST)
	@ResponseBody
	public Response save(HttpSession httpSession, @Valid @RequestBody Regulation regulation) {
		regulation.setUploadTime(new Timestamp(System.currentTimeMillis()));
		Account loginAccount = null;
		loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		regulation.setUploader(loginAccount.getRealName());
		return new Response(regulationService.save(regulation));
	}

	@RequestMapping(value = "/car/regulations/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Regulation regulation, @PathVariable long id) {
		return new Response(regulationService.update(regulation));
	}
}