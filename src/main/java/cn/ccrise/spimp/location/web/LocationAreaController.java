/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
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
	 * 人机环
	 * 
	 * @param accidentRecord
	 * @returnd
	 */
	@RequestMapping(value = "/location/location-areas/rjhcommand", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView rjhCommand(String rjhParam) {
		StringBuilder buff = new StringBuilder();
		buff.append("{'ENRIROMENT':");
		buff.append("[{'DBID':'MineID:14291000017;NodeID:0000000102;','TABLE':'K_Node','TYPE':'ENRIROMENT'},");
		buff.append("{'DBID':'MineID:14291000017;NodeID:0000000103;','TABLE':'K_Node','TYPE':'ENRIROMENT'}");
		buff.append("],");
		buff.append("'EQIPMENT':[");
		buff.append("{'DBID':'MineID:14291000017;EQUIPMETNID:GP01','STATE':'暂无信号','TABLE':'electr_equipments','TYPE':'EQIPMENT'},");
		buff.append("{'DBID':'MineID:14291000017;EQUIPMETNID:7＃皮带机','STATE':'暂无信号','TABLE':'electr_transform_equipments','TYPE':'EQIPMENT'},");
		buff.append("{'DBID':'MineID:14291000017;EQUIPMETNID:aa','STATE':'暂无信号','TABLE':'electr_wind_water_equipments','TYPE':'EQIPMENT'},");
		buff.append("{'DBID':'MineID:14291000017;EQUIPMETNID:bb','STATE':'暂无信号','TABLE':'electr_fire_fighting_equipments','TYPE':'EQIPMENT'}");
		buff.append("],");
		buff.append("'PERSON':[");
		buff.append("{'DBID':'MineID:14040002001;StationID:0403;','TABLE':'','TYPE':'PERSON'},");
		buff.append("{'DBID':'MineID:14040002001;StationID:0505;','TABLE':'M_Station','TYPE':'PERSON'}");
		buff.append("]}");
		String json=buff.toString();
		if(StringUtils.isNotBlank(rjhParam)){
			json=rjhParam;
		}
		HashMap<String,Object> root = new HashMap<String,Object>();
		locationAreaService.deal(json,root);
		return new ModelAndView("3d/rjhTemplate",root);
	}

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
	 * 查询所有的读卡器所有的相关人员
	 * @param page
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/location/read_cards_staff", method = RequestMethod.GET)
	@ResponseBody
	public Response readCardsStaffs() {
		StringBuilder buff = new StringBuilder();
		buff.append("SELECT DISTINCT stn.MineId,stn.StationId,stf.StaffId,stf.Name,stf.CardId ");
		buff.append("FROM M_Station stn, M_Staff stf WHERE stf.CurStationId=stn.StationId and stf.state>=2");
		SQLQuery query=locationAreaService.getDAO().getSession().createSQLQuery(buff.toString());
		@SuppressWarnings("unchecked")
		List<Object> list=(List<Object>)query.list();
		Object []raw=null;
		HashMap<String,LinkedList<HashMap<String,Object>>> result=new HashMap<String,LinkedList<HashMap<String,Object>>>();
		String key=null;
		String mineId=null;
		String stationId=null;
		String staffId= null;
		String staffName=null;
		int index=0;
		LinkedList<HashMap<String,Object>> resultRaw= null;
		HashMap<String,Object> entity=null;
		buff.delete(0, buff.capacity());
		for(Object temp:list){
			raw=(Object [])temp;
			index=0;
			mineId=(String)raw[index++];
			stationId=(String)raw[index++];
			staffId=(String)raw[index++];
			staffName=(String)raw[index++];
			//build key
			buff.delete(0, buff.capacity());
			buff.append("MineID:");
			buff.append(mineId);
			buff.append(";");
			buff.append("StationID:");
			buff.append(stationId);
			key=buff.toString();
			if(!result.containsKey(key)){
				resultRaw = new LinkedList<HashMap<String,Object>> ();
				result.put(key, resultRaw);
			}
			resultRaw=result.get(key);
			entity = new HashMap<String,Object>();
			entity.put("STAFFID", staffId);
			entity.put("NAME", staffName);
			resultRaw.add(entity);
		}
		return new Response(result);
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