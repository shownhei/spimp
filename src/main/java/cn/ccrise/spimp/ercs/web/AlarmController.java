/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
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
import cn.ccrise.spimp.util.AlarmMessage;
import cn.ccrise.spimp.util.ErcsDeferredResult;

/**
 * Alarm Controller。
 */
@Controller
public class AlarmController {
	/**
	 * 报警接口 触发各个客户端打开输入界面、同时启动个客户端的等待关闭程序
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ercs/alarm/putalarm", method = RequestMethod.GET)
	@ResponseBody
	public AlarmMessage putAlarm(HttpServletRequest httpServletRequest) {
		logger.debug("请求进入...........");
		alarmService.putAlarm(httpServletRequest);
		AlarmMessage message = new AlarmMessage();
		return message;
	}

	/**
	 * 关闭一个对话框
	 * 
	 * @param alarmId
	 * @return
	 */
	@RequestMapping(value = "/ercs/alarm/closealarm", method = RequestMethod.GET)
	@ResponseBody
	public ErcsDeferredResult<AlarmMessage> asynClose(Long alarmId) {
		alarmService.notifyAlarmProcessed(alarmId);
		ErcsDeferredResult<AlarmMessage> deferredResult = new ErcsDeferredResult<AlarmMessage>();
		deferredResult.setResult(new AlarmMessage());
		return deferredResult;
	}

	/**
	 * 等待报警
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ercs/alarm/waitalarm", method = RequestMethod.GET)
	@ResponseBody
	public ErcsDeferredResult<AlarmMessage> asynGet(Long[] idArray, HttpServletRequest request) {
		ErcsDeferredResult<AlarmMessage> deferredResult = new ErcsDeferredResult<AlarmMessage>();
		AlarmMessage message = new AlarmMessage();
		message.setSessionId(request.getSession().getId());
		deferredResult.setRecordTime(new Timestamp(System.currentTimeMillis()));
		alarmService.waitAlarm(deferredResult, request, message, idArray);
		return deferredResult;
	}

	/**
	 * 等待报警
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ercs/alarm/waitclose", method = RequestMethod.GET)
	@ResponseBody
	public ErcsDeferredResult<AlarmMessage> waitCloseAlarm(HttpServletRequest request) {
		ErcsDeferredResult<AlarmMessage> deferredResult = new ErcsDeferredResult<AlarmMessage>();
		deferredResult.setRecordTime(new Timestamp(System.currentTimeMillis()));
		alarmService.waitCloseAlarm(request, deferredResult);
		return deferredResult;
	}

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
	public Response page(Page<Alarm> page, HttpServletRequest httpServletRequest) {
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

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AlarmService alarmService;
}