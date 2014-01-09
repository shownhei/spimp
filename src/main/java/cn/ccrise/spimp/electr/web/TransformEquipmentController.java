/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;

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
import cn.ccrise.spimp.electr.entity.TransformEquipment;
import cn.ccrise.spimp.electr.service.TransformEquipmentService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 运输设备台账。
 * 
 */
@Controller
public class TransformEquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TransformEquipmentService transformEquipmentService;

	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(transformEquipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(transformEquipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipment", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/transformequipment/index";
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<TransformEquipment> page, Long deviceClass,
			String search) {
		page = transformEquipmentService.pageQuery(page, deviceClass, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(HttpSession httpSession,
			@Valid @RequestBody TransformEquipment transformEquipment) {

		Account loginAccount = (Account) httpSession
				.getAttribute(PropertiesUtils
						.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		transformEquipment.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(transformEquipmentService.save(transformEquipment));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(HttpSession httpSession,
			@Valid @RequestBody TransformEquipment transformEquipment,
			@PathVariable long id) {

		Account loginAccount = (Account) httpSession
				.getAttribute(PropertiesUtils
						.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		transformEquipment.setRecordGroup(loginAccount.getGroupEntity());
		return new Response(
				transformEquipmentService.update(transformEquipment));
	}

	@RequestMapping(value = "/electr/equipment/transform-equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long deviceClass,
			String search) throws Exception {
		Page<TransformEquipment> page = new Page<TransformEquipment>();
		page.setPageSize(100000);
		page = transformEquipmentService.pageQuery(page, deviceClass, search);

		String[] headers = { "设备分类", "设备名称", "设备型号", "速度(m/s)", "输送量(T/h)",
				"出厂编号", "出厂日期", "设备编号", "使用地点", "生产厂家", "布置长度(m)", "单位" };

		HSSFWorkbook wb = new ExcelHelper<TransformEquipment>().genExcel(
				"运输设备管理 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ URLEncoder.encode("运输设备管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}