/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.location.entity.Attendance;
import cn.ccrise.spimp.location.entity.DownMineRecord;
import cn.ccrise.spimp.location.entity.LocationAttendance;
import cn.ccrise.spimp.location.service.LocationAttendanceService;
import cn.ccrise.spimp.location.service.LocationStaffService;
import cn.ccrise.spimp.util.TemplateUtil;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * LocationAttendance Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationAttendanceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LocationStaffService locationStaffService;
	@Autowired
	private LocationAttendanceService locationAttendanceService;

	/**
	 * 领导带班-下井记录查询
	 */
	@RequestMapping(value = "/location/location-attendances-staff", method = RequestMethod.GET)
	@ResponseBody
	public Response attendance(Page<DownMineRecord> page, String recordQueryIn, String startTime, String endTime)
			throws UnsupportedEncodingException {
		if (!Strings.isNullOrEmpty(recordQueryIn)) {
			recordQueryIn = URLDecoder.decode(recordQueryIn, "UTF-8");
		}
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
		String hql = "SELECT staff.id.staffId,staff.name,staff.department,staff.jobName,staff.troopName,attendance.startTime,attendance.endTime,staff.state "
				+ "From LocationStaff staff,LocationAttendance attendance "
				+ "WHERE staff.id.staffId=attendance.staffId ";
		tempTable.append(hql);
		// 查询行数与查询结果通用条件
		if (!Strings.isNullOrEmpty(recordQueryIn)) {
			filterTable.append(" AND (staff.department LIKE '%").append(recordQueryIn)
					.append("%' OR staff.jobName LIKE '%").append(recordQueryIn).append("%' OR staff.name LIKE '%")
					.append(recordQueryIn).append("%' OR staff.troopName LIKE '%").append(recordQueryIn).append("%')");
		}
		if (StringUtils.isNotBlank(startTime)) {
			filterTable.append(" AND attendance.startTime >= CONVERT(DATETIME, '").append(startTime).append("', 102) ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			filterTable.append(" AND attendance.endTime <= CONVERT(DATETIME, '").append(endTime).append("', 102) ");
		}
		tempTable.append(filterTable);
		// 查询结果条数hql语句
		String countHql = "SELECT count(*) " + "From LocationStaff staff,LocationAttendance attendance "
				+ "WHERE staff.id.staffId=attendance.staffId ";
		StringBuffer countHqlBuffer = new StringBuffer();
		countHqlBuffer.append(countHql).append(filterTable);
		Long totalRows = (Long) locationStaffService.getDAO().createQuery(countHqlBuffer.toString()).uniqueResult();
		if (totalRows > 0) {
			@SuppressWarnings("unchecked")
			List<Object[]> results = locationStaffService.getDAO().createQuery(tempTable.toString())
					.setFirstResult(page.getPageSize() * (page.getPageNumber() - 1)).setMaxResults(page.getPageSize())
					.list();
			List<DownMineRecord> lists = Lists.newArrayList();
			for (Object[] result : results) {
				DownMineRecord downMineRecord = new DownMineRecord();
				downMineRecord.setStaffId(String.valueOf(result[0]));
				downMineRecord.setName(String.valueOf(result[1]));
				downMineRecord.setDepartment(String.valueOf(result[2]));
				downMineRecord.setJobType(String.valueOf(result[3]));
				downMineRecord.setTroopName(String.valueOf(result[4]));
				downMineRecord.setStartTime(String.valueOf(result[5]));
				downMineRecord.setEndTime(String.valueOf(result[6]));
				String state = stateMaps.get(Integer.parseInt(String.valueOf(result[7])));
				downMineRecord.setState(state);
				lists.add(downMineRecord);
			}
			page.setTotalCount(totalRows);
			page.setResult(lists);
			return new Response(page);
		} else {
			return new Response(page);
		}
	}

	/**
	 * 入井考勤记录Excel导出
	 */
	@RequestMapping(value = "/location/location-attendances-export", method = RequestMethod.GET)
	public void attendanceDetailExport(HttpServletResponse response, Page<Attendance> page, String department,
			String staffId, String startTime, String endTime) throws Exception {

		getAttendanceDetails(page, department, staffId, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "入井考勤", TemplateUtil.loadTemplate("AttendanceDetails.xml", results));
	}

	@RequestMapping(value = "/location/location-attendances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(locationAttendanceService.delete(id));
	}

	@RequestMapping(value = "/location/location-attendances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(locationAttendanceService.get(id));
	}

	/**
	 * 入井考勤详细查询
	 */
	@SuppressWarnings({ "unchecked" })
	public void getAttendanceDetails(Page<Attendance> page, String department, String staffId, String startTime,
			String endTime) {
		StringBuffer tempTable = new StringBuffer();
		StringBuffer filterTable = new StringBuffer();
		String regionString = "";
		// 查询结果hql语句
		String hql = "SELECT staff.id.staffId,staff.name,staff.department,staff.jobName,staff.troopName,attendance.startTime,attendance.endTime "
				+ "From LocationStaff staff,LocationAttendance attendance "
				+ "WHERE staff.id.staffId=attendance.staffId ";
		tempTable.append(hql);
		// 查询行数与查询结果通用条件
		if (!Strings.isNullOrEmpty(staffId)) {
			filterTable.append(" AND staff.id.staffId='").append(staffId).append("'");

		}
		if (StringUtils.isNotBlank(startTime)) {
			filterTable.append(" AND attendance.startTime >= CONVERT(DATETIME, '").append(startTime).append("', 102) ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			filterTable.append(" AND attendance.endTime <= CONVERT(DATETIME, '").append(endTime).append("', 102) ");
		}
		tempTable.append(filterTable);
		// 查询结果条数hql语句
		String countHql = "SELECT count(*) " + "From LocationStaff staff,LocationAttendance attendance "
				+ "WHERE staff.id.staffId=attendance.staffId ";
		StringBuffer countHqlBuffer = new StringBuffer();
		countHqlBuffer.append(countHql).append(filterTable);
		Long totalRows = (Long) locationStaffService.getDAO().createQuery(countHqlBuffer.toString()).uniqueResult();
		if (totalRows > 0) {
			List<Object[]> results = locationStaffService.createQuery(tempTable.toString())
					.setFirstResult(page.getPageSize() * (page.getPageNumber() - 1)).setMaxResults(page.getPageSize())
					.list();

			List<Attendance> lists = Lists.newArrayList();
			for (Object[] result : results) {
				Attendance attendance = new Attendance();
				attendance.setStaffId(String.valueOf(result[0]));
				attendance.setName(String.valueOf(result[1]));
				attendance.setDepartment(String.valueOf(result[2]));
				attendance.setJobType(String.valueOf(result[3]));
				attendance.setTroopName(String.valueOf(result[4]));
				attendance.setStartTime(String.valueOf(result[5]));
				attendance.setEndTime(String.valueOf(result[6]));
				if (result[5] != null && result[6] != null) {
					Timestamp timeStart = Timestamp.valueOf(String.valueOf(result[5]));
					Timestamp timeEnd = Timestamp.valueOf(String.valueOf(result[6]));
					long betweenTime = timeEnd.getTime() - timeStart.getTime();
					long days = betweenTime / (1000 * 60 * 60 * 24);
					long hours = (betweenTime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
					long min = (betweenTime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
					regionString = days + "天" + hours + "小时" + min + "分钟";
				}
				attendance.setTimeRegion(regionString);
				lists.add(attendance);
			}
			page.setTotalCount(totalRows);
			page.setResult(lists);
		}
	}

	@RequestMapping(value = "/location/location-attendance", method = RequestMethod.GET)
	public String index() {
		return "location/location-attendance/index";
	}

	@RequestMapping(value = "/location/location-attendances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationAttendance> page) {
		return new Response(locationAttendanceService.getPage(page));
	}

	/**
	 * 下井考勤查询
	 */
	@RequestMapping(value = "/location/location-attendances-query", method = RequestMethod.GET)
	@ResponseBody
	public Response query(Page<Attendance> page, String department, String staffId, String startTime, String endTime) {
		getAttendanceDetails(page, department, staffId, startTime, endTime);
		return new Response(page);
	}

	@RequestMapping(value = "/location/location-attendances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody LocationAttendance locationAttendance) {
		return new Response(locationAttendanceService.save(locationAttendance));
	}

	@RequestMapping(value = "/location/location-attendances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationAttendance locationAttendance, @PathVariable long id) {
		return new Response(locationAttendanceService.update(locationAttendance));
	}

	private void exportExcel(HttpServletResponse response, String fileName, String sourceCode) throws Exception {
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")
				+ ".xls");
		response.getWriter().print(sourceCode);
	}
}