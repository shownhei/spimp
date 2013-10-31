/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.web;

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
import cn.ccrise.spimp.spmi.instruction.entity.Instruction;
import cn.ccrise.spimp.spmi.instruction.service.InstructionService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Instruction Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class InstructionController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InstructionService instructionService;

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(instructionService.delete(id));
	}

	@RequestMapping(value = "/spmi/instruction/instructions/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Date startDate, Date endDate, String search) throws Exception {
		Page<Instruction> page = new Page<Instruction>();
		page.setPageSize(100000);
		page = instructionService.pageQuery(page, startDate, endDate, search);

		String[] headers = { "时间", "指示人", "承办人", "接收人", "指示内容" };

		HSSFWorkbook wb = new ExcelHelper<Instruction>().genExcel("title", headers, page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件名称", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(instructionService.get(id));
	}

	@RequestMapping(value = "/spmi/instruction/instruction", method = RequestMethod.GET)
	public String index() {
		return "spmi/instruction/instruction/index";
	}

	@RequestMapping(value = "/spmi/instruction/instructions", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Instruction> page, Date startDate, Date endDate, String search) {
		page = instructionService.pageQuery(page, startDate, endDate, search);

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/instruction/instructions", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Instruction instruction) {
		return new Response(instructionService.save(instruction));
	}

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Instruction instruction, @PathVariable long id) {
		return new Response(instructionService.update(instruction));
	}
}