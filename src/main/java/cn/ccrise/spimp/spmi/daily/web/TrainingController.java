/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import javax.validation.Valid;

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
import cn.ccrise.spimp.spmi.daily.entity.Training;
import cn.ccrise.spimp.spmi.daily.service.TrainingService;

import com.google.common.base.Strings;

/**
 * Training Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class TrainingController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TrainingService trainingService;

	@RequestMapping(value = "/spmi/daily/trainings/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(trainingService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/trainings/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(trainingService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/trainings", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Training> page, String search) {
		if (!Strings.isNullOrEmpty(search)) {
			return new Response(trainingService.getPage(page, Restrictions.ilike("name", search, MatchMode.ANYWHERE)));
		} else {
			return new Response(trainingService.getPage(page));
		}
	}

	@RequestMapping(value = "/spmi/daily/trainings", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Training training) {
		return new Response(trainingService.save(training));
	}

	@RequestMapping(value = "/spmi/daily/trainings/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Training training, @PathVariable long id) {
		training.setId(id);
		return new Response(trainingService.merge(training));
	}
}