/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.ercs.entity.Dictionary;
import cn.ccrise.spimp.ercs.service.AlarmService;
import cn.ccrise.spimp.ercs.service.DictionaryService;
import cn.ccrise.spimp.util.AlarmMessage;
import cn.ccrise.spimp.util.ErcsDeferredResult;

/**
 * Alarm Controller。
 */
@Controller
public class AlarmController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private AlarmService alarmService;

	/**
	 * 关闭一个对话框 主要是测试使用
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
	public Response page(Page<Alarm> page, Long accidentType, Long accidentLevel, HttpServletRequest httpServletRequest) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (accidentType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", accidentType));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("accidentType", result.iterator().next()));
			}
		}
		if (accidentLevel != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", accidentLevel));
			if (result != null && result.size() > 0) {
				param.add(Restrictions.eq("accidentLevel", result.iterator().next()));
			}
		}
		page.setOrder("desc");
		page.setOrderBy("alarmTime");
		return new Response(alarmService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

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

	@RequestMapping(value = "/ercs/alarms", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Alarm alarm) {
		return new Response(alarmService.save(alarm));
	}

	@RequestMapping(value = "/ercs/alarms/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Alarm alarm, @PathVariable long id) {
		alarm.setProcessingTime(new Timestamp(System.currentTimeMillis()));
		return new Response(alarmService.updateAlarm(alarm));
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

}