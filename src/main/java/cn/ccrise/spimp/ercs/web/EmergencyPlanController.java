/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;

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
import cn.ccrise.spimp.ercs.entity.EmergencyPlan;
import cn.ccrise.spimp.ercs.service.EmergencyPlanService;

/**
 * EmergencyPlan Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyPlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EmergencyPlanService emergencyPlanService;

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyPlanService.delete(id));
	}

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyPlanService.get(id));
	}

	@RequestMapping(value = "/ercs/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyPlan> page) {
		return new Response(emergencyPlanService.getPage(page));
	}

	@RequestMapping(value = "/ercs/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save( @RequestBody EmergencyPlan emergencyPlan) {
		emergencyPlan.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(emergencyPlanService.save(emergencyPlan));
	}

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyPlan emergencyPlan, @PathVariable long id) {
		logger.debug("{}",emergencyPlan);
		return new Response(emergencyPlanService.update(emergencyPlan));
	}
}