/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.instruction.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.spmi.instruction.entity.Instruction;
import cn.ccrise.spimp.spmi.instruction.service.InstructionService;

import com.google.common.collect.Lists;

/**
 * Instruction Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class InstructionController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InstructionService instructionService;

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(instructionService.delete(id));
	}

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(instructionService.get(id));
	}

	@RequestMapping(value = "/spmi/instruction/instruction", method = RequestMethod.GET)
	public String index() {
		return "spmi/instruction/instruction/index";
	}

	@RequestMapping(value = "/spmi/instruction/instructions", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Instruction> page, String search) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("indicator", search, MatchMode.ANYWHERE),
					Restrictions.ilike("content", search, MatchMode.ANYWHERE)));
		}
		instructionService.getPage(page, criterions.toArray(new Criterion[0]));

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/instruction/instructions", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Instruction instruction) {
		return new Response(instructionService.save(instruction));
	}

	@RequestMapping(value = "/spmi/instruction/instructions/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Instruction instruction, @PathVariable long id) {
		return new Response(instructionService.update(instruction));
	}
}