/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Criterion;
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
	 */
	@RequestMapping(value = "/location/location-stations-staff/detail", method = RequestMethod.GET)
	public ModelAndView alarmDetail(String param) {
		ModelAndView modelAndView = new ModelAndView("location/realtime/staffDetail");
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		modelAndView.addObject("datas", params);
		return modelAndView;
	}

	@RequestMapping(value = "/location/location-stations", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationStation> page, Integer state) {
		page = locationStationService.getPage(page);

		Map<Integer, String> stationMaps = new HashMap<Integer, String>();
		stationMaps.put(0, "井下定位");
		stationMaps.put(1, "井口考勤");
		stationMaps.put(2, "井底（考勤）");
		stationMaps.put(3, "井下禁区");
		for (LocationStation station : page.getResult()) {
			ArrayList<Criterion> criterions = Lists.newArrayList();
			if (state != null) {
				criterions.add(Restrictions.eq("state", 3));
			}
			station.setTypeString(stationMaps.get(station.getType()));
			criterions.add(Restrictions.eq("id.mineId", station.getId().getMineId()));
			criterions.add(Restrictions.eq("curStationId", station.getId().getStationId()));
			station.setCurPersonNum(locationStaffService.count(criterions.toArray(new Criterion[0])));
			Date date = new Date();
			Timestamp nousedate = new Timestamp(date.getTime());
			station.setDataTime(nousedate);
		}
		return new Response(page);
	}

	/**
	 * 返回分站内人员信息详情数据
	 */
	@RequestMapping(value = "/location/location-stations-detail", method = RequestMethod.GET)
	@ResponseBody
	public Response stationStaffDatasDetail(Page<LocationStaff> page, String stationId) throws ParseException {
		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", stationId));
		return new Response(page);
	}
}