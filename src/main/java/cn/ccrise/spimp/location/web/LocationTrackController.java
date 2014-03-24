/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.location.entity.LocationTrack;
import cn.ccrise.spimp.location.entity.Track;
import cn.ccrise.spimp.location.entity.Warn;
import cn.ccrise.spimp.location.service.LocationStaffService;
import cn.ccrise.spimp.location.service.LocationStationService;
import cn.ccrise.spimp.location.service.LocationTrackService;
import cn.ccrise.spimp.util.TemplateUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * LocationTrack Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationTrackController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LocationTrackService locationTrackService;
	@Autowired
	private LocationStaffService locationStaffService;
	@Autowired
	private LocationStationService locationStationService;

	@RequestMapping(value = "/location/location-tracks/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(locationTrackService.delete(id));
	}

	/**
	 * 报警导出
	 * 
	 * @param response
	 * @param page
	 * @param type
	 * @param department
	 * @param staffId
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/location/location-warn-export", method = RequestMethod.GET)
	@ResponseBody
	public void exportWarn(HttpServletResponse response, Page<Warn> page, String type, String department,
			String staffId, String startTime, String endTime, String warnQueryIn) throws Exception {
		page = locationTrackService.getWarn(page, type, department, staffId, startTime, endTime, warnQueryIn);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());
		exportExcel(response, "入井考勤", TemplateUtil.loadTemplate("WarnDetails.xml", results));
	}

	/**
	 * 井下人员实时轨迹详情
	 * 
	 * @param id
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/location/location-tracks/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable String id, Page<LocationTrack> page) {
		Timestamp inMineTime = locationStaffService.findUniqueBy("staffId", id).getInMineTime();
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
		page = locationTrackService.getPage(page, Restrictions.gt("enterCurTime", inMineTime),
				Restrictions.eq("staffId", id));
		if (page.getResult().size() > 0) {
			for (LocationTrack track : page.getResult()) {
				track.setStateString(stateMaps.get(track.getState()));
				track.setStationId(String.valueOf(locationStationService.get(
						Long.parseLong(String.valueOf(track.getStationId()))).getPos()));
			}
		}
		return new Response(page);
	}

	public void getTrackDetails(Page<Track> page, String department, String staffId, String startTime, String endTime) {
		StringBuffer tempTable = new StringBuffer();
		StringBuffer filterTable = new StringBuffer();
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
		// 查询结果hql语句
		String hql = "SELECT staff.staffId,staff.name,staff.department,staff.jobName,staff.troopName,track.stationId,track.enterCurTime,track.state "
				+ "From LocationStaff staff,LocationTrack track " + "WHERE staff.staffId=track.staffId ";
		tempTable.append(hql);
		// 查询行数与查询结果通用条件
		if (!Strings.isNullOrEmpty(department)) {
			filterTable.append(" AND staff.department='").append(department).append("'");

		}
		if (!Strings.isNullOrEmpty(staffId)) {
			filterTable.append(" AND staff.staffId='").append(staffId).append("'");

		}
		if (StringUtils.isNotBlank(startTime)) {
			filterTable.append(" AND track.enterCurTime >= CONVERT(DATETIME, '").append(startTime).append("', 102) ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			filterTable.append(" AND track.enterCurTime <= CONVERT(DATETIME, '").append(endTime).append("', 102) ");
		}
		tempTable.append(filterTable);
		// 查询结果条数hql语句
		String countHql = "SELECT count(*) " + "From LocationStaff staff,LocationTrack track "
				+ "WHERE staff.staffId=track.staffId ";
		StringBuffer countHqlBuffer = new StringBuffer();
		countHqlBuffer.append(countHql).append(filterTable);
		Long totalRows = (Long) locationStaffService.getDAO().createQuery(countHqlBuffer.toString()).uniqueResult();
		if (totalRows > 0) {
			@SuppressWarnings("unchecked")
			List<Object[]> results = locationStaffService.getDAO().createQuery(tempTable.toString())
					.setFirstResult(page.getPageSize() * (page.getPageNumber() - 1)).setMaxResults(page.getPageSize())
					.list();
			List<Track> lists = Lists.newArrayList();
			for (Object[] result : results) {
				Track track = new Track();
				track.setStaffId(String.valueOf(result[0]));
				track.setName(String.valueOf(result[1]));
				track.setDepartment(String.valueOf(result[2]));
				track.setJobType(String.valueOf(result[3]));
				track.setTroopName(String.valueOf(result[4]));
				track.setStationName(String.valueOf(locationStationService.findUniqueBy("stationId",
						String.valueOf(result[5])).getPos()));
				track.setEnterCurTime(String.valueOf(result[6]));
				String state = stateMaps.get(Integer.parseInt(String.valueOf(result[7])));
				track.setState(state);
				track.setStationId(String.valueOf(result[5]));
				lists.add(track);
			}
			page.setTotalCount(totalRows);
			page.setResult(lists);
		}
	}

	@RequestMapping(value = "/location/location-track", method = RequestMethod.GET)
	public String index() {
		return "location/location-track/index";
	}

	@RequestMapping(value = "/location/location-tracks", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationTrack> page) {
		return new Response(locationTrackService.getPage(page));
	}

	/**
	 * 下井轨迹查询
	 * 
	 * @param page
	 * @param department
	 * @param staffs
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/location/location-tracks-query", method = RequestMethod.GET)
	@ResponseBody
	public Response query(Page<Track> page, String department, String staffId, String startTime, String endTime) {
		getTrackDetails(page, department, staffId, startTime, endTime);
		return new Response(page);
	}

	/**
	 * 报警查询
	 * 
	 * @param page
	 * @param type
	 * @param department
	 * @param staffId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/location/location-warn-query", method = RequestMethod.GET)
	@ResponseBody
	public Response queryWarn(Page<Warn> page, String type, String department, String staffId, String startTime,
			String endTime, String warnQueryIn) {
		page = locationTrackService.getWarn(page, type, department, staffId, startTime, endTime, warnQueryIn);
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-tracks", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody LocationTrack locationTrack) {
		return new Response(locationTrackService.save(locationTrack));
	}

	@RequestMapping(value = "/location/location-tracks-export", method = RequestMethod.GET)
	public void trackDetailExport(HttpServletResponse response, Page<Track> page, String department, String staffId,
			String startTime, String endTime) throws Exception {

		getTrackDetails(page, department, staffId, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "入井考勤", TemplateUtil.loadTemplate("TrackDetails.xml", results));
	}

	@RequestMapping(value = "/location/location-tracks/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationTrack locationTrack, @PathVariable long id) {
		return new Response(locationTrackService.update(locationTrack));
	}

	private void exportExcel(HttpServletResponse response, String fileName, String sourceCode) throws Exception {
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")
				+ ".xls");
		response.getWriter().print(sourceCode);
	}

	@SuppressWarnings("unused")
	private String secondDiff(long timeDiffValue) {
		long hours = Math.round(timeDiffValue / (3600 * 1000));

		// 计算相差分钟数
		long leave2 = timeDiffValue % (3600 * 1000);// 计算小时数后剩余的毫秒数
		long minutes = Math.round(leave2 / (60 * 1000));

		// 计算相差秒数
		long leave3 = leave2 % (60 * 1000);// 计算分钟数后剩余的毫秒数
		long seconds = Math.round(leave3 / 1000);

		return new StringBuffer().append(hours).append("小时").append(minutes).append("分钟").append(seconds).append("秒")
				.toString();
	}
}