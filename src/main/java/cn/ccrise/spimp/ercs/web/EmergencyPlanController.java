/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.ercs.entity.EmergencyPlan;
import cn.ccrise.spimp.ercs.service.EmergencyPlanService;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;

/**
 * EmergencyPlan Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyPlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EmergencyPlanService emergencyPlanService;

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(HttpSession httpSession, @PathVariable long id) {
		return new Response(emergencyPlanService.deletePlan(httpSession, id));
	}

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyPlanService.get(id));
	}

	@RequestMapping(value = "/ercs/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyPlan> page, String planName, Long planType) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(planName)) {
			param.add(Restrictions.like("planName", planName, MatchMode.ANYWHERE));
		}
		if (planType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", planType));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("planType", result.iterator().next()));
			}
		}
		return new Response(emergencyPlanService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@RequestBody EmergencyPlan emergencyPlan) {
		emergencyPlan.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(emergencyPlanService.save(emergencyPlan));
	}

	@RequestMapping(value = "/ercs/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyPlan emergencyPlan, @PathVariable long id) {
		return new Response(emergencyPlanService.update(emergencyPlan));
	}
}