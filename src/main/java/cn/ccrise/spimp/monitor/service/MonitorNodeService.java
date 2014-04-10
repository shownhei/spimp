package cn.ccrise.spimp.monitor.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorNodeDAO;
import cn.ccrise.spimp.monitor.entity.MonitorNode;
import cn.ccrise.spimp.monitor.entity.MonitorState;
import cn.ccrise.spimp.system.service.GroupService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * MonitorNode Service.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorNodeService extends HibernateDataServiceImpl<MonitorNode, Long> {
	@Autowired
	private MonitorNodeDAO monitorNodeDAO;
	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;
	@Autowired
	private MonitorStateService monitorStateService;
	@Autowired
	private GroupService groupService;

	private static int QUERY_TIMES = 0;
	private static Map<String, Double[]> nodeUpperCutValue = new HashMap<String, Double[]>();

	public String ch4AndCoStatistic() {
		return ch4OrCOAlarmTimesToday(1) + "/" + ch4OrCOAlarmTimesToday(2);
	}

	/**
	 * 瓦斯当日报警次数、CO当日超限次数 默认：瓦斯报警
	 * 
	 * @param type
	 *            1:CH4,2:CO
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public Long ch4OrCOAlarmTimesToday(int type) {
		Integer sensorTypeId = 1; // 1:甲烷 4:CO
		Integer stateId = 12; // 12：报警 2:超限

		if (type == 2) {
			sensorTypeId = 4;
			stateId = 2;
		}

		Calendar cal = Calendar.getInstance();
		String today = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-"
				+ cal.get(Calendar.DAY_OF_MONTH);
		Timestamp startTime = Timestamp.valueOf(today + " 00:00:00");
		Timestamp endTime = Timestamp.valueOf(today + " 23:59:59");

		StringBuffer sql = new StringBuffer();
		sql.append("select count(alarm.id) from MonitorAlarm alarm ")
				.append("where alarm.sensorTypeId=:sensorTypeId and alarm.stateId=:stateId ")
				.append("and alarm.startTime>=:startTime and alarm.startTime<=:endTime");

		List<Long> result = createQuery(sql.toString()).setInteger("sensorTypeId", sensorTypeId)
				.setInteger("stateId", stateId).setTimestamp("startTime", startTime).setTimestamp("endTime", endTime)
				.list();

		return result.get(0);
	}

	@Override
	public HibernateDAO<MonitorNode, Long> getDAO() {
		return monitorNodeDAO;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Double[]> getNodeUpperCutValue() {
		String sql = "select mn.id.nodeId,mn.alarmUpperValue,mn.hCutValue from MonitorNode mn";
		if (QUERY_TIMES % 10 != 0 && nodeUpperCutValue != null) {
			QUERY_TIMES++;
			return nodeUpperCutValue;
		}

		List<Object[]> results = createQuery(sql).list();
		for (int i = 0; i < results.size(); i++) {
			Object[] result = results.get(i);
			Double[] values = new Double[2];
			values[0] = NumberUtils.toDouble(String.valueOf(result[1]), 0d);
			values[1] = NumberUtils.toDouble(String.valueOf(result[2]), 0d);
			nodeUpperCutValue.put(String.valueOf(result[0]), values);
		}

		QUERY_TIMES = 0;

		return nodeUpperCutValue;
	}

	public String statisticByState(Integer type) {
		Map<Integer, Integer> statisticResult = statisticResult(type);

		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		int totalNum = 0;
		StringBuffer result = new StringBuffer();
		for (Map.Entry<Integer, MonitorState> monitorState : monitorStateCache.entrySet()) {
			result.append("、").append(monitorState.getValue().getStateName());

			Integer num = statisticResult.get(monitorState.getKey());
			result.append(num == null ? 0 : num);

			if (num != null) {
				totalNum += num;
			}
		}

		return "测点总数:" + totalNum + " 其中：" + result.toString().substring(1);
	}

	public String statisticByState(Integer type, String mineId, Integer monitorSensorType, Integer monitorState) {
		Map<Integer, Integer> statisticResult = statisticResult(type, mineId, monitorSensorType, monitorState);

		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		int totalNum = 0;
		StringBuffer result = new StringBuffer();
		for (Map.Entry<Integer, MonitorState> ms : monitorStateCache.entrySet()) {
			result.append("、").append(ms.getValue().getStateName());

			Integer num = statisticResult.get(ms.getKey());
			result.append(num == null ? 0 : num);

			if (num != null) {
				totalNum += num;
			}
		}

		return "测点总数:" + totalNum + " 其中：" + result.toString().substring(1);
	}

	@SuppressWarnings("unchecked")
	public Map<Integer, Integer> statisticResult(Integer type) {
		StringBuffer sql = new StringBuffer(
				"SELECT node.stateId as stateid,count(node.id) as num FROM MonitorNode node ");
		if (type != null && type != 0) {
			sql.append("where node.sensorTypeId>=:startNum and node.sensorTypeId<=:endNum ");
		}
		sql.append("group by node.stateId");

		Query query = createQuery(sql.toString());
		if (type != null && type != 0) {
			Integer[] protocolNumber = monitorSensorTypeService.protocolAnalysis(type);
			query.setInteger("startNum", protocolNumber[0]).setInteger("endNum", protocolNumber[1]);
		}

		List<Object[]> results = query.list();

		Map<Integer, Integer> revs = new HashMap<Integer, Integer>();
		for (Object[] result : results) {
			revs.put(NumberUtils.toInt(String.valueOf(result[0]), -1000),
					NumberUtils.toInt(String.valueOf(result[1]), -1000));
		}

		return revs;
	}

	public Map<Integer, Integer> statisticResult(Integer type, String mineId, Integer monitorSensorType,
			Integer monitorState) {
		ArrayList<Criterion> criterions = Lists.newArrayList();

		if (type != null && type != 0) {
			Integer[] protocolNumber = monitorSensorTypeService.protocolAnalysis(type);
			criterions.add(Restrictions.ge("sensorTypeId", protocolNumber[0]));
			criterions.add(Restrictions.le("sensorTypeId", protocolNumber[1]));
		}
		if (mineId != null) {
			criterions.add(Restrictions.in("mineId", groupService.getChildrenMines(mineId)));
		}
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}
		if (monitorState != null) {
			criterions.add(Restrictions.eq("stateId", monitorState));
		}
		criterions.add(Restrictions.eq("isValid", 1));

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("stateId"));
		projectionList.add(Projections.rowCount());

		@SuppressWarnings("unchecked")
		List<Object[]> res = getDAO().createCriteria(criterions.toArray(new Criterion[0]))
				.setProjection(projectionList).list();
		Map<Integer, Integer> result = Maps.newHashMap();
		for (Object[] o : res) {
			result.put(NumberUtils.toInt(String.valueOf(o[0]), -1000), NumberUtils.toInt(String.valueOf(o[1]), 0));
		}

		return result;
	}
}
