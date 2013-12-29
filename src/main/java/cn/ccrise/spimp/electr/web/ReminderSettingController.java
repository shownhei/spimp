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
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.electr.entity.ReminderSetting;
import cn.ccrise.spimp.electr.service.ReminderSettingService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.DateUtil;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * ReminderSetting Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class ReminderSettingController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReminderSettingService reminderSettingService;

	/**
	 * 提醒
	 * 
	 * @param page
	 * @param project
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@RequestMapping(value = "/electr/equipment/alerts", method = RequestMethod.GET)
	@ResponseBody
	public Response alert(Page<ReminderSetting> page, Long project, Date startDate, Date endDate) {
		page.setOrder("asc");
		page.setOrderBy("dateEarly");
		page = reminderSettingService.getPage(page, Restrictions.le("dateEarly", new Date(System.currentTimeMillis())));
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(reminderSettingService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long project, Date startDate, Date endDate) throws Exception {
		Page<ReminderSetting> page = new Page<ReminderSetting>();
		page.setPageSize(100000);
		page = reminderSettingService.pageQuery(page, project, startDate, endDate);

		String[] headers = { "项目", "到期时间", "提前天数", "记录日期", "记录人", "记录组织" };

		HSSFWorkbook wb = new ExcelHelper<ReminderSetting>().genExcel("定期检修设置管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("定期检修设置管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(reminderSettingService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/reminder-setting", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/reminder-setting/index";
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ReminderSetting> page, Long project, Date startDate, Date endDate) {
		page = reminderSettingService.pageQuery(page, project, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ReminderSetting reminderSetting, HttpSession session) {
		reminderSetting.setRecordDate(new Date(System.currentTimeMillis()));
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		reminderSetting.setRecordAccount(loginAccount);
		reminderSetting.setRecordGroup(loginAccount.getGroupEntity());

		reminderSetting.setDateEarly(DateUtil.getFutureDay(reminderSetting.getExpirationDate(),
				-reminderSetting.getDaysEarly()));
		return new Response(reminderSettingService.save(reminderSetting));
	}

	@RequestMapping(value = "/electr/equipment/reminder-settings/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ReminderSetting reminderSetting, @PathVariable long id,
			HttpSession session) {
		Account loginAccount = (Account) session.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		reminderSetting.setRecordAccount(loginAccount);
		reminderSetting.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(reminderSettingService.update(reminderSetting));
	}
}