/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Innovation;
import cn.ccrise.spimp.electr.service.InnovationService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Innovation Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class InnovationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InnovationService innovationService;

	@RequestMapping(value = "/electr/innovation/innovations/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(innovationService.delete(id));
	}

	@RequestMapping(value = "/electr/innovation/innovations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(innovationService.get(id));
	}

	@RequestMapping(value = "/electr/innovation/innovations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Innovation> page, String search) {
		page = innovationService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/innovation/detail/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView detail(@PathVariable long id) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("innovation", innovationService.get(id));
		return new ModelAndView("electr/innovation/innovation/detail", root);
	}

	@RequestMapping(value = "/electr/innovation/innovations", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Innovation innovation, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		innovation.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(innovationService.save(innovation));
	}

	@RequestMapping(value = "/electr/innovation/innovations/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Innovation innovation, @PathVariable long id, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		innovation.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(innovationService.update(innovation));
	}

	@RequestMapping(value = "/electr/innovation/innovations/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search) throws Exception {
		Page<Innovation> page = new Page<Innovation>();
		page.setPageSize(100000);
		page = innovationService.pageQuery(page, search);

		String[] headers = { "项目名称", "负责人", "申报日期", "实施地点", "实施时间", "参与人", "目的", "主要内容或原理", "效果及经济社会效益分析", "记录组织" };

		HSSFWorkbook wb = new ExcelHelper<Innovation>().genExcel("小改小革管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("小改小革管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}