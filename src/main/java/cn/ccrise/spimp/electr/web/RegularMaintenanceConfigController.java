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
import cn.ccrise.spimp.electr.entity.RegularMaintenanceConfig;
import cn.ccrise.spimp.electr.service.RegularMaintenanceConfigService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * RegularMaintenanceConfig Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RegularMaintenanceConfigController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegularMaintenanceConfigService regularMaintenanceConfigService;

	@RequestMapping(value = "/electr/maintenance/regular-configs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(regularMaintenanceConfigService.delete(id));
	}

	@RequestMapping(value = "/electr/maintenance/regular-configs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(regularMaintenanceConfigService.get(id));
	}

	@RequestMapping(value = "/electr/maintenance/regular-configs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<RegularMaintenanceConfig> page) {
		page = regularMaintenanceConfigService.pageQuery(page);
		return new Response(page);
	}

	/**
	 * 加载定期维修配置列表
	 * 
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/regular-configs-list", method = RequestMethod.GET)
	@ResponseBody
	public Response pageList(Page<RegularMaintenanceConfig> page) {
		page = regularMaintenanceConfigService.pageQuery(page);
		return new Response(page.getResult());
	}

	@RequestMapping(value = "/electr/maintenance/regular-configs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RegularMaintenanceConfig regularMaintenanceConfig, HttpSession httpSession) {
		regularMaintenanceConfig.setRecordDate(new Date(System.currentTimeMillis()));
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		regularMaintenanceConfig.setPlanGroup(loginAccount.getGroupEntity());
		return new Response(regularMaintenanceConfigService.save(regularMaintenanceConfig));
	}

	@RequestMapping(value = "/electr/maintenance/regular-configs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RegularMaintenanceConfig regularMaintenanceConfig,
			@PathVariable long id, HttpSession httpSession) {
		regularMaintenanceConfig.setUpdateDate(new Date(System.currentTimeMillis()));
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		regularMaintenanceConfig.setPlanGroup(loginAccount.getGroupEntity());
		return new Response(regularMaintenanceConfigService.update(regularMaintenanceConfig));
	}

	@RequestMapping(value = "/electr/maintenance/regular-configs/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		Page<RegularMaintenanceConfig> page = new Page<RegularMaintenanceConfig>();
		page.setPageSize(100000);
		page = regularMaintenanceConfigService.pageQuery(page);

		String[] headers = { "保养类别", "公里数", "记录时间", "修改时间", "创建单位" };

		HSSFWorkbook wb = new ExcelHelper<RegularMaintenanceConfig>().genExcel("定期保养周期配置管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("定期保养周期配置管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}