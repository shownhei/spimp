/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

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
import cn.ccrise.spimp.spmi.car.entity.MaterialsPlanDetail;
import cn.ccrise.spimp.spmi.car.service.MaterialsPlanDetailService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * MaterialsPlanDetail Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaterialsPlanDetailController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialsPlanDetailService materialsPlanDetailService;

	@RequestMapping(value = "/car/material/plan-details/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(materialsPlanDetailService.delete(id));
	}

	@RequestMapping(value = "/car/material/plan-details/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(materialsPlanDetailService.get(id));
	}

	@RequestMapping(value = "/car/material/plan-detail", method = RequestMethod.GET)
	public String index() {
		return "car/material/plandetail/index";
	}

	@RequestMapping(value = "/car/material/plan-details", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaterialsPlanDetail> page, Long plan, String search) {
		page = materialsPlanDetailService.pageQuery(page, plan, search);
		return new Response(page);
	}

	@RequestMapping(value = "/car/material/plan-details", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaterialsPlanDetail materialsPlanDetail) {
		return new Response(materialsPlanDetailService.save(materialsPlanDetail));
	}

	@RequestMapping(value = "/car/material/plan-details/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaterialsPlanDetail materialsPlanDetail, @PathVariable long id) {
		return new Response(materialsPlanDetailService.update(materialsPlanDetail));
	}

	@RequestMapping(value = "/car/material/plan-details/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long plan, String search) throws Exception {
		Page<MaterialsPlanDetail> page = new Page<MaterialsPlanDetail>();
		page.setPageSize(100000);
		page = materialsPlanDetailService.pageQuery(page, plan, search);

		String[] headers = { "单位", "材料名称", "规格型号、设备号", "度量单位", "单价（元）", "单价（元）" };

		HSSFWorkbook wb = new ExcelHelper<MaterialsPlanDetail>().genExcel("防治水信息管理 - 安全生产综合管理平台", headers,
				page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("防治水信息管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}