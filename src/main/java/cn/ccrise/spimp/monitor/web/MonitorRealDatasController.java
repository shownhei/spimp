package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.monitor.web.entity.RealData;

import com.google.common.collect.Lists;

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

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monitor/realdatas-3d/realdata", method = RequestMethod.GET)
	@ResponseBody
	public Response getRealData3D(String mineId) {
		String sql = "SELECT COUNT(*) AS count,(select COUNT(*) from MonitorNode nodeone where nodeone.sensortypeid between 0 and 99 and nodeone.id.mineId=:mineID) as moli"
				+ ",(select COUNT(*) from MonitorNode nodetwo where nodetwo.sensortypeid>99 and nodetwo.id.mineId=:mineID) as kaiguan,"
				+ "(select nodethree.nodeplace from MonitorNode nodethree where nodethree.currentdata=(select MAX(nodefour.currentdata) from MonitorNode nodefour where nodefour.sensortypeid=1 and nodefour.id.mineId=:mineID)) as place,"
				+ "(select MAX(nodefive.currentdata) from MonitorNode nodefive where nodefive.sensortypeid=1 and nodefive.id.mineId=:mineID) as maxdata,"
				+ "(select nodesix.nodeplace from MonitorNode nodesix where nodesix.currentdata=(select MAX(nodeseven.currentdata) from MonitorNode nodeseven where nodeseven.sensortypeid=4 adn nodeseven.id.mineId=:mineID)) as COplace,"
				+ "(select MAX(nodeeight.currentdata) from MonitorNode nodeeight where nodeeight.sensortypeid=4 adn nodeeight.id.mineId=:mineID) as COmaxdata  FROM MonitorNode node";
		Query resultQuery = monitorRealDatasService.getDAO().createQuery(sql);
		resultQuery.setParameter("mineID", mineId);
		List<Object[]> results = Lists.newArrayList();
		results = resultQuery.list();
		RealData realData = new RealData();
		realData.setCount(String.valueOf(results.get(0)[0]));
		realData.setSimulateCount(String.valueOf(results.get(0)[1]));
		realData.setSwitchCount(String.valueOf(results.get(0)[2]));
		realData.setCh4Place(String.valueOf(results.get(0)[3]));
		realData.setCh4Data(String.valueOf(results.get(0)[4]));
		realData.setCh4Place(String.valueOf(results.get(0)[5]));
		realData.setCoData(String.valueOf(results.get(0)[6]));
		return new Response(realData);
	}

	@RequestMapping(value = "/monitor/nodedata-3d/realdata", method = RequestMethod.GET)
	@ResponseBody
	public Response getNodeData3D(String nodeId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		criterions.add(Restrictions.eq("id.nodeId", nodeId));
		Map<String, String> datas = new HashMap<String, String>();
		datas.put(nodeId, monitorNodeService.findUnique(criterions.toArray(new Criterion[0])).getCurrentData()
				.toString());
		return new Response(datas);
	}
}
