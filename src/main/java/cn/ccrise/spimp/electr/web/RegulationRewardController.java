/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;

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
import cn.ccrise.spimp.electr.entity.RegulationReward;
import cn.ccrise.spimp.electr.service.RegulationRewardService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * RegulationReward Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RegulationRewardController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RegulationRewardService regulationRewardService;

	@RequestMapping(value = "/electr/regulation/regulation-rewards/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(regulationRewardService.delete(id));
	}

	@RequestMapping(value = "/electr/regulation/regulation-rewards/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(regulationRewardService.get(id));
	}

	@RequestMapping(value = "/electr/regulation/regulation-rewards", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<RegulationReward> page, String search, Date startDate, Date endDate) {
		page = regulationRewardService.pageQuery(page, search, startDate, endDate);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/regulation/regulation-rewards", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RegulationReward regulationReward) {
		return new Response(regulationRewardService.save(regulationReward));
	}

	@RequestMapping(value = "/electr/regulation/regulation-rewards/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RegulationReward regulationReward, @PathVariable long id) {
		return new Response(regulationRewardService.update(regulationReward));
	}

	@RequestMapping(value = "/electr/regulation/regulation-rewards/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Date startDate, Date endDate) throws Exception {
		Page<RegulationReward> page = new Page<RegulationReward>();
		page.setPageSize(100000);
		page = regulationRewardService.pageQuery(page, search, startDate, endDate);

		String[] headers = { "受奖人", "奖惩日期", "奖惩原因", "奖惩类型", "奖惩金额" };

		HSSFWorkbook wb = new ExcelHelper<RegulationReward>().genExcel("奖惩管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("奖惩管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}