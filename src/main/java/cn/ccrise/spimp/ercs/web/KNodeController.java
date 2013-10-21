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
import cn.ccrise.spimp.ercs.entity.KNode;
import cn.ccrise.spimp.ercs.service.KNodeService;

/**
 * KNode Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class KNodeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KNodeService kNodeService;

	@RequestMapping(value = "/ercs/k-nodes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(kNodeService.delete(id));
	}

	@RequestMapping(value = "/ercs/k-nodes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(kNodeService.get(id));
	}

	@RequestMapping(value = "/ercs/k-node", method = RequestMethod.GET)
	public String index() {
		return "ercs/k-node/index";
	}

	@RequestMapping(value = "/ercs/k-nodes", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<KNode> page) {
		return new Response(kNodeService.getPage(page));
	}

	@RequestMapping(value = "/ercs/k-nodes", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody KNode kNode) {
		return new Response(kNodeService.save(kNode));
	}

	@RequestMapping(value = "/ercs/k-nodes/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody KNode kNode, @PathVariable long id) {
		return new Response(kNodeService.update(kNode));
	}
}