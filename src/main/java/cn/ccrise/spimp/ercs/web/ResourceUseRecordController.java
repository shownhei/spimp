/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

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
import cn.ccrise.spimp.ercs.entity.ResourceUseRecord;
import cn.ccrise.spimp.ercs.service.ResourceUseRecordService;

/**
 * ResourceUseRecord Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ResourceUseRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceUseRecordService resourceUseRecordService;

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(resourceUseRecordService.delete(id));
	}

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(resourceUseRecordService.get(id));
	}

	@RequestMapping(value = "/ercs/resource-use-records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ResourceUseRecord> page) {
		return new Response(resourceUseRecordService.getPage(page));
	}

	@RequestMapping(value = "/ercs/resource-use-records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ResourceUseRecord resourceUseRecord) {
		return new Response(resourceUseRecordService.save(resourceUseRecord));
	}

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ResourceUseRecord resourceUseRecord, @PathVariable long id) {
		return new Response(resourceUseRecordService.update(resourceUseRecord));
	}
}