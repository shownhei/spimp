/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.location.access.LocationTrackDAO;
import cn.ccrise.spimp.location.entity.LocationTrack;
import cn.ccrise.spimp.location.entity.Warn;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * LocationTrack Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationTrackService extends HibernateDataServiceImpl<LocationTrack, Long> {
	@Autowired
	private LocationTrackDAO locationTrackDAO;
	@Autowired
	private LocationStationService locationStationService;

	@Override
	public HibernateDAO<LocationTrack, Long> getDAO() {
		return locationTrackDAO;
	}

	public Page<Warn> getWarn(Page<Warn> page, String state, String department, String staffId, String startTime,
			String endTime, String warnQueryIn) {
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
		String hql = "SELECT staff.staffId,staff.name,staff.department,staff.jobName,staff.troopName,track.stationId,track.enterCurTime,track.state "
				+ "From LocationStaff staff,LocationTrack track "
				+ "WHERE staff.staffId=track.staffId AND track.state>3";
		tempTable.append(hql);
		if (!Strings.isNullOrEmpty(warnQueryIn)) {
			filterTable.append(" AND (staff.department LIKE '%").append(warnQueryIn)
					.append("%' OR staff.jobName LIKE '%").append(warnQueryIn).append("%' OR staff.department LIKE '%")
					.append(warnQueryIn).append("%' OR staff.troopName LIKE '%").append(warnQueryIn).append("%')");
		}
		if (!Strings.isNullOrEmpty(state)) {
			int switchData = Integer.parseInt(state);
			switch (switchData) {
			case 7:
				filterTable.append(" AND track.state='").append(7).append("'");
				break;
			case 6:
				filterTable.append(" AND track.state='").append(6).append("'");
				break;
			case 5:
				filterTable.append(" AND track.state='").append(5).append("'");
				break;
			default:
				break;
			}

		}
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
				+ "WHERE staff.staffId=track.staffId AND track.state>3";
		StringBuffer countHqlBuffer = new StringBuffer();
		countHqlBuffer.append(countHql).append(filterTable);
		Long totalRows = (Long) getDAO().createQuery(countHqlBuffer.toString()).uniqueResult();
		if (totalRows > 0) {
			@SuppressWarnings("unchecked")
			List<Object[]> results = getDAO().createQuery(tempTable.toString())
					.setFirstResult(page.getPageSize() * (page.getPageNumber() - 1)).setMaxResults(page.getPageSize())
					.list();
			List<Warn> lists = Lists.newArrayList();
			for (Object[] result : results) {
				Warn warn = new Warn();
				warn.setStaffId(String.valueOf(result[0]));
				warn.setName(String.valueOf(result[1]));
				warn.setDepartment(String.valueOf(result[2]));
				warn.setJobType(String.valueOf(result[3]));
				warn.setTroopName(String.valueOf(result[4]));
				warn.setStationId(String.valueOf(locationStationService.findUniqueBy("stationId",
						String.valueOf(result[5])).getPos()));
				warn.setEnterCurTime(String.valueOf(result[6]));
				String staffState = stateMaps.get(Integer.parseInt(String.valueOf(result[7])));
				warn.setState(staffState);
				lists.add(warn);
			}
			page.setTotalCount(totalRows);
			page.setResult(lists);
			return page;
		} else {
			return page;
		}

	}
}