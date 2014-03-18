package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorNode;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;
import cn.ccrise.spimp.monitor.entity.MonitorState;
import cn.ccrise.spimp.monitor.entity.MonitorStation;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorSensorTypeService;
import cn.ccrise.spimp.monitor.service.MonitorStateService;
import cn.ccrise.spimp.monitor.service.MonitorStationService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.collect.Lists;

@Controller
public class InformationController {
	@Autowired
	private MonitorStationService monitorStationService;
	@Autowired
	private MonitorNodeService monitorNodeService;
	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;
	@Autowired
	private MonitorStateService monitorStateService;

	@RequestMapping(value = "/monitor/information-nodes", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MonitorNode> page, Integer monitorSensorType) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}

		monitorNodeService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		for (MonitorNode monitorNode : page.getResult()) {
			ResponseDataFilter.filter(monitorNode, monitorStateCache, monitorSensorTypeCache);
		}

		return new Response(page);
	}

	@RequestMapping(value = "/monitor/information-stations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MonitorStation> page) {
		monitorStationService.getPage(page);
		return new Response(page);
	}
}
