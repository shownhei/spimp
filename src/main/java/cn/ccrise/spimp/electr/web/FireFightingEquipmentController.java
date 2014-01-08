/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.ccrise.spimp.electr.entity.FireFightingEquipment;
import cn.ccrise.spimp.electr.service.FireFightingEquipmentService;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * FireFightingEquipment Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class FireFightingEquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FireFightingEquipmentService fireFightingEquipmentService;

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(fireFightingEquipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(fireFightingEquipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipment", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/firefighting/index";
	}

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<FireFightingEquipment> page,String search) {
		page = fireFightingEquipmentService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody FireFightingEquipment fireFightingEquipment) {
		fireFightingEquipment.setRecordDate(new Date(System.currentTimeMillis()));
		return new Response(fireFightingEquipmentService.save(fireFightingEquipment));
	}

	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody FireFightingEquipment fireFightingEquipment, @PathVariable long id) {
		return new Response(fireFightingEquipmentService.update(fireFightingEquipment));
	}
	
	@RequestMapping(value = "/electr/equipment/fire-fighting-equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response,HttpSession httpSession,String search) throws Exception {
		Page<FireFightingEquipment> page = new Page<FireFightingEquipment>();
		page.setPageSize(100000);
		page = fireFightingEquipmentService.pageQuery(page, search);
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("result", page.getResult());
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response,
				"electr/equipment/fire-fighting-equipments.xls", root, "井下消防器材统计表",
				new String[] { "井下消防器材统计表" }, new ExcelCallBackInteface() {
					@Override
					public void process(Workbook book,
							HashMap<String, Object> root) {
					}
				});
	}
}