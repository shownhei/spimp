/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

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
import cn.ccrise.spimp.ercs.entity.Refuge;
import cn.ccrise.spimp.ercs.service.RefugeService;

/**
 * Refuge Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class RefugeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RefugeService refugeService;

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(refugeService.delete(id));
	}

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(refugeService.get(id));
	}

	@RequestMapping(value = "/ercs/refuges", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Refuge> page) {
		return new Response(refugeService.getPage(page));
	}

	@RequestMapping(value = "/ercs/refuges", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Refuge refuge) {
		return new Response(refugeService.save(refuge));
	}

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Refuge refuge, @PathVariable long id) {
		return new Response(refugeService.update(refuge));
	}
}