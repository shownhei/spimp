/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.service.AlarmConfigService;

/**
 * AlarmConfig Controller.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AlarmConfigController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AlarmConfigService alarmConfigService;

	@RequestMapping(value = "/system/alarm-configs", method = RequestMethod.GET)
	@ResponseBody
	public Response list(@RequestParam Long accountId) {
		return new Response(alarmConfigService.getByAccountId(accountId));
	}

	@RequestMapping(value = "/system/alarm-configs", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@RequestBody Map<String, String> data, @RequestParam Long accountId) {
		return new Response(alarmConfigService.updateByAccountId(accountId, data));
	}
}