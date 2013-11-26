/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.web;

import javax.validation.Valid;

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
import cn.ccrise.spimp.spmi.quality.entity.GradeRecord;
import cn.ccrise.spimp.spmi.quality.service.GradeRecordService;

/**
 * GradeRecord Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class GradeRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GradeRecordService gradeRecordService;

	@RequestMapping(value = "/spmi/quality/grade-records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(gradeRecordService.delete(id));
	}

	@RequestMapping(value = "/spmi/quality/grade-records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(gradeRecordService.get(id));
	}

	@RequestMapping(value = "/spmi/quality/grade-records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<GradeRecord> page) {
		return new Response(gradeRecordService.getPage(page));
	}

	@RequestMapping(value = "/spmi/quality/grade-records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody GradeRecord gradeRecord) {
		return new Response(gradeRecordService.save(gradeRecord));
	}

	@RequestMapping(value = "/spmi/quality/grade-records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody GradeRecord gradeRecord, @PathVariable long id) {
		return new Response(gradeRecordService.update(gradeRecord));
	}
}