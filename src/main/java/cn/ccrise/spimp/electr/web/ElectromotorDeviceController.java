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
import cn.ccrise.spimp.electr.entity.ElectromotorDevice;
import cn.ccrise.spimp.electr.service.ElectromotorDeviceService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * ElectromotorDevice Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class ElectromotorDeviceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ElectromotorDeviceService electromotorDeviceService;

	@RequestMapping(value = "/electr/equipment/electromotor-devices/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(electromotorDeviceService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/electromotor-devices/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		Page<ElectromotorDevice> page = new Page<ElectromotorDevice>();
		page.setPageSize(100000);
		page = electromotorDeviceService.pageQuery(page);

		String[] headers = { "型号", "编号", "出厂日期", "生产厂家" };

		HSSFWorkbook wb = new ExcelHelper<ElectromotorDevice>().genExcel("运输设备管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("运输设备管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/equipment/electromotor-devices/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(electromotorDeviceService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/electromotor-device", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/electromotor-device/index";
	}

	@RequestMapping(value = "/electr/equipment/electromotor-devices", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ElectromotorDevice> page) {
		page = electromotorDeviceService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/electromotor-devices", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ElectromotorDevice electromotorDevice) {
		return new Response(electromotorDeviceService.save(electromotorDevice));
	}

	@RequestMapping(value = "/electr/equipment/electromotor-devices/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ElectromotorDevice electromotorDevice, @PathVariable long id) {
		return new Response(electromotorDeviceService.update(electromotorDevice));
	}
}