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
import cn.ccrise.spimp.electr.entity.Equipment;
import cn.ccrise.spimp.electr.service.EquipmentService;

/**
 * Equipment Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EquipmentService equipmentService;

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(equipmentService.delete(id));
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(equipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/detail", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Equipment> page) {
		return new Response(equipmentService.getPage(page));
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Equipment equipment) {
		return new Response(equipmentService.save(equipment));
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Equipment equipment, @PathVariable long id) {
		return new Response(equipmentService.update(equipment));
	}
}