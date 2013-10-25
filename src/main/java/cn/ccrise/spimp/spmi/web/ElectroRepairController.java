/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.spmi.entity.ElectroRepair;
import cn.ccrise.spimp.spmi.service.ElectroRepairService;

import com.google.common.collect.Lists;

/**
 * ElectroRepair Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ElectroRepairController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ElectroRepairService electroRepairService;

	@RequestMapping(value = "/spmi/electro/electro-repairs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(electroRepairService.delete(id));
	}

	@RequestMapping(value = "/spmi/electro/electro-repairs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(electroRepairService.get(id));
	}

	@RequestMapping(value = "/spmi/electro/electro-repair", method = RequestMethod.GET)
	public String index() {
		return "spmi/electro/electro-repair/index";
	}

	@RequestMapping(value = "/spmi/electro/electro-repairs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ElectroRepair> page, String search) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (search != null) {
			criterions.add(Restrictions.or(Restrictions.ilike("name", search, MatchMode.ANYWHERE),
					Restrictions.ilike("deviceName", search, MatchMode.ANYWHERE)));
		}

		electroRepairService.getPage(page, criterions.toArray(new Criterion[0]));
		return new Response(page);
	}

	@RequestMapping(value = "/spmi/electro/electro-repairs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ElectroRepair electroRepair) {
		return new Response(electroRepairService.save(electroRepair));
	}

	@RequestMapping(value = "/spmi/electro/electro-repairs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ElectroRepair electroRepair, @PathVariable long id) {
		return new Response(electroRepairService.update(electroRepair));
	}
}