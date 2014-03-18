package cn.ccrise.spimp.monitor.web;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.MonitorAlarm;
import cn.ccrise.spimp.monitor.entity.MonitorFiveMinutesData;
import cn.ccrise.spimp.monitor.entity.MonitorRealData;
import cn.ccrise.spimp.monitor.entity.MonitorSensorType;
import cn.ccrise.spimp.monitor.entity.MonitorState;
import cn.ccrise.spimp.monitor.entity.MonitorValueChange;
import cn.ccrise.spimp.monitor.service.MonitorAlarmService;
import cn.ccrise.spimp.monitor.service.MonitorFiveMinutesDataService;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorRealDataService;
import cn.ccrise.spimp.monitor.service.MonitorSensorTypeService;
import cn.ccrise.spimp.monitor.service.MonitorStateService;
import cn.ccrise.spimp.monitor.service.MonitorValueChangeService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ResponseDataFilter;
import cn.ccrise.spimp.util.TemplateUtil;

import com.google.common.collect.Lists;

/**
 * HistoryQuery Controller.
 * <p>
 * 历史数据查询控制类
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class HistoryQueryController {

	@Autowired
	private MonitorAlarmService monitorAlarmService;

	@Autowired
	private MonitorFiveMinutesDataService monitorFiveMinutesDataService;

	@Autowired
	private MonitorRealDataService monitorRealDataService;

	@Autowired
	private MonitorValueChangeService monitorValueChangeService;

	@Autowired
	private MonitorSensorTypeService monitorSensorTypeService;

	@Autowired
	private MonitorStateService monitorStateService;

	@Autowired
	private MonitorNodeService monitorNodeService;

	// 报警统计
	/**
	 * 报警统计(表格)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/alarm-datas-statistic", method = RequestMethod.GET)
	@ResponseBody
	public Response alarmDatasStatistic(Page<MonitorAlarm> page, Integer monitorSensorType, Integer monitorState,
			String startTime, String endTime) {

		genAlarmDatasStatisticDatas(page, monitorSensorType, monitorState, startTime, endTime);

		return new Response(page);
	}

	/**
	 * 报警统计(明细)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/alarm-datas-statistic-detail", method = RequestMethod.GET)
	@ResponseBody
	public Response alarmDatasStatisticDetail(Page<MonitorAlarm> page, String nodeId, String sensorName,
			String nodePlace) {

		genAlarmDatas(page, null, null, null, null, null, nodeId, sensorName, nodePlace);

		return new Response(page);
	}

	/**
	 * 报警统计(导出)
	 * 
	 * @param response
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/alarm-datas-statistic-export", method = RequestMethod.GET)
	public void alarmDatasStatisticExport(HttpServletResponse response, Page<MonitorAlarm> page,
			Integer monitorSensorType, Integer monitorState, String startTime, String endTime) throws Exception {

		genAlarmDatasStatisticDatas(page, monitorSensorType, monitorState, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "报警统计", TemplateUtil.loadTemplate("AlarmDatasStatisticExport.xml", results));
	}

	@RequestMapping(value = "/monitor/alarm-datas-statistic/detail", method = RequestMethod.GET)
	public ModelAndView alarmDetail(String param) {
		ModelAndView modelAndView = new ModelAndView("monitor/query/alarmDetail");
		Map<String, String> params = new HashMap<String, String>();
		params.put("param", param);
		modelAndView.addObject("datas", params);
		return modelAndView;
	}

	/**
	 * 获取当前用户所关注的报警类型
	 * 
	 * @param httpSession
	 * @param monitorSensorType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monitor/curuser-selected-monitor-states", method = RequestMethod.GET)
	@ResponseBody
	public Response curUserSelectedMS(HttpSession httpSession, Integer monitorSensorType) {
		Account account = ((Account) WebUtils.getAccount(httpSession));
		Long accountid = account.getId();

		StringBuffer hql = new StringBuffer("select ms.stateId,ms.stateName from AlarmConfig ac,MonitorState ms ")
				.append("where ac.value=:acValue and ac.type=:acType and ac.accountId=:userId and ac.name = ms.stateName ")
				.append("order by ms.stateId");

		List<Object[]> results = monitorSensorTypeService.createQuery(hql.toString()).setString("acValue", "true")
				.setString("acType", "prompt").setLong("userId", accountid).list();

		if (results != null && results.size() > 0) {
			List<MonitorState> response = new ArrayList<MonitorState>(results.size());
			for (int i = 0; i < results.size(); i++) {
				Object[] result = results.get(i);

				MonitorState ms = new MonitorState();
				ms.setStateId(NumberUtils.toInt(String.valueOf(result[0])));
				ms.setStateName(String.valueOf(result[1]));

				response.add(ms);
			}
			return new Response(response);
		}

		return new Response(0);
	}

	// 模拟量查询
	/**
	 * 模拟量查询(表格)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/five-minute-datas", method = RequestMethod.GET)
	@ResponseBody
	public Response fiveMinutesData(Page<MonitorFiveMinutesData> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {

		genFiveMinutesData(page, monitorSensorType, nodePlace, startTime, endTime);

		return new Response(page);
	}

	/**
	 * 模拟量查询(导出)
	 * 
	 * @param response
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/five-minute-datas-export", method = RequestMethod.GET)
	public void fiveMinutesDataExport(HttpServletResponse response, Page<MonitorFiveMinutesData> page,
			Integer monitorSensorType, String nodePlace, String startTime, String endTime) throws Exception {

		genFiveMinutesData(page, monitorSensorType, nodePlace, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "模拟量导出", TemplateUtil.loadTemplate("FiveMinuteDatasExport.xml", results));
	}

	/**
	 * 报警明细(数据)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param timeLast
	 * @param startTime
	 * @param endTime
	 */
	public void genAlarmDatas(Page<MonitorAlarm> page, Integer monitorSensorType, Integer monitorState,
			Integer timeLast, String startTime, String endTime, String nodeId, String sensorName, String nodePlace) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}

		if (monitorState != null) {
			criterions.add(Restrictions.eq("stateId", monitorState));
		}

		if (timeLast != null) {
			criterions.add(Restrictions.sqlRestriction("datediff(ss, starttime, endtime) > ?", timeLast,
					IntegerType.INSTANCE));
		}

		if (StringUtils.isNotBlank(startTime)) {
			criterions.add(Restrictions.ge("startTime", Timestamp.valueOf(startTime)));
		}

		if (StringUtils.isNotBlank(endTime)) {
			criterions.add(Restrictions.le("startTime", Timestamp.valueOf(endTime)));
		}

		if (StringUtils.isNotBlank(nodeId)) {
			criterions.add(Restrictions.eq("nodeId", nodeId));
		}

		if (StringUtils.isNotBlank(sensorName)) {
			criterions.add(Restrictions.eq("sensorName", sensorName));
		}

		if (StringUtils.isNotBlank(nodePlace)) {
			criterions.add(Restrictions.eq("nodePlace", nodePlace));
		}

		monitorAlarmService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		Map<String, Double[]> nodeUpperCutValue = monitorNodeService.getNodeUpperCutValue();
		for (MonitorAlarm monitorAlarm : page.getResult()) {
			ResponseDataFilter.filter(monitorAlarm, monitorStateCache, monitorSensorTypeCache);
			Double[] ucValue = nodeUpperCutValue.get(monitorAlarm.getNodeId());
			if (ucValue != null) {
				monitorAlarm.setAlarmUpperValue(ucValue[0]);
				monitorAlarm.sethCutValue(ucValue[1]);
			}
			monitorAlarm.setTimeDiff(timeDiff(monitorAlarm.getStartTime(), monitorAlarm.getEndTime()));
		}
	}

	/**
	 * 报警统计(数据)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param startTime
	 * @param endTime
	 */
	@SuppressWarnings("unchecked")
	public void genAlarmDatasStatisticDatas(Page<MonitorAlarm> page, Integer monitorSensorType, Integer monitorState,
			String startTime, String endTime) {
		StringBuffer tempTable = new StringBuffer();
		tempTable
				.append("select NodeID,SensorName,NodePlace,SUM(TotRecCount) as TotRecCount,SUM(DATEDIFF(ss,StartTime,EndTime)) as SecondDiff ")
				.append("from k_alarm where 1=1 ");

		if (monitorSensorType != null) {
			tempTable.append(" and SensorTypeId=").append(monitorSensorType);
		}

		if (monitorState != null) {
			tempTable.append(" and stateId=").append(monitorState);
		}

		if (StringUtils.isNotBlank(startTime)) {
			tempTable.append(" and startTime >= CONVERT(DATETIME, '").append(startTime).append("', 102) ");
		}

		if (StringUtils.isNotBlank(endTime)) {
			tempTable.append(" and startTime <= CONVERT(DATETIME, '").append(endTime).append("', 102) ");
		}

		tempTable.append(" group by NodeID,SensorName,NodePlace ");

		StringBuffer countSql = new StringBuffer("select count(*) from (").append(tempTable.toString()).append(
				") as tempTable");

		StringBuffer pageQuerySql = new StringBuffer(
				"SELECT TOP (:pageSize) NodeID,SensorName,NodePlace,TotRecCount,SecondDiff ")
				.append("FROM (SELECT ROW_NUMBER() OVER (ORDER BY NodeID) AS RowNumber,* FROM( ")
				.append(tempTable.toString()).append(") as tempTable) A WHERE RowNumber > :rowNumber ");

		Integer totalRows = (Integer) monitorAlarmService.getDAO().createSQLQuery(countSql.toString()).uniqueResult();
		List<Object[]> results = monitorAlarmService.getDAO().createSQLQuery(pageQuerySql.toString())
				.setParameter("pageSize", page.getPageSize())
				.setParameter("rowNumber", page.getPageSize() * (page.getPageNumber() - 1)).list();

		List<MonitorAlarm> lists = new ArrayList<MonitorAlarm>(page.getPageSize());
		for (Object[] result : results) {
			MonitorAlarm ma = new MonitorAlarm();
			ma.setNodeId(String.valueOf(result[0]));
			ma.setSensorName(String.valueOf(result[1]));
			ma.setNodePlace(String.valueOf(result[2]));
			ma.setTotalAlarmCount(NumberUtils.toInt(String.valueOf(result[3]), 0));
			ma.setTimeDiff(secondDiff(NumberUtils.toLong(String.valueOf(result[4]), 0L) * 1000));
			lists.add(ma);
		}

		page.setTotalCount(totalRows);
		page.setResult(lists);
	}

	/**
	 * 模拟量查询(导出)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 */
	public void genFiveMinutesData(Page<MonitorFiveMinutesData> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}

		if (nodePlace != null) {
			criterions.add(Restrictions.eq("nodeId", nodePlace));
		}

		if (StringUtils.isNotBlank(startTime)) {
			criterions.add(Restrictions.ge("dataTime", Timestamp.valueOf(startTime)));
		}

		if (StringUtils.isNotBlank(endTime)) {
			criterions.add(Restrictions.le("dataTime", Timestamp.valueOf(endTime)));
		}

		monitorFiveMinutesDataService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		for (MonitorFiveMinutesData monitorFiveMinutesData : page.getResult()) {
			ResponseDataFilter.filter(monitorFiveMinutesData, monitorSensorTypeCache);
		}
	}

	/**
	 * 密采查询(数据查询)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 */
	public void genMonitorRealData(Page<MonitorRealData> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}

		if (nodePlace != null) {
			criterions.add(Restrictions.eq("nodeId", nodePlace));
		}

		if (StringUtils.isNotBlank(startTime)) {
			criterions.add(Restrictions.ge("createTime", Timestamp.valueOf(startTime)));
		}

		if (StringUtils.isNotBlank(endTime)) {
			criterions.add(Restrictions.le("createTime", Timestamp.valueOf(endTime)));
		}

		monitorRealDataService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		Map<Integer, MonitorState> monitorStateCache = monitorStateService.getAllInstanceAsMap();
		for (MonitorRealData monitorRealData : page.getResult()) {
			ResponseDataFilter.filter(monitorRealData, monitorSensorTypeCache, monitorStateCache);
		}
	}

	/**
	 * 开关量查询(数据)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 */
	public void genValueChangeDatas(Page<MonitorValueChange> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (monitorSensorType != null) {
			criterions.add(Restrictions.eq("sensorTypeId", monitorSensorType));
		}

		if (nodePlace != null) {
			criterions.add(Restrictions.eq("nodeId", nodePlace));
		}

		if (StringUtils.isNotBlank(startTime)) {
			criterions.add(Restrictions.ge("startTime", Timestamp.valueOf(startTime)));
		}

		if (StringUtils.isNotBlank(endTime)) {
			criterions.add(Restrictions.le("startTime", Timestamp.valueOf(endTime)));
		}

		monitorValueChangeService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤关联数据
		Map<Integer, MonitorSensorType> monitorSensorTypeCache = monitorSensorTypeService.getAllInstanceAsMap();
		for (MonitorValueChange monitorValueChange : page.getResult()) {
			ResponseDataFilter.filter(monitorValueChange, monitorSensorTypeCache);
		}
	}

	// 密采查询
	/**
	 * 密采查询(表格)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/real-datas", method = RequestMethod.GET)
	@ResponseBody
	public Response monitorRealData(Page<MonitorRealData> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {

		genMonitorRealData(page, monitorSensorType, nodePlace, startTime, endTime);

		return new Response(page);
	}

	/**
	 * 密采查询(导出)
	 * 
	 * @param response
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/real-datas-export", method = RequestMethod.GET)
	public void monitorRealDataExport(HttpServletResponse response, Page<MonitorRealData> page,
			Integer monitorSensorType, String nodePlace, String startTime, String endTime) throws Exception {

		genMonitorRealData(page, monitorSensorType, nodePlace, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "密采查询导出", TemplateUtil.loadTemplate("RealDataExport.xml", results));
	}

	// 报警明细
	/**
	 * 报警明细(表格)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param timeLast
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/alarm-datas", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<MonitorAlarm> page, Integer monitorSensorType, Integer monitorState, Integer timeLast,
			String startTime, String endTime) {

		genAlarmDatas(page, monitorSensorType, monitorState, timeLast, startTime, endTime, null, null, null);

		return new Response(page);
	}

	/**
	 * 报警明细(导出)
	 * 
	 * @param response
	 * @param page
	 * @param monitorSensorType
	 * @param monitorState
	 * @param timeLast
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/alarm-datas-export", method = RequestMethod.GET)
	public void pageExport(HttpServletResponse response, Page<MonitorAlarm> page, Integer monitorSensorType,
			Integer monitorState, Integer timeLast, String startTime, String endTime) throws Exception {

		genAlarmDatas(page, monitorSensorType, monitorState, timeLast, startTime, endTime, null, null, null);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "报警明细", TemplateUtil.loadTemplate("AlarmDatasExport.xml", results));
	}

	// 开关量查询
	/**
	 * 开关量查询(表格)
	 * 
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/monitor/value-change", method = RequestMethod.GET)
	@ResponseBody
	public Response valueChange(Page<MonitorValueChange> page, Integer monitorSensorType, String nodePlace,
			String startTime, String endTime) {

		genValueChangeDatas(page, monitorSensorType, nodePlace, startTime, endTime);

		return new Response(page);
	}

	/**
	 * 开关量查询(导出)
	 * 
	 * @param response
	 * @param page
	 * @param monitorSensorType
	 * @param nodePlace
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	@RequestMapping(value = "/monitor/value-change-export", method = RequestMethod.GET)
	public void valueChangeExport(HttpServletResponse response, Page<MonitorValueChange> page,
			Integer monitorSensorType, String nodePlace, String startTime, String endTime) throws Exception {

		genValueChangeDatas(page, monitorSensorType, nodePlace, startTime, endTime);

		Map<String, Object> results = new HashMap<String, Object>();
		results.put("datas", page.getResult());

		exportExcel(response, "开关量导出", TemplateUtil.loadTemplate("ValueChangeExport.xml", results));
	}

	/**
	 * 通用EXCEL导出接口
	 * 
	 * @param response
	 * @param fileName
	 * @param sourceCode
	 * @throws Exception
	 */
	private void exportExcel(HttpServletResponse response, String fileName, String sourceCode) throws Exception {
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8")
				+ ".xls");
		response.getWriter().print(sourceCode);
	}

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

	private String timeDiff(Timestamp startTime, Timestamp endTime) {
		// 计算出小时数
		long timeDiffValue = endTime.getTime() - startTime.getTime();

		return secondDiff(timeDiffValue);
	}
}
