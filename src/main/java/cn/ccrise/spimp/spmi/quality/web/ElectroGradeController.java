/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.web;

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
import cn.ccrise.spimp.spmi.quality.entity.ElectroGrade;
import cn.ccrise.spimp.spmi.quality.service.ElectroGradeService;

import com.google.common.collect.Lists;

/**
 * ElectroGrade Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ElectroGradeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ElectroGradeService electroGradeService;

	@RequestMapping(value = "/spmi/quality/electro-grades/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(electroGradeService.delete(id));
	}

	@RequestMapping(value = "/spmi/quality/electro-grades/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(electroGradeService.get(id));
	}

	@RequestMapping(value = "/spmi/quality/electro-grades", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ElectroGrade> page, Integer year, Integer month, String category) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (year != null) {
			criterions.add(Restrictions.eq("year", year));
		}
		if (month != null) {
			criterions.add(Restrictions.eq("month", month));
		}
		if (category != null) {
			criterions.add(Restrictions.eq("category", category));
		}
		return new Response(electroGradeService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/quality/electro-grades", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ElectroGrade electroGrade) {
		return new Response(electroGradeService.save(electroGrade));
	}

	@RequestMapping(value = "/spmi/quality/electro-grades/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ElectroGrade electroGrade, @PathVariable long id) {
		return new Response(electroGradeService.update(electroGrade));
	}
}