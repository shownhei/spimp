/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import cn.ccrise.spimp.entity.Dictionary;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.spimp.ercs.service.EmergencyPlanInstanceService;
import cn.ccrise.spimp.service.DictionaryService;

/**
 * EmergencyPlanInstance Controller。 应急预案的实例
 */
@Controller
public class EmergencyPlanInstanceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EmergencyPlanInstanceService emergencyPlanInstanceService;

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyPlanInstanceService.delete(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyPlanInstanceService.get(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyPlanInstance> page, Long accidentType) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (accidentType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", accidentType));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("emergencyCategory", result.iterator().next()));
			}
		}
		page = emergencyPlanInstanceService.getPage(page, param.toArray(new SimpleExpression[0]));
		return new Response(page);
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EmergencyPlanInstance emergencyPlanInstance) {
		return new Response(emergencyPlanInstanceService.save(emergencyPlanInstance));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyPlanInstance emergencyPlanInstance, @PathVariable long id) {
		return new Response(emergencyPlanInstanceService.update(emergencyPlanInstance));
	}
}