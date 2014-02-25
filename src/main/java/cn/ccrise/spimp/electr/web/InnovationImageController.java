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
import cn.ccrise.spimp.electr.entity.InnovationImage;
import cn.ccrise.spimp.electr.service.InnovationImageService;
/**
 * InnovationImage Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class InnovationImageController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InnovationImageService innovationImageService;

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(innovationImageService.delete(id));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(innovationImageService.get(id));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<InnovationImage> page) {
		page = innovationImageService.pageQuery(page);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/innovation/innovation-images", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody InnovationImage innovationImage) {
		return new Response(innovationImageService.save(innovationImage));
	}

	@RequestMapping(value = "/electr/innovation/innovation-images/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody InnovationImage innovationImage, @PathVariable long id) {
		return new Response(innovationImageService.update(innovationImage));
	}
}