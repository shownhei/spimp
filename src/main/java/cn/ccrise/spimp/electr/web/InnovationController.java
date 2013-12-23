/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
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
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, String search) throws Exception {
		Page<Innovation> page = new Page<Innovation>();
		page.setPageSize(100000);
		page = innovationService.pageQuery(page, search);

		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("lists", page.getResult());
		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月份");
		Date current = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		root.put("year", calendar.get(Calendar.YEAR));
		root.put("yearMonth", formater.format(current));
		ExcelHelper<MaterialsPlan> helper = new ExcelHelper<MaterialsPlan>();
		helper.genExcelWithTel(httpSession, response, "electr/innovation_statistics.xls", root, "小改小革上报统计",
				new String[] { "小改小革上报统计表", "小改小革上报统计表(队内公示）" });
	}
}