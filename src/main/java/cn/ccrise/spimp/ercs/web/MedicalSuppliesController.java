/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.ercs.entity.MedicalSupplies;
import cn.ccrise.spimp.ercs.service.MedicalSuppliesService;

/**
 * MedicalSupplies Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class MedicalSuppliesController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MedicalSuppliesService medicalSuppliesService;

	@RequestMapping(value = "/ercs/medical-supply/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(medicalSuppliesService.delete(id));
	}

	@RequestMapping(value = "/ercs/medical-supply/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(medicalSuppliesService.get(id));
	}

	@RequestMapping(value = "/ercs/medical-supply", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MedicalSupplies> page, String name) {
		if (StringUtils.isBlank(name)) {
			return new Response(medicalSuppliesService.getPage(page));
		} else {
			return new Response(medicalSuppliesService.getPage(page,
					Restrictions.ilike("name", name, MatchMode.ANYWHERE)));
		}
	}

	@RequestMapping(value = "/ercs/medical-supply", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MedicalSupplies medicalSupplies) {
		return new Response(medicalSuppliesService.save(medicalSupplies));
	}

	@RequestMapping(value = "/ercs/medical-supply/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MedicalSupplies medicalSupplies, @PathVariable long id) {
		return new Response(medicalSuppliesService.update(medicalSupplies));
	}
}