package cn.ccrise.spimp.monitor.web;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;
import cn.ccrise.spimp.monitor.service.MonitorRealDatasService;

/**
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class MonitorRealDatasController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MonitorRealDatasService monitorRealDatasService;
	@Autowired
	private MonitorNodeService monitorNodeService;

	@RequestMapping(value = "/monitor/monitor-real-datas/max", method = RequestMethod.GET)
	@ResponseBody
	public Response max(Integer sensorTypeId, String date) {
		if (sensorTypeId == null) {
			return new Response(false);
		}

		Query query = monitorRealDatasService
				.getDAO()
				.createQuery(
						"select max(rd.realData) from MonitorRealDatas rd"
								+ " where exists(select n.id.nodeId from MonitorNode n"
								+ " where n.id.mineId=rd.id.mineId and n.id.nodeId=rd.id.nodeId and n.sensorTypeId=:sensorTypeId)"
								+ " and rd.id.dataTime between :startTime and :endTime");
		query.setInteger("sensorTypeId", sensorTypeId);
		query.setString("startTime", date + " 00:00:00");
		query.setString("endTime", date + " 23:59:59");

		Object max = query.list().get(0);
		if (max == null) {
			return new Response(false);
		}

		Query query2 = monitorRealDatasService
				.getDAO()
				.createQuery(
						"select rd.realData,n.nodePlace from MonitorRealDatas rd,MonitorNode n"
								+ " where rd.realData=:realData and n.sensorTypeId=:sensorTypeId and rd.id.dataTime between :startTime and :endTime and n.id.mineId=rd.id.mineId and n.id.nodeId=rd.id.nodeId"
								+ " group by rd.realData,n.nodePlace");
		query2.setDouble("realData", Double.parseDouble(max.toString()));
		query2.setInteger("sensorTypeId", sensorTypeId);
		query2.setString("startTime", date + " 00:00:00");
		query2.setString("endTime", date + " 23:59:59");

		return new Response(query2.list());
	}
}
