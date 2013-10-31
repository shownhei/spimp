/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;

import javax.validation.Valid;

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
import cn.ccrise.spimp.entity.Dictionary;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanTemplate;
import cn.ccrise.spimp.ercs.service.EmergencyPlanTemplateService;
import cn.ccrise.spimp.service.DictionaryService;

/**
 * EmergencyPlanTemplate Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyPlanTemplateController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EmergencyPlanTemplateService emergencyPlanTemplateService;

	@RequestMapping(value = "/ercs/emergency-plan-templates/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyPlanTemplateService.delete(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-templates/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyPlanTemplateService.get(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-template", method = RequestMethod.GET)
	public String index() {
		return "ercs/emergency-plan-template/index";
	}

	@RequestMapping(value = "/ercs/emergency-plan-templates", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyPlanTemplate> page, Long emergencyCategory) {
		if (emergencyCategory != null) {
			Dictionary instance = dictionaryService.findUniqueBy("id", emergencyCategory);
			return new Response(emergencyPlanTemplateService.getPage(page,
					Restrictions.eq("emergencyCategory", instance)));
		}
		return new Response(emergencyPlanTemplateService.getPage(page));
	}

	@RequestMapping(value = "/ercs/emergency-plan-templates", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EmergencyPlanTemplate emergencyPlanTemplate) {
		emergencyPlanTemplate.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(emergencyPlanTemplateService.save(emergencyPlanTemplate));
	}

	@RequestMapping(value = "/ercs/emergency-plan-templates/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyPlanTemplate emergencyPlanTemplate, @PathVariable long id) {
		return new Response(emergencyPlanTemplateService.update(emergencyPlanTemplate));
	}
}