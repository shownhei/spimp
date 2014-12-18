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

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.location.entity.Department;
import cn.ccrise.spimp.location.entity.Leader;
import cn.ccrise.spimp.location.entity.LocationStaff;
import cn.ccrise.spimp.location.entity.LocationStation;
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

	/**
	 * 获得所有的部门
	 */
	@RequestMapping(value = "/location/location-staffs/alldepartment", method = RequestMethod.GET)
	@ResponseBody
	public Response getAllDepartment() {
		return new Response(locationStaffService.getAllDepartment());
	}

	/**
	 * 获得给定部门的所有人员
	 */
	@RequestMapping(value = "/location/location-staffs/departmentstaff", method = RequestMethod.GET)
	@ResponseBody
	public Response getDepartmentStaff(String department) {
		System.out.println("in...." + department);
		return new Response(locationStaffService.getDepartmentStaff(department));
	}

	/**
	 * 实时下井总人数
	 */
	@RequestMapping(value = "/location/location-staffs/count", method = RequestMethod.GET)
	@ResponseBody
	public Response count() {
		return new Response(locationStaffService.count(Restrictions.ge("state", 3)));
	}

	/**
	 * 得到所有部门或者根据人员信息得到对应部门信息
	 */
	@RequestMapping(value = "/location/location-staffs-department", method = RequestMethod.GET)
	@ResponseBody
	public Response departments(String staffId) {
		List<Department> lists = Lists.newArrayList();
		Set<String> departments = new TreeSet<String>();
		if (!Strings.isNullOrEmpty(staffId)) {
			for (LocationStaff locationStaff : locationStaffService.findBy("id.staffId", staffId)) {
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
	 * 按照读卡器编号或安装位置查询在线人数，包括人员名称、职务、工种、部门、联系电话等。
	 */
	@RequestMapping(value = "/location/location-staffs/detail", method = RequestMethod.GET)
	@ResponseBody
	public Response detail(String staffId, String pos, String curStationId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (staffId != null) {
			criterions.add(Restrictions.eq("id.staffId", staffId));
		}
		if (curStationId != null) {
			criterions.add(Restrictions.eq("curStationId", curStationId));
		}
		if (pos != null) {
			List<String> stationsId = Lists.newArrayList();

			for (LocationStation locationStation : locationStationService.find(Restrictions.like("pos", pos,
					MatchMode.ANYWHERE))) {
				stationsId.add(locationStation.getId().getStationId());
			}

			criterions.add(Restrictions.in("curStationId", stationsId));
		}

		return new Response(locationStaffService.find(criterions.toArray(new Criterion[0])));
	}

	/**
	 * 人员基本信息查询
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
			}
		}
		return new Response(page);
	}

	/**
	 * 紧急避险系统中人员监测
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

	/**
	 * 实时下井领导
	 */
	@RequestMapping(value = "/location/location-staffs/leader", method = RequestMethod.GET)
	@ResponseBody
	public Response leader() {
		// List<LocationStaff> result =
		// locationStaffService.find(Restrictions.ge("state", 3),
		// Restrictions.or(Restrictions.eq("jobType", 2),
		// Restrictions.eq("jobType", 3)));

		List<LocationStaff> result = locationStaffService.find(Restrictions.ge("state", 2),
				Restrictions.eq("department", "矿领导"));

		List<Leader> leaders = Lists.newArrayList();
		for (LocationStaff locationStaff : result) {
			Leader leader = new Leader(locationStaff.getName(), locationStationService.findUnique(
					Restrictions.eq("id.mineId", locationStaff.getId().getMineId()),
					Restrictions.eq("id.stationId", locationStaff.getCurStationId())).getPos());
			leaders.add(leader);
		}

		return new Response(leaders);
	}

	/**
	 * 实时下井领导和井下人数-3D
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/location/location-staffs/leader-count", method = RequestMethod.GET)
	@ResponseBody
	public Response leaderAndCount(String mineId) {
		// List<LocationStaff> result =
		// locationStaffService.find(Restrictions.ge("state", 3),
		// Restrictions.or(Restrictions.eq("jobType", 2),
		// Restrictions.eq("jobType", 3)));
		String sql = "SELECT distinct COUNT(*),(select COUNT(*) from LocationStaff staffone where staffone.state=3 and staffone.id.mineId=:mineID) from LocationStaff staff";
		String leadersql = "select stafftwo.name,(select station.pos from LocationStation station where station.id.stationId =stafftwo.curStationId ) from LocationStaff stafftwo where stafftwo.department="
				+ "'矿领导'" + " and stafftwo.state=3 and stafftwo.id.mineId=:mineID";
		Query resultQuery = locationStaffService.getDAO().createQuery(sql);
		Query leaderQuery = locationStaffService.getDAO().createQuery(leadersql);
		resultQuery.setParameter("mineID", mineId);
		leaderQuery.setParameter("mineID", mineId);
		List<Object[]> results = Lists.newArrayList();
		results = resultQuery.list();
		List<Object[]> leaderResults = Lists.newArrayList();
		leaderResults = leaderQuery.list();
		List<Leader> leaders = Lists.newArrayList();
		for (Object[] result : leaderResults) {
			Leader leader = new Leader(String.valueOf(result[0]), String.valueOf(result[1]));
			leaders.add(leader);
		}
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("total", String.valueOf(results.get(0)[0]));
		data.put("inMine", String.valueOf(results.get(0)[1]));
		data.put("leaders", leaders);
		return new Response(data);
	}

	/**
	 * 查询当前分站人员信息列表
	 */
	@RequestMapping(value = "/location/station-staffs", method = RequestMethod.GET)
	@ResponseBody
	public Response getStationStaffs(String nodeId, Page<LocationStaff> page) {
		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", nodeId));
		return new Response(page);
	}

	/**
	 * 让3D访问返回页面
	 * 
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/location/station-staffs-index", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getStationStaffIndex(String stationId) {
		Map<String, String> node = new HashMap<String, String>();
		node.put("nodeId", stationId);
		return new ModelAndView("location/3D/index", node);
	}

	/**
	 * 查询当前分站人数
	 */
	@RequestMapping(value = "/location/location-staffs-count", method = RequestMethod.GET)
	@ResponseBody
	public Response getStationCount(String stationId) {
		Long count = locationStaffService.count(Restrictions.eq("curStationId", stationId));
		return new Response(count);
	}

	/**
	 * 查询当前区域人数
	 */
	@RequestMapping(value = "/location/location-staffs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response page(@PathVariable String id, Page<LocationStaff> page) {
		page = locationStaffService.getPage(page, Restrictions.eq("curAreaId", id));
		return new Response(page);
	}

	/**
	 * 得到所有人员信息或者根据部门信息得到对应人员信息
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
	 */
	@RequestMapping(value = "/location/location-station-staffs/{selectItemID}", method = RequestMethod.GET)
	@ResponseBody
	public Response station(@PathVariable String selectItemID, Page<LocationStaff> page) {
		page = locationStaffService.getPage(page, Restrictions.eq("curStationId", selectItemID));
		return new Response(page);
	}
}