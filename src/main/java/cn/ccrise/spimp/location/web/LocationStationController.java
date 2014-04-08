/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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
import cn.ccrise.spimp.location.entity.LocationStaff;
import cn.ccrise.spimp.location.entity.LocationStation;
import cn.ccrise.spimp.location.service.LocationStaffService;
import cn.ccrise.spimp.location.service.LocationStationService;

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

	/**
	 * 分站内人员信息详情页面-转向
	 * 
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/location/location-stations-staff/detail", method = RequestMethod.GET)
	public ModelAndView alarmDetail(String param) {
		ModelAndView modelAndView = new ModelAndView("location/realtime/staffDetail");
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		modelAndView.addObject("datas", params);
		return modelAndView;
	}

	@RequestMapping(value = "/location/location-stations/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(locationStationService.delete(id));
	}

	@RequestMapping(value = "/location/location-stations/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(locationStationService.get(id));
	}

	@RequestMapping(value = "/location/location-station", method = RequestMethod.GET)
	public String index() {
		return "location/location-station/index";
	}

	@RequestMapping(value = "/location/location-stations", method = RequestMethod.GET)
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
			station.setCurPersonNum(locationStaffService.count(Restrictions.eq("curStationId", station.getId()
					.getStationId())));
			station.setTypeString(stationMaps.get(station.getType()));
		}
		page.setResult(stations);
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-stations", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody LocationStation locationStation) {
		return new Response(locationStationService.save(locationStation));
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
	@RequestMapping(value = "/location/location-stations-detail", method = RequestMethod.GET)
	@ResponseBody
	public Response stationStaffDatasDetail(Page<LocationStaff> page, String stationId) throws ParseException {

		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", stationId));

		// for (LocationStaff locationStaff : page.getResult()) {
		// String[] dates = locationStaff.getBirthday().split(" ");

		// locationStaff.setBirthday(Integer.parseInt(dates[3]) + "-"
		// + Integer.parseInt(dates[0]) + "-"
		// + Integer.parseInt(dates[2]));
		// }

		return new Response(page);
	}

	@RequestMapping(value = "/location/location-stations/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationStation locationStation, @PathVariable long id) {
		return new Response(locationStationService.update(locationStation));
	}
}