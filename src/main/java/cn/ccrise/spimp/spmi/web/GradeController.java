/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.web;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.spmi.service.GradeRecordService;
import cn.ccrise.spimp.spmi.service.GradeService;

import com.google.common.collect.Lists;

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
	public Response page(Page<Grade> page, Integer year, Integer month) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (year != null) {
			criterions.add(Restrictions.eq("year", year));
		}
		if (month != null) {
			criterions.add(Restrictions.eq("month", month));
		}
		return new Response(gradeService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/quality/grades", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Grade grade) {
		return new Response(gradeService.save(grade));
	}

	@RequestMapping(value = "/spmi/quality/grades/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@RequestBody Grade grade, @PathVariable long id) {
		Grade gradeInDb = gradeService.get(id);

		if (grade.getCollectRecords().isEmpty()) { // 更新评分表
			gradeInDb.getGradeRecords().clear();
			gradeService.update(gradeInDb);

			grade.setGradedTime(gradeInDb.getGradedTime());
			grade.setCollectRecords(gradeInDb.getCollectRecords());
			grade.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			return new Response(gradeService.merge(grade));
		} else { // 更新总表
			if (!gradeInDb.getCollectRecords().isEmpty()) {
				gradeInDb.getCollectRecords().clear();
				gradeService.update(gradeInDb);
			}

			gradeInDb.getCollectRecords().addAll(grade.getCollectRecords());
			gradeInDb.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			return new Response(gradeService.save(gradeInDb));
		}
	}
}