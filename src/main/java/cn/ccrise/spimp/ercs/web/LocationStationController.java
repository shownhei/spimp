/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.entity.LocationStaff;
import cn.ccrise.spimp.ercs.entity.LocationStation;
import cn.ccrise.spimp.ercs.service.LocationStaffService;
import cn.ccrise.spimp.ercs.service.LocationStationService;

import com.google.common.collect.Lists;

/**
 * LocationStation Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationStationController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LocationStationService locationStationService;
	@Autowired
	private LocationStaffService locationStaffService;

	@RequestMapping(value = "/ercs/location-stations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationStation> page) {
		page = locationStationService.getPage(page);
		Map<Integer, String> stationMaps = new HashMap<Integer, String>();
		stationMaps.put(0, "井下定位");
		stationMaps.put(1, "井口考勤");
		stationMaps.put(2, "井底（考勤）");
		stationMaps.put(3, "井下禁区");
		List<LocationStation> stations = Lists.newArrayList();
		stations = page.getResult();
		for (LocationStation station : stations) {
			station.setCurPersonNum(locationStaffService.count(Restrictions.eq("curStationId", station.getStationId())));
			station.setTypeString(stationMaps.get(station.getType()));
		}
		page.setResult(stations);
		return new Response(page);
	}

	/**
	 * 分站内人员信息详情页面-转向
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/ercs/location-stations-staff/detail", method = RequestMethod.GET)
	public ModelAndView alarmDetail(String param) {
		ModelAndView modelAndView = new ModelAndView("ercs/realtime/staffDetail");
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		modelAndView.addObject("datas", params);
		return modelAndView;
	}

	/**
	 * 返回分站内人员信息详情数据
	 * 
	 * @param page
	 * @param nodeId
	 * @param sensorName
	 * @param nodePlace
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/ercs/location-stations-detail", method = RequestMethod.GET)
	@ResponseBody
	public Response stationStaffDatasDetail(Page<LocationStaff> page, String stationId) throws ParseException {
		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", stationId));
		return new Response(page);
	}
}