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
import cn.ccrise.spimp.electr.entity.InnovationImage;
import cn.ccrise.spimp.electr.service.InnovationImageService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * InnovationImage Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class InnovationImageController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InnovationImageService innovationImageService;

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(innovationImageService.delete(id));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(innovationImageService.get(id));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<InnovationImage> page) {
		page = innovationImageService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/innovation/innovation-images", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody InnovationImage innovationImage) {
		return new Response(innovationImageService.save(innovationImage));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody InnovationImage innovationImage, @PathVariable long id) {
		return new Response(innovationImageService.update(innovationImage));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) throws Exception {
		Page<InnovationImage> page = new Page<InnovationImage>();
		page.setPageSize(100000);
		page = innovationImageService.pageQuery(page);

		String[] headers = {};

		HSSFWorkbook wb = new ExcelHelper<InnovationImage>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}