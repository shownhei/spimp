package cn.ccrise.spimp.monitor.web;

import java.util.ArrayList;
import java.util.List;

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
		String sql = "SELECT DISTINCT COUNT(*) AS count,(select COUNT(*) from K_Node nodeone where (nodeone.sensorTypeId between 0 and 99) and nodeone.mineId="
				+ mineId
				+ ") as moli,"
				+ "(select COUNT(*) from K_Node nodetwo where nodetwo.sensorTypeId>99 and nodetwo.mineId="
				+ mineId
				+ ") as kaiguan,"
				+ "(select Top 1 nodethree.nodePlace from K_Node nodethree where nodethree.currentData=(select MAX(nodefour.currentData) "
				+ "from K_Node nodefour where nodefour.sensorTypeId=1 and nodefour.mineId="
				+ mineId
				+ ")) as place,"
				+ "(select MAX(nodefive.currentData) from K_Node nodefive where nodefive.sensorTypeId=1 and nodefive.mineId="
				+ mineId
				+ ") as maxdata,"
				+ "(select Top 1 nodesix.nodePlace from K_Node nodesix where nodesix.currentData=(select  MAX(nodeseven.currentData)"
				+ " from K_Node nodeseven where nodeseven.sensorTypeId=4 and nodeseven.mineId="
				+ mineId
				+ ")) as COplace,"
				+ "(select MAX(nodeeight.currentData) from K_Node nodeeight where nodeeight.sensorTypeId=4 and nodeeight.mineId="
				+ mineId + ") as COmaxdata  FROM K_Node node";

		Query resultQuery = monitorRealDatasService.getDAO().createSQLQuery(sql);
		// resultQuery.setParameter("mineID", mineId);
		List<Object[]> results = Lists.newArrayList();
		results = resultQuery.list();
		RealData realData = new RealData();
		realData.setCount(String.valueOf(results.get(0)[0]));
		realData.setSimulateCount(String.valueOf(results.get(0)[1]));
		realData.setSwitchCount(String.valueOf(results.get(0)[2]));
		realData.setCh4Place(String.valueOf(results.get(0)[3]));
		realData.setCh4Data(String.valueOf(results.get(0)[4]));
		realData.setCoPlace(String.valueOf(results.get(0)[5]));
		realData.setCoData(String.valueOf(results.get(0)[6]));
		return new Response(realData);
	}

	/**
	 * 返回测点数据-3D
	 * 
	 * @param nodeId
	 * @return
	 */
	@RequestMapping(value = "/monitor/nodedata-3d/realdata", method = RequestMethod.GET)
	@ResponseBody
	public Response getNodeData3D(String nodeId) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		criterions.add(Restrictions.eq("id.nodeId", nodeId));
		String data = "";
		try {
			data = monitorNodeService.findUnique(criterions.toArray(new Criterion[0])).getCurrentData().toString();
		} catch (Exception e) {
			data = null;
		}

		return new Response(data);
	}
}
