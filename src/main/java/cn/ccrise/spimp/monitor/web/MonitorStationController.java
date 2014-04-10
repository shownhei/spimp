/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorStation;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorStationService;
import cn.ccrise.spimp.system.service.GroupService;

import com.google.common.collect.Lists;

/**
 * MonitorStation Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class MonitorStationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MonitorStationService monitorStationService;
	@Autowired
	private MonitorNodeService monitorNodeService;
	@Autowired
	private GroupService groupService;

	@RequestMapping(value = "/monitor/monitor-stations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(monitorStationService.get(id));
	}

	@RequestMapping(value = "/monitor/monitor-stations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MonitorStation> page, String mineId, Boolean state) {
		ArrayList<Criterion> criterions = Lists.newArrayList();

		if (mineId != null) {
			criterions.add(Restrictions.in("mineId", groupService.getChildrenMines(mineId)));
		}
		if (state != null) {
			if (state) {
				criterions.add(Restrictions.eq("stationData", "1"));
			} else {
				criterions.add(Restrictions.ne("stationData", "1"));
			}
		}

		monitorStationService.getPage(page, criterions.toArray(new Criterion[0]));

		for (MonitorStation monitorStation : page.getResult()) {
			if (monitorStation.getId().getMineId() != null) {
				GroupEntity groupEntity = groupService.findUniqueBy("number", monitorStation.getId().getMineId());
				if (null != groupEntity) {
					monitorStation.setMineName(groupEntity.getName());
				}
			}
		}

		return new Response(page);
	}

	@RequestMapping(value = "/monitor/statistic-by-data", method = RequestMethod.GET)
	@ResponseBody
	public Response statisticByData(String mineId, Boolean state) {
		return new Response(monitorStationService.statisticByData(mineId, state));
	}
}