/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Workbook;
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
import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.entity.WindWaterEquipment;
import cn.ccrise.spimp.electr.service.WindWaterEquipmentService;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * WindWaterEquipment Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class WindWaterEquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private WindWaterEquipmentService windWaterEquipmentService;

	@RequestMapping(value = "/electr/equipment/wind-water-equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(windWaterEquipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, HttpSession httpSession, String search) throws Exception {
		Page<WindWaterEquipment> page = new Page<WindWaterEquipment>();
		page.setPageSize(100000);
		page = windWaterEquipmentService.pageQuery(page, search);
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", page.getResult());
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response,
				"electr/equipment/wind-water-equipments.xls", root, "压风供水自救装置台账", new String[] { "压风供水自救装置台账" },
				new ExcelCallBackInteface() {
					@Override
					public void process(Workbook book, HashMap<String, Object> root) {
					}
				});
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(windWaterEquipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipment", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/windwater/index";
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<WindWaterEquipment> page, String search) {
		page = windWaterEquipmentService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody WindWaterEquipment instance) {
		instance.setRecordDate(new Date(System.currentTimeMillis()));
		return new Response(windWaterEquipmentService.save(instance));
	}

	@RequestMapping(value = "/electr/equipment/wind-water-equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody WindWaterEquipment windWaterEquipment, @PathVariable long id) {
		return new Response(windWaterEquipmentService.update(windWaterEquipment));
	}
}