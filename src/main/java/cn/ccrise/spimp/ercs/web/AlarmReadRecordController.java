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
import cn.ccrise.spimp.ercs.entity.AlarmReadRecord;
import cn.ccrise.spimp.ercs.service.AlarmReadRecordService;

/**
 * AlarmReadRecord Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AlarmReadRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AlarmReadRecordService alarmReadRecordService;

	@RequestMapping(value = "/ercs//alarm-read-records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(alarmReadRecordService.delete(id));
	}

	@RequestMapping(value = "/ercs//alarm-read-records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(alarmReadRecordService.get(id));
	}

	@RequestMapping(value = "/ercs//alarm-read-record", method = RequestMethod.GET)
	public String index() {
		return "ercs//alarm-read-record/index";
	}

	@RequestMapping(value = "/ercs//alarm-read-records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<AlarmReadRecord> page) {
		return new Response(alarmReadRecordService.getPage(page));
	}

	@RequestMapping(value = "/ercs//alarm-read-records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody AlarmReadRecord alarmReadRecord) {
		return new Response(alarmReadRecordService.save(alarmReadRecord));
	}

	@RequestMapping(value = "/ercs//alarm-read-records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody AlarmReadRecord alarmReadRecord, @PathVariable long id) {
		return new Response(alarmReadRecordService.update(alarmReadRecord));
	}
}