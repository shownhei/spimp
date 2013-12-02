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
import cn.ccrise.spimp.electr.entity.MaintenanceDetail;
import cn.ccrise.spimp.electr.service.MaintenanceDetailService;

/**
 * MaintenanceDetail Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaintenanceDetailController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaintenanceDetailService maintenanceDetailService;

	@RequestMapping(value = "/electr/maintenance-details/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceDetailService.delete(id));
	}

	@RequestMapping(value = "/electr/maintenance-details/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceDetailService.get(id));
	}

	@RequestMapping(value = "/electr/maintenance-detail", method = RequestMethod.GET)
	public String index() {
		return "electr/maintenance-detail/index";
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-details", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaintenanceDetail> page) {
		page = maintenanceDetailService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-details", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaintenanceDetail maintenanceDetail) {
		return new Response(maintenanceDetailService.save(maintenanceDetail));
	}

	@RequestMapping(value = "/electr/maintenance/maintenance-details/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaintenanceDetail maintenanceDetail, @PathVariable long id) {
		return new Response(maintenanceDetailService.update(maintenanceDetail));
	}
}