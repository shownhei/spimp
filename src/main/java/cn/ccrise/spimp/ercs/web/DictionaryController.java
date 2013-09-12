/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.entity.Dictionary;
import cn.ccrise.spimp.ercs.service.DictionaryService;

/**
 * Dictionary Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class DictionaryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DictionaryService dictionaryService;

	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(dictionaryService.delete(id));
	}

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(dictionaryService.get(id));
	}

	@RequestMapping(value = "/ercs/dictionaries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Dictionary> page, String typeCode, Boolean list, String itemName) {
		if (StringUtils.isBlank(itemName)) {
			page = dictionaryService.getPage(page, Restrictions.eq("typeCode", typeCode));
		} else {
			page = dictionaryService.getPage(page, Restrictions.eq("typeCode", typeCode),
					Restrictions.like("itemName", "%" + itemName + "%"));
		}

		if (list != null && list.booleanValue()) {
			return new Response(page.getResult());
		}
		return new Response(page);
	}

	@RequestMapping(value = "/ercs/dictionaries", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@RequestBody Dictionary dictionary) {
		return new Response(dictionaryService.save(dictionary));
	}

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Dictionary dictionary, @PathVariable long id) {
		return new Response(dictionaryService.update(dictionary));
	}
}