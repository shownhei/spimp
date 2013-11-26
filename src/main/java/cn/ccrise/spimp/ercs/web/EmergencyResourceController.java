/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import cn.ccrise.spimp.ercs.entity.EmergencyResource;
import cn.ccrise.spimp.ercs.service.EmergencyResourceService;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;

/**
 * EmergencyResource Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyResourceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EmergencyResourceService emergencyResourceService;

	@RequestMapping(value = "/ercs/emergency-resources/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyResourceService.delete(id));
	}

	@RequestMapping(value = "/ercs/emergency-resources/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyResourceService.get(id));
	}

	@RequestMapping(value = "/ercs/emergency-resources", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyResource> page, String resourceName, Long refugeType, String q) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(resourceName)) {
			param.add(Restrictions.like("resourceName", resourceName, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(q)) {
			param.add(Restrictions.like("resourceName", q, MatchMode.ANYWHERE));
		}
		if (refugeType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", refugeType));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("resourceType", result.iterator().next()));
			}
		}
		return new Response(emergencyResourceService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/emergency-resources", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EmergencyResource emergencyResource) {
		emergencyResource.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(emergencyResourceService.save(emergencyResource));
	}

	@RequestMapping(value = "/ercs/emergency-resources/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyResource emergencyResource, @PathVariable long id) {
		return new Response(emergencyResourceService.update(emergencyResource));
	}
}