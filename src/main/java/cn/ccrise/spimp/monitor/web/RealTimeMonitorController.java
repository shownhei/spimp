package cn.ccrise.spimp.monitor.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
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
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorSensorTypeService;
import cn.ccrise.spimp.monitor.service.MonitorStateService;
import cn.ccrise.spimp.system.service.GroupService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.collect.Lists;

/**
 * RealTimeMonitorController.
 * <p>
 * 实时监测模块控制类
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class RealTimeMonitorController {
	@Autowired
	private MonitorNodeService monitorNodeService;
	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;
	@Autowired
	private MonitorStateService monitorStateService;
	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/monitor/real-time-datas", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MonitorNode> page, Integer monitorSensorType, Integer monitorState, Integer type,
			String query, String startTime, String endTime, String mineId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}
		if (monitorState != null) {
			criterions.add(Restrictions.eq("stateId", monitorState));
		}

		if (type != null && type != 0) {
			Integer[] protocolNumber = monitorSensorTypeService.protocolAnalysis(type);
			criterions.add(Restrictions.ge("sensorTypeId", protocolNumber[0]));
			criterions.add(Restrictions.le("sensorTypeId", protocolNumber[1]));
		}

		if (StringUtils.isNotBlank(query)) {
			criterions.add(Restrictions.ilike("stateId", query, MatchMode.ANYWHERE));
		}

		if (StringUtils.isNotBlank(startTime)) {
			criterions.add(Restrictions.ge("startTime", Timestamp.valueOf(startTime)));
		}

		if (StringUtils.isNotBlank(endTime)) {
			criterions.add(Restrictions.le("endTime", Timestamp.valueOf(endTime)));
		}
		if (mineId != null) {
			criterions.add(Restrictions.in("mineId", groupService.getChildrenMines(mineId)));
		}

		monitorNodeService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		for (MonitorNode monitorNode : page.getResult()) {
			ResponseDataFilter.filter(monitorNode, monitorStateCache, monitorSensorTypeCache);
			if (monitorNode.getId().getMineId() != null) {
				monitorNode.setMineName(groupService.findUniqueBy("number", monitorNode.getId().getMineId()).getName());
			}
		}

		return new Response(page);
	}

	@RequestMapping(value = "/monitor/statistic-by-state", method = RequestMethod.GET)
	@ResponseBody
	public Response statisticByState(Integer type, String mineId, Integer monitorSensorType, Integer monitorState) {
		return new Response(monitorNodeService.statisticByState(type, mineId, monitorSensorType, monitorState));
	}

	@RequestMapping(value = "/monitor/statistic-ch4-co", method = RequestMethod.GET)
	@ResponseBody
	public Response statisticCH4AndCO(Integer type) {
		return new Response(monitorNodeService.ch4AndCoStatistic());
	}
}
