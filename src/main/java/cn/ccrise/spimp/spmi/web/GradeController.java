/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.web;

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
import cn.ccrise.spimp.spmi.entity.Grade;
import cn.ccrise.spimp.spmi.entity.GradeRecord;
import cn.ccrise.spimp.spmi.service.GradeRecordService;
import cn.ccrise.spimp.spmi.service.GradeService;

/**
 * Grade Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class GradeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GradeService gradeService;
	@Autowired
	private GradeRecordService gradeRecordService;

	@RequestMapping(value = "/spmi/quality/grades/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(gradeService.delete(id));
	}

	@RequestMapping(value = "/spmi/quality/grades/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(gradeService.get(id));
	}

	@RequestMapping(value = "/spmi/quality/grades", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Grade> page) {
		return new Response(gradeService.getPage(page));
	}

	@RequestMapping(value = "/spmi/quality/grades", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Grade grade) {
		for (GradeRecord gradeRecord : grade.getGradeRecords()) {
			gradeRecordService.save(gradeRecord);
		}

		return new Response(gradeService.save(grade));
	}

	@RequestMapping(value = "/spmi/quality/grades/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Grade grade, @PathVariable long id) {
		return new Response(gradeService.update(grade));
	}
}