/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
import cn.ccrise.spimp.location.entity.LocationSchedule;
import cn.ccrise.spimp.location.entity.Schedule;
import cn.ccrise.spimp.location.service.LocationScheduleService;
import cn.ccrise.spimp.location.service.LocationStaffService;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * LocationSchedule Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LocationScheduleController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LocationScheduleService locationScheduleService;
	@Autowired
	private LocationStaffService locationStaffService;

	@RequestMapping(value = "/location/location-schedules", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LocationSchedule> page) {
		return new Response(locationScheduleService.getPage(page));
	}

	@RequestMapping(value = "/location/location-schedules-staff", method = RequestMethod.POST)
	@ResponseBody
	public Response saveWithStaff(@Valid String staffid, String classes, String date) {
		String format = "yyyy-MM-dd";
		Date utilDate = null;
		try {
			utilDate = new Date((new SimpleDateFormat(format)).parse(date).getTime());
		} catch (Exception e) {

		}
		LocationSchedule locationSchedule = new LocationSchedule();
		locationSchedule.setClasses(classes);
		locationSchedule.setStaffid(staffid);
		locationSchedule.setDate(utilDate);
		return new Response(locationScheduleService.save(locationSchedule));
	}

	@RequestMapping(value = "/location/location-schedules-staff", method = RequestMethod.GET)
	@ResponseBody
	public Response schedules(Page<Schedule> page, String scheduleQueryIn, String startTime, String endTime)
			throws UnsupportedEncodingException {
		StringBuffer tempTable = new StringBuffer();
		StringBuffer filterTable = new StringBuffer();
		if (!Strings.isNullOrEmpty(scheduleQueryIn)) {
			scheduleQueryIn = URLDecoder.decode(scheduleQueryIn, "UTF-8");
		}
		String hql = "SELECT staff.id.staffId,staff.name,staff.department,staff.jobName,schedule.classes,schedule.date,schedule.id "
				+ "FROM LocationStaff staff,LocationSchedule schedule " + "WHERE staff.id.staffId=schedule.staffid ";
		tempTable.append(hql);
		if (!Strings.isNullOrEmpty(scheduleQueryIn)) {
			filterTable.append(" AND (staff.department LIKE '%").append(scheduleQueryIn)
					.append("%' OR staff.jobName LIKE '%").append(scheduleQueryIn).append("%' OR staff.name LIKE '%")
					.append(scheduleQueryIn).append("%' OR schedule.classes LIKE '%").append(scheduleQueryIn)
					.append("%')");
		}
		if (StringUtils.isNotBlank(startTime)) {
			filterTable.append(" AND schedule.date >= CONVERT(DATETIME, '").append(startTime).append("', 102) ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			filterTable.append(" AND schedule.date <= CONVERT(DATETIME, '").append(endTime).append("', 102) ");
		}
		tempTable.append(filterTable);
		String countHql = "SELECT count(*) " + "From LocationStaff staff,LocationSchedule schedule "
				+ "WHERE staff.id.staffId=schedule.staffid ";
		StringBuffer countHqlBuffer = new StringBuffer();
		countHqlBuffer.append(countHql).append(filterTable);

		System.out.println("countHqlBuffer.toString():" + countHqlBuffer.toString());

		Long totalRows = (Long) locationStaffService.getDAO().createQuery(countHqlBuffer.toString()).uniqueResult();
		if (totalRows > 0) {
			@SuppressWarnings("unchecked")
			List<Object[]> results = locationStaffService.getDAO().createQuery(tempTable.toString())
					.setFirstResult(page.getPageSize() * (page.getPageNumber() - 1)).setMaxResults(page.getPageSize())
					.list();
			List<Schedule> lists = Lists.newArrayList();
			for (Object[] result : results) {
				Schedule schedule = new Schedule();
				schedule.setStaffId(String.valueOf(result[0]));
				schedule.setName(String.valueOf(result[1]));
				schedule.setDepartment(String.valueOf(result[2]));
				schedule.setJobType(String.valueOf(result[3]));
				schedule.setClasses(String.valueOf(result[4]));
				schedule.setDate(Date.valueOf(String.valueOf(result[5])));
				schedule.setId(Long.parseLong(String.valueOf(result[6])));
				lists.add(schedule);
			}
			page.setTotalCount(totalRows);
			page.setResult(lists);
			return new Response(page);
		} else {
			return new Response(page);
		}
	}

	@RequestMapping(value = "/location/location-schedules/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody LocationSchedule locationSchedule, @PathVariable long id) {
		return new Response(locationScheduleService.update(locationSchedule));
	}
}