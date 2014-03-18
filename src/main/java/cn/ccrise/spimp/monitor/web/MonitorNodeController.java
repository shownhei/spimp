/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
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

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorNode;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorSensorTypeService;

import com.google.common.collect.Lists;

/**
 * MonitorNode Controller.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class MonitorNodeController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MonitorNodeService monitorNodeService;
	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;

	@RequestMapping(value = "/monitor/monitor-nodes/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(monitorNodeService.delete(id));
	}

	@RequestMapping(value = "/monitor/monitor-nodes/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(monitorNodeService.get(id));
	}

	/**
	 * 
	 * @param sensorTypeId
	 * @param sysId
	 *            监测监控实时监测部分的全部测点列表
	 * @return
	 */
	@RequestMapping(value = "/monitor/monitor-node-places", method = RequestMethod.GET)
	@ResponseBody
	public Response list(Integer sensorTypeId, Long sysId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (sensorTypeId == null) {
			sensorTypeId = -99999;
		}
		criterions.add(Restrictions.eq("sensorTypeId", sensorTypeId));
		if (sysId != null) {
			criterions.add(Restrictions.eq("sysId", sysId));
		}
		return new Response(monitorNodeService.find(criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/monitor/monitor-nodes", method = RequestMethod.GET)
	@ResponseBody
	public Response list(String sensorTypeName, Long sysId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (sensorTypeName != null) {
			MonitorSensorType monitorSensorType = monitorSensorTypeService.findUnique(Restrictions.eq("sensorTypeName",
					sensorTypeName));

			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType.getSensorTypeId()));
		}

		if (sysId != null) {
			criterions.add(Restrictions.eq("sysId", sysId));
		}
		return new Response(monitorNodeService.find(criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/monitor/monitor-nodes", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody MonitorNode monitorNode) {
		return new Response(monitorNodeService.save(monitorNode));
	}

	@RequestMapping(value = "/monitor/monitor-nodes/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody MonitorNode monitorNode, @PathVariable long id) {
		return new Response(monitorNodeService.update(monitorNode));
	}
}