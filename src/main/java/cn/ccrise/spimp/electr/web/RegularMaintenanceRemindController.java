/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;

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
import cn.ccrise.spimp.electr.entity.RegularMaintenanceRemind;
import cn.ccrise.spimp.electr.service.RegularMaintenanceRemindService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * RegularMaintenanceRemind Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RegularMaintenanceRemindController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegularMaintenanceRemindService regularMaintenanceRemindService;

	@RequestMapping(value = "/electr/maintenance/regular-reminds/close", method = RequestMethod.GET)
	@ResponseBody
	public Response closeRemind(Long id) {
		regularMaintenanceRemindService.closeRemind(id);
		return new Response(true);
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(regularMaintenanceRemindService.delete(id));
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long car) throws Exception {
		Page<RegularMaintenanceRemind> page = new Page<RegularMaintenanceRemind>();
		page.setPageSize(100000);
		page = regularMaintenanceRemindService.pageQuery(page, car);

		String[] headers = { "车号", "保养类别", "公里数" };

		HSSFWorkbook wb = new ExcelHelper<RegularMaintenanceRemind>().genExcel("定期保养到期提醒配置管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("定期保养到期提醒配置管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(regularMaintenanceRemindService.get(id));
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<RegularMaintenanceRemind> page, Long car) {
		page = regularMaintenanceRemindService.pageQuery(page, car);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RegularMaintenanceRemind regularMaintenanceRemind) {
		return new Response(regularMaintenanceRemindService.save(regularMaintenanceRemind));
	}

	@RequestMapping(value = "/electr/maintenance/regular-reminds/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RegularMaintenanceRemind regularMaintenanceRemind, @PathVariable long id) {
		return new Response(regularMaintenanceRemindService.update(regularMaintenanceRemind));
	}
}