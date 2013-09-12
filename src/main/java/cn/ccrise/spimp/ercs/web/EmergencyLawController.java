/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import cn.ccrise.spimp.ercs.entity.EmergencyLaw;
import cn.ccrise.spimp.ercs.service.EmergencyLawService;

/**
 * EmergencyLaw Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class EmergencyLawController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private EmergencyLawService emergencyLawService;

	@RequestMapping(value = "/ercs/emergency-laws/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyLawService.delete(id));
	}

	@RequestMapping(value = "/ercs/emergency-laws/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyLawService.get(id));
	}

	@RequestMapping(value = "/ercs/emergency-laws", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyLaw> page, String search) {
		if (StringUtils.isNotBlank(search)) {
			emergencyLawService.getPage(
					page,
					Restrictions.or(Restrictions.like("fileNo", "%" + search + "%"),
							Restrictions.like("fileName", "%" + search + "%")));
		}
		return new Response(emergencyLawService.getPage(page));
	}

	@RequestMapping(value = "/ercs/emergency-laws", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EmergencyLaw emergencyLaw) {
		emergencyLaw.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(emergencyLawService.save(emergencyLaw));
	}

	@RequestMapping(value = "/ercs/emergency-laws/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyLaw emergencyLaw, @PathVariable long id) {
		return new Response(emergencyLawService.update(emergencyLaw));
	}
}