/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.web;

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
import cn.ccrise.spimp.system.entity.DicitonaryType;
import cn.ccrise.spimp.system.service.DicitonaryTypeService;

/**
 * DicitonaryType Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class DicitonaryTypeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DicitonaryTypeService dicitonaryTypeService;

	@RequestMapping(value = "/system/dicitonary-types/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(dicitonaryTypeService.delete(id));
	}

	@RequestMapping(value = "/system/dicitonary-types/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(dicitonaryTypeService.get(id));
	}

	@RequestMapping(value = "/system/dicitonary-types", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<DicitonaryType> page) {
		page = dicitonaryTypeService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/system/dicitonary-types/list", method = RequestMethod.GET)
	@ResponseBody
	public Response list(Page<DicitonaryType> page) {
		page.setPageSize(200);
		page = dicitonaryTypeService.pageQuery(page);
		return new Response(page.getResult());
	}

	@RequestMapping(value = "/system/dicitonary-types", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody DicitonaryType dicitonaryType) {
		return new Response(dicitonaryTypeService.save(dicitonaryType));
	}

	@RequestMapping(value = "/system/dicitonary-types/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody DicitonaryType dicitonaryType, @PathVariable long id) {
		return new Response(dicitonaryTypeService.update(dicitonaryType));
	}
}