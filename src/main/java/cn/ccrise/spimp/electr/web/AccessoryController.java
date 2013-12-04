/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

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
import cn.ccrise.spimp.electr.entity.Accessory;
import cn.ccrise.spimp.electr.service.AccessoryService;

/**
 * Accessory Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AccessoryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccessoryService accessoryService;

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(accessoryService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(accessoryService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/accessories", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Accessory> page) {
		return new Response(accessoryService.getPage(page));
	}

	@RequestMapping(value = "/electr/equipment/accessories", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Accessory accessory) {
		return new Response(accessoryService.save(accessory));
	}

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Accessory accessory, @PathVariable long id) {
		return new Response(accessoryService.update(accessory));
	}
}