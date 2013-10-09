/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
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
import cn.ccrise.spimp.ercs.entity.Scheme;
import cn.ccrise.spimp.ercs.service.SchemeService;

import com.google.common.collect.Lists;

/**
 * Scheme Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class SchemeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SchemeService schemeService;

	@RequestMapping(value = "/ercs/schemes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(schemeService.delete(id));
	}

	@RequestMapping(value = "/ercs/schemes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(schemeService.get(id));
	}

	@RequestMapping(value = "/ercs/schemes", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Scheme> page, String search) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (search != null) {
			criterions.add(Restrictions.or(Restrictions.ilike("type", search, MatchMode.ANYWHERE),
					Restrictions.ilike("decide", search, MatchMode.ANYWHERE)));
		}

		schemeService.getPage(page, criterions.toArray(new Criterion[0]));
		return new Response(page);
	}

	@RequestMapping(value = "/ercs/schemes", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Scheme scheme) {
		scheme.setUploadTime(new Timestamp(System.currentTimeMillis()));
		return new Response(schemeService.save(scheme));
	}

	@RequestMapping(value = "/ercs/schemes/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Scheme scheme, @PathVariable long id) {
		return new Response(schemeService.update(scheme));
	}
}