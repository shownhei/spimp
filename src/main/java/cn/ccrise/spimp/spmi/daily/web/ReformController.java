/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

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
import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.spimp.spmi.daily.service.ReformService;

/**
 * Reform Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ReformController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReformService reformService;

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(reformService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(reformService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/reforms", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Reform> page) {
		return new Response(reformService.getPage(page));
	}

	@RequestMapping(value = "/spmi/daily/reforms", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Reform reform) {
		return new Response(reformService.save(reform));
	}

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Reform reform, @PathVariable long id) {
		return new Response(reformService.update(reform));
	}
}