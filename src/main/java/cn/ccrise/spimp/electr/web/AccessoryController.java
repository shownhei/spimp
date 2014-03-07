/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Date;

import javax.servlet.http.HttpSession;
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
import cn.ccrise.spimp.electr.service.EquipmentService;
import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * Accessory Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AccessoryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private UploadedFileService uploadedFileService;

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id, HttpSession httpSession) {
		return new Response(accessoryService.deleteAccessory(id, httpSession));
	}

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(accessoryService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/accessories/setinstructions", method = RequestMethod.GET)
	@ResponseBody
	public Response modify(Long accessoryId, Long uploadFileId) {
		Accessory accessory = accessoryService.findUniqueBy("id", accessoryId);
		accessory.setInstructions(uploadedFileService.findUniqueBy("id", uploadFileId));
		return new Response(accessoryService.update(accessory));
	}

	@RequestMapping(value = "/electr/equipment/accessories", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Accessory> page) {
		return new Response(accessoryService.getPage(page));
	}

	@RequestMapping(value = "/electr/equipment/accessories", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Accessory accessory) {
		accessory.setRecordDate(new Date(System.currentTimeMillis()));
		accessoryService.save(accessory);
		return new Response(accessoryService.save(accessory));
	}

	@RequestMapping(value = "/electr/equipment/accessories/setpictureurl", method = RequestMethod.GET)
	@ResponseBody
	public Response setPicture(Long id, String pictureUrl) {
		Accessory temp = accessoryService.get(id);
		temp.setPictureURL(pictureUrl);
		return new Response(accessoryService.save(temp));
	}

	@RequestMapping(value = "/electr/equipment/accessories/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Accessory accessory, @PathVariable long id) {
		return new Response(accessoryService.update(accessory));
	}
}