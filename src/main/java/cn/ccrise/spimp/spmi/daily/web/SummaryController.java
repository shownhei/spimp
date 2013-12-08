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
import cn.ccrise.spimp.spmi.daily.entity.Summary;
import cn.ccrise.spimp.spmi.daily.service.SummaryService;

/**
 * Summary Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class SummaryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SummaryService summaryService;

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(summaryService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(summaryService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/summaries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Summary> page) {
		return new Response(summaryService.getPage(page));
	}

	@RequestMapping(value = "/spmi/daily/summaries", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Summary summary) {
		return new Response(summaryService.save(summary));
	}

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Summary summary, @PathVariable long id) {
		return new Response(summaryService.update(summary));
	}
}