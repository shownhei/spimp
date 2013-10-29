/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.spmi.instruction.entity.Focus;
import cn.ccrise.spimp.spmi.instruction.service.FocusService;

import com.google.common.collect.Lists;

/**
 * Focus Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class FocusController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FocusService focusService;

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(focusService.delete(id));
	}

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(focusService.get(id));
	}

	@RequestMapping(value = "/spmi/instruction/focus", method = RequestMethod.GET)
	public String index() {
		return "spmi/instruction/focus/index";
	}

	@RequestMapping(value = "/spmi/instruction/focuses", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Focus> page, String search) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.ilike("name", search, MatchMode.ANYWHERE));
		}
		focusService.getPage(page, criterions.toArray(new Criterion[0]));

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/instruction/focuses", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Focus focus) {
		return new Response(focusService.save(focus));
	}

	@RequestMapping(value = "/spmi/instruction/focuses/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Focus focus, @PathVariable long id) {
		return new Response(focusService.update(focus));
	}
}