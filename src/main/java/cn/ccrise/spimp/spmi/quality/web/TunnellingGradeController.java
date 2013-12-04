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
import cn.ccrise.spimp.spmi.quality.entity.TunnellingGrade;
import cn.ccrise.spimp.spmi.quality.service.TunnellingGradeService;

import com.google.common.collect.Lists;

/**
 * TunnellingGrade Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class TunnellingGradeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TunnellingGradeService tunnellingGradeService;

	@RequestMapping(value = "/spmi/quality/tunnelling-grades/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(tunnellingGradeService.delete(id));
	}

	@RequestMapping(value = "/spmi/quality/tunnelling-grades/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(tunnellingGradeService.get(id));
	}

	@RequestMapping(value = "/spmi/quality/tunnelling-grades", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<TunnellingGrade> page, Integer year, Integer month, String category) {
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
		return new Response(tunnellingGradeService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/quality/tunnelling-grades", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody TunnellingGrade tunnellingGrade) {
		return new Response(tunnellingGradeService.save(tunnellingGrade));
	}

	@RequestMapping(value = "/spmi/quality/tunnelling-grades/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody TunnellingGrade tunnellingGrade, @PathVariable long id) {
		return new Response(tunnellingGradeService.update(tunnellingGrade));
	}
}