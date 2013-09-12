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
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.ercs.service.AlarmService;

/**
 * Alarm Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AlarmController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AlarmService alarmService;

	@RequestMapping(value = "/ercs/alarms/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(alarmService.delete(id));
	}

	@RequestMapping(value = "/ercs/alarms/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(alarmService.get(id));
	}

	@RequestMapping(value = "/ercs/alarms", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Alarm> page) {
		return new Response(alarmService.getPage(page));
	}

	@RequestMapping(value = "/ercs/alarms", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Alarm alarm) {
		return new Response(alarmService.save(alarm));
	}

	@RequestMapping(value = "/ercs/alarms/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Alarm alarm, @PathVariable long id) {
		return new Response(alarmService.update(alarm));
	}
}