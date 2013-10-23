/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
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
import cn.ccrise.spimp.ercs.entity.SpeciaList;
import cn.ccrise.spimp.ercs.service.DictionaryService;
import cn.ccrise.spimp.ercs.service.SpeciaListService;

/**
 * SpeciaList Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class SpeciaListController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SpeciaListService speciaListService;
	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/ercs/specia-lists/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(speciaListService.delete(id));
	}

	@RequestMapping(value = "/ercs/specia-lists/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(speciaListService.get(id));
	}

	@RequestMapping(value = "/ercs/specia-lists", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<SpeciaList> page, String name, Long specialty) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(name)) {
			param.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
		}
		if (specialty != null) {
			param.add(Restrictions.eq("specialty", dictionaryService.findUniqueBy("id", specialty)));
		}
		return new Response(speciaListService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/specia-lists", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody SpeciaList speciaList) {
		return new Response(speciaListService.save(speciaList));
	}

	@RequestMapping(value = "/ercs/specia-lists/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody SpeciaList speciaList, @PathVariable long id) {
		return new Response(speciaListService.update(speciaList));
	}
}