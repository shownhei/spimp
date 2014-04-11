/*
\ * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.validation.Valid;

import org.hibernate.criterion.MatchMode;
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

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.location.entity.Department;
import cn.ccrise.spimp.location.entity.LocationStaff;
import cn.ccrise.spimp.location.entity.StaffSelect;
import cn.ccrise.spimp.location.service.LocationStaffService;
import cn.ccrise.spimp.location.service.LocationStationService;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * LocationStaff Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationStaffController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LocationStaffService locationStaffService;
	@Autowired
	private LocationStationService locationStationService;

	@RequestMapping(value = "/location/location-staffs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(locationStaffService.delete(id));
	}

	/**
	 * 得到所有部门或者根据人员信息得到对应部门信息
	 * 
	 * @param staffId
	 * @return
	 */
	@RequestMapping(value = "/location/location-staffs-department", method = RequestMethod.GET)
	@ResponseBody
	public Response departments(String staffId) {
		List<Department> lists = Lists.newArrayList();
		Set<String> departments = new TreeSet<String>();
		if (!Strings.isNullOrEmpty(staffId)) {
			for (LocationStaff locationStaff : locationStaffService.findBy("staffId", staffId)) {
				departments.add(locationStaff.getDepartment());
			}
		} else {
			for (LocationStaff locationStaff : locationStaffService.getAll()) {
				departments.add(locationStaff.getDepartment());
			}
		}
		for (String name : departments) {
			Department department = new Department();
			department.setName(name);
			lists.add(department);
		}
		return new Response(lists);
	}

	/**
	 * 人员基本信息查询
	 * 
	 * @param page
	 * @param staffQueryIn
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/location/location-staffs", method = RequestMethod.GET)
	@ResponseBody
	public Response filters(Page<LocationStaff> page, String staffQueryIn) throws UnsupportedEncodingException {
		if (!Strings.isNullOrEmpty(staffQueryIn)) {
			staffQueryIn = URLDecoder.decode(staffQueryIn, "UTF-8");
		}
		if (staffQueryIn != null) {
			page = locationStaffService.getPage(
					page,
					Restrictions.or(Restrictions.ilike("name", staffQueryIn, MatchMode.ANYWHERE),
							Restrictions.ilike("department", staffQueryIn, MatchMode.ANYWHERE),
							Restrictions.ilike("jobName", staffQueryIn, MatchMode.ANYWHERE),
							Restrictions.ilike("jobName", staffQueryIn, MatchMode.ANYWHERE)));
		} else {
			page = locationStaffService.getPage(page);
		}
		Map<Integer, String> stateMaps = new HashMap<Integer, String>();
		stateMaps.put(0, "井上");
		stateMaps.put(1, "入井");
		stateMaps.put(2, "出井");
		stateMaps.put(3, "井下（正常）");
		stateMaps.put(4, "井下（求救）");
		stateMaps.put(5, "井下（进入禁区）");
		stateMaps.put(6, "井下（禁区求救）");
		stateMaps.put(7, "井下（超时）");
		stateMaps.put(8, "井下（特种人员偏离轨道）");
		if (page.getResult().size() > 0) {
			for (LocationStaff staff : page.getResult()) {
				if (staff.getState() != null) {
					staff.setStateString(stateMaps.get(staff.getState()));
				}
				if (staff.getCurStationId() != null) {
					staff.setCurStationId(String.valueOf((locationStationService.findUniqueBy("id.stationId",
							staff.getCurStationId())) != null ? (locationStationService.findUniqueBy("id.stationId",
							staff.getCurStationId()).getPos()) : ""));
				}
				// if (staff.getBirthday() != null) {
				// String[] dates = staff.getBirthday().split(" ");

				// staff.setBirthday(Integer.parseInt(dates[3]) + "-"
				// + Integer.parseInt(dates[0]) + "-"
				// + Integer.parseInt(dates[2]));
				// }
			}
		}
		return new Response(page);
	}

	/**
	 * 紧急避险系统中人员监测
	 * 
	 * @param page
	 * @param staffQueryIn
	 * @return
	 */
	@RequestMapping(value = "/location/location-staffs/hedge", method = RequestMethod.GET)
	@ResponseBody
	public Response filtersForHedge(Page<LocationStaff> page) {
		/**
		 * 人员监测数据从M_staff表查询， 需要关联M_station表，查询M_station中systype=H_bixian的数据，
		 * 通过M_station表中的stationid和M_staff表中的curstationid进行关联，
		 * 显示的样式同人员定位中实时监测的井下人员功能
		 */
		List<?> staffIdList = locationStaffService
				.getDAO()
				.getSession()
				.createQuery(
						"select staff.id from LocationStaff staff ,LocationStation station where staff.curStationId=station.stationId and station.sysType='H_bixian'")
				.list();
		if (staffIdList != null && staffIdList.size() > 0) {
			Iterator<?> it = staffIdList.iterator();
			Object currentRaw = null;
			ArrayList<Long> ids = new ArrayList<Long>();
			while (it.hasNext()) {
				currentRaw = it.next();
				ids.add((Long) currentRaw);
			}
			page = locationStaffService.getPage(page, Restrictions.in("id", ids));
		}
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-staff", method = RequestMethod.GET)
	public String index() {
		return "location/location-staff/index";
	}

	/**
	 * 查询当前区域人数
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/location/location-staffs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response page(@PathVariable String id, Page<LocationStaff> page) {
		page = locationStaffService.getPage(page, Restrictions.eq("curAreaId", id));
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-staffs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody LocationStaff locationStaff) {
		return new Response(locationStaffService.save(locationStaff));
	}

	/**
	 * 得到所有人员信息或者根据部门信息得到对应人员信息
	 * 
	 * @param department
	 * @return
	 */
	@RequestMapping(value = "/location/location-staffs-staff", method = RequestMethod.GET)
	@ResponseBody
	public Response staffs(String department) {
		List<StaffSelect> lists = Lists.newArrayList();
		if (!Strings.isNullOrEmpty(department)) {
			for (LocationStaff locationStaff : locationStaffService.findBy("department", department)) {
				StaffSelect select = new StaffSelect();
				select.setName(locationStaff.getName() + "|" + locationStaff.getId().getStaffId());
				select.setStaffId(locationStaff.getId().getStaffId());
				lists.add(select);
			}
		} else {
			for (LocationStaff locationStaff : locationStaffService.getAll()) {
				StaffSelect select = new StaffSelect();
				select.setName(locationStaff.getName() + "|" + locationStaff.getId().getStaffId());
				select.setStaffId(locationStaff.getId().getStaffId());
				lists.add(select);
			}
		}
		return new Response(lists);
	}

	/**
	 * 查询当前分站人员详情
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/location/location-station-staffs/{selectItemID}", method = RequestMethod.GET)
	@ResponseBody
	public Response station(@PathVariable String selectItemID, Page<LocationStaff> page) {
		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", selectItemID));
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-staffs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationStaff locationStaff, @PathVariable long id) {
		return new Response(locationStaffService.update(locationStaff));
	}
}