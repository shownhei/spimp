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
import cn.ccrise.spimp.ercs.entity.EmergencyResource;
import cn.ccrise.spimp.ercs.service.EmergencyResourceService;

/**
 * EmergencyResource Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyResourceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

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
	public Response page(Page<EmergencyResource> page) {
		return new Response(emergencyResourceService.getPage(page));
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