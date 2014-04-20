/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
	/**
	 * 返回所有测点的实时数据 for activex
	 * @return
	 */
	@RequestMapping(value = "/monitor/monitor-nodes-value", method = RequestMethod.GET)
	@ResponseBody
	public Response sensorPointValue() {
		LinkedList<HashMap<String,Object>> result = new LinkedList<HashMap<String,Object>>();
		HashMap<String,Object> raw = null;
		StringBuilder buff = new StringBuilder();
		for(MonitorNode node :monitorNodeService.find()){
			buff.delete(0, buff.capacity());
			raw = new HashMap<String,Object> ();
			buff.append("MineID:");
			buff.append(node.getId().getMineId());
			buff.append(";StationID:");
			buff.append(node.getId().getNodeId());
			raw.put("DBID", buff.toString());
			raw.put("DATA", node.getCurrentData());
			result.add(raw);
		}
		return new Response(result);
	}
	/**
	 * 监测监控实时监测部分的全部测点列表
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

	@RequestMapping(value = "/monitor/monitor-nodes/max", method = RequestMethod.GET)
	@ResponseBody
	public Response max(Integer sensorTypeId) {
		if (sensorTypeId == null) {
			return new Response(false);
		}

		Query query = monitorNodeService
				.getDAO()
				.createQuery(
						"select currentData,nodePlace from MonitorNode"
								+ " where currentData=(select max(currentData) from MonitorNode where sensorTypeId=:sensorTypeId)"
								+ " and sensorTypeId=:sensorTypeId");
		query.setInteger("sensorTypeId", sensorTypeId);

		return new Response(query.list());
	}
}