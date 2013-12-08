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
import cn.ccrise.spimp.spmi.daily.entity.Accident;
import cn.ccrise.spimp.spmi.daily.service.AccidentService;

/**
 * Accident Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AccidentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccidentService accidentService;

	@RequestMapping(value = "/spmi/daily/accidents/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(accidentService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/accidents/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(accidentService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/accidents", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Accident> page) {
		return new Response(accidentService.getPage(page));
	}

	@RequestMapping(value = "/spmi/daily/accidents", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Accident accident) {
		return new Response(accidentService.save(accident));
	}

	@RequestMapping(value = "/spmi/daily/accidents/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Accident accident, @PathVariable long id) {
		return new Response(accidentService.update(accident));
	}
}