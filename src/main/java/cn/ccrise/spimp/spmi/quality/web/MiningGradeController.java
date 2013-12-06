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
import cn.ccrise.spimp.spmi.quality.entity.MiningGrade;
import cn.ccrise.spimp.spmi.quality.service.MiningGradeService;

import com.google.common.collect.Lists;

/**
 * MiningGrade Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class MiningGradeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MiningGradeService miningGradeService;

	@RequestMapping(value = "/spmi/quality/mining-grades/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(miningGradeService.delete(id));
	}

	@RequestMapping(value = "/spmi/quality/mining-grades/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(miningGradeService.get(id));
	}

	@RequestMapping(value = "/spmi/quality/mining-grades", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MiningGrade> page, Integer year, Integer month, String category) {
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
		return new Response(miningGradeService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/quality/mining-grades", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MiningGrade miningGrade) {
		return new Response(miningGradeService.save(miningGrade));
	}

	@RequestMapping(value = "/spmi/quality/mining-grades/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MiningGrade miningGrade, @PathVariable long id) {
		return new Response(miningGradeService.update(miningGrade));
	}
}