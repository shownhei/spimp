/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
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
import cn.ccrise.spimp.ercs.entity.Dictionary;
import cn.ccrise.spimp.ercs.entity.Refuge;
import cn.ccrise.spimp.ercs.service.DictionaryService;
import cn.ccrise.spimp.ercs.service.RefugeService;

/**
 * Refuge Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class RefugeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private RefugeService refugeService;

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(refugeService.delete(id));
	}

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(refugeService.get(id));
	}

	@RequestMapping(value = "/ercs/refuges", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Refuge> page, String refugeName, Long refugeType) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(refugeName)) {
			param.add(Restrictions.like("refugeName", refugeName, MatchMode.ANYWHERE));
		}
		if (refugeType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", refugeType));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("refugeType", result.iterator().next()));
			}
		}
		return new Response(refugeService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/refuges", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Refuge refuge) {
		refuge.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(refugeService.save(refuge));
	}

	@RequestMapping(value = "/ercs/refuges/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Refuge refuge, @PathVariable long id) {
		return new Response(refugeService.update(refuge));
	}
}