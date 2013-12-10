/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Equipment;
import cn.ccrise.spimp.electr.service.EquipmentService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Equipment Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class EquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EquipmentService equipmentService;

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(equipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(equipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/info", method = RequestMethod.GET)
	public ModelAndView getPlan(Long equipmentId) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		if (equipmentId != null) {
			root.put("equipment", equipmentService.get(equipmentId));
		}
		return new ModelAndView("electr/equipment/detail/info", root);
	}

	@RequestMapping(value = "/electr/equipment/detail", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Equipment> page, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) {
		page = equipmentService.pageQuery(page, deviceClass, deviceCategory, deviceType, serviceEnvironment,
				deviceArea, stowedPosition);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Equipment equipment) {
		return new Response(equipmentService.save(equipment));
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Equipment equipment, @PathVariable long id) {
		return new Response(equipmentService.update(equipment));
	}

	@RequestMapping(value = "/electr/equipment/equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) throws Exception {
		Page<Equipment> page = new Page<Equipment>();
		page.setPageSize(100000);
		page = equipmentService.pageQuery(page, deviceClass, deviceCategory, deviceType, serviceEnvironment,
				deviceArea, stowedPosition);

		String[] headers = { "设备分类", "设备种类", "设备类型", "设备名称", "设备型号", "使用环境", "所属区域", "存放地点", "用途", "生产厂家", "设备编号",
				"出厂编号", "出厂日期", "包机人", "班长/组长", "", "", "速度", "运输量", "布置长度", "是否已拆除", "图片路径", "说明书路径" };

		HSSFWorkbook wb = new ExcelHelper<Equipment>().genExcel("定期检修设置管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("定期检修设置管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}