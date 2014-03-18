/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.web;

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

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;
import cn.ccrise.spimp.monitor.service.MonitorSensorTypeService;

/**
 * MonitorSensorType Controller.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class MonitorSensorTypeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;

	@RequestMapping(value = "/monitor/monitor-sensor-types/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(monitorSensorTypeService.delete(id));
	}

	@RequestMapping(value = "/monitor/monitor-sensor-types/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(monitorSensorTypeService.get(id));
	}

	@RequestMapping(value = "/monitor/monitor-sensor-types", method = RequestMethod.GET)
	@ResponseBody
	public Response list(Integer type) {
		return new Response(monitorSensorTypeService.getSensorTypesAsType(type));
	}

	@RequestMapping(value = "/monitor/monitor-sensor-types", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MonitorSensorType monitorSensorType) {
		return new Response(monitorSensorTypeService.save(monitorSensorType));
	}

	@RequestMapping(value = "/monitor/monitor-sensor-types/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MonitorSensorType monitorSensorType, @PathVariable long id) {
		return new Response(monitorSensorTypeService.update(monitorSensorType));
	}
}