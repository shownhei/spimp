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
import cn.ccrise.spimp.electr.entity.MaterialsPlanDetail;
import cn.ccrise.spimp.electr.service.MaterialsPlanDetailService;

/**
 * MaterialsPlanDetail Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MaterialsPlanDetailController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MaterialsPlanDetailService materialsPlanDetailService;

	@RequestMapping(value = "/electr/material/plan-details/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(materialsPlanDetailService.delete(id));
	}

	@RequestMapping(value = "/electr/material/plan-details/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(materialsPlanDetailService.get(id));
	}

	@RequestMapping(value = "/electr/material/plan-detail", method = RequestMethod.GET)
	public String index() {
		return "electr/material/plandetail/index";
	}

	@RequestMapping(value = "/electr/material/plan-details", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MaterialsPlanDetail> page, Long plan, String search) {
		page = materialsPlanDetailService.pageQuery(page, plan, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/material/plan-details", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MaterialsPlanDetail materialsPlanDetail) {
		return new Response(materialsPlanDetailService.save(materialsPlanDetail));
	}

	@RequestMapping(value = "/electr/material/plan-details/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MaterialsPlanDetail materialsPlanDetail, @PathVariable long id) {
		return new Response(materialsPlanDetailService.update(materialsPlanDetail));
	}
}