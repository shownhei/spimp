/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.location.entity.LocationArea;
import cn.ccrise.spimp.location.entity.LocationStaff;
import cn.ccrise.spimp.location.service.LocationAreaService;
import cn.ccrise.spimp.location.service.LocationStaffService;

/**
 * LocationArea Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationAreaController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LocationAreaService locationAreaService;
	@Autowired
	private LocationStaffService locationStaffService;

	/**
	 * 区域详细人数页面-转向
	 */
	@RequestMapping(value = "/location/location-areas-staff/detail", method = RequestMethod.GET)
	public ModelAndView alarmDetail(String param) {
		ModelAndView modelAndView = new ModelAndView("location/realtime/staffAreaDetail");
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		modelAndView.addObject("datas", params);
		return modelAndView;
	}

	@RequestMapping(value = "/location/location-areas", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationArea> page) {
		page = locationAreaService.getPage(page);
		Map<Integer, String> areaMaps = new HashMap<Integer, String>();
		areaMaps.put(0, "正常区域");
		areaMaps.put(1, "重点区域");
		areaMaps.put(2, "限制区域");
		for (LocationArea area : page.getResult()) {
			if (area.getType() != null) {
				area.setTypeString(areaMaps.get(area.getType()));
			}

			// 统计区域人数
			area.setCurPersonNum(locationStaffService.count(Restrictions.eq("id.mineId", area.getId().getMineId()),
					Restrictions.eq("curAreaId", area.getId().getAreaId())));
		}

		return new Response(page);
	}

	@RequestMapping(value = "/location/location-areas", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody LocationArea locationArea) {
		return new Response(locationAreaService.save(locationArea));
	}

	/**
	 * 返回区域内人员信息详情数据
	 */
	@RequestMapping(value = "/location/location-areas-detail", method = RequestMethod.GET)
	@ResponseBody
	public Response stationStaffDatasDetail(Page<LocationStaff> page, String areaId) {
		page = locationStaffService.getPage(page, Restrictions.eq("curAreaId", areaId));
		for (LocationStaff locationStaff : page.getResult()) {
			String[] dates = locationStaff.getBirthday().split(" ");

			locationStaff.setBirthday(Integer.parseInt(dates[3]) + "-" + Integer.parseInt(dates[0]) + "-"
					+ Integer.parseInt(dates[2]));
		}
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-areas/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationArea locationArea, @PathVariable long id) {
		return new Response(locationAreaService.update(locationArea));
	}
}