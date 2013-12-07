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
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.service.PlanService;

/**
 * Plan Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class PlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlanService planService;

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(planService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(planService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Plan> page) {
		return new Response(planService.getPage(page));
	}

	@RequestMapping(value = "/spmi/daily/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Plan plan) {
		return new Response(planService.save(plan));
	}

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Plan plan, @PathVariable long id) {
		return new Response(planService.update(plan));
	}
}