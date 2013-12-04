/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.web;

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
import cn.ccrise.spimp.system.entity.Alteration;
import cn.ccrise.spimp.system.service.AlterationService;

/**
 * Alteration Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AlterationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AlterationService alterationService;

	@RequestMapping(value = "/system/alterations/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(alterationService.delete(id));
	}

	@RequestMapping(value = "/system/alterations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(alterationService.get(id));
	}

	@RequestMapping(value = "/system/alterations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Alteration> page) {
		return new Response(alterationService.getPage(page));
	}

	@RequestMapping(value = "/system/alterations", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Alteration alteration) {
		return new Response(alterationService.save(alteration));
	}

	@RequestMapping(value = "/system/alterations/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Alteration alteration, @PathVariable long id) {
		return new Response(alterationService.update(alteration));
	}
}