package cn.ccrise.spimp.monitor.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.monitor.entity.HistoryCurveModel;
import cn.ccrise.spimp.monitor.entity.MonitorNode;
import cn.ccrise.spimp.monitor.service.MonitorFiveMinutesDataService;
import cn.ccrise.spimp.monitor.service.MonitorNodeService;

/**
 * HistoryCurve Controller。 历史曲线控制类
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class HistoryCurveController {

	@Autowired
	private MonitorFiveMinutesDataService monitorFiveMinutesDataService;

	@Autowired
	private MonitorNodeService monitorNodeService;

	@RequestMapping(value = "/monitor/history-curve-alarm-uv", method = RequestMethod.GET)
	@ResponseBody
	public Response historyCurveAlarmUv(String nodeId) {
		if (StringUtils.isNotBlank(nodeId)) {
			MonitorNode node = monitorNodeService.findUniqueBy("nodeId", nodeId);
			if (node != null) {
				return new Response(node);
			}
		}

		return new Response(null);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/monitor/history-curve", method = RequestMethod.GET)
	@ResponseBody
	public Response historyCurveDatas(String[] nodeIds, String date) {
		if (nodeIds != null && nodeIds.length > 0) {
			StringBuffer sql = new StringBuffer("select fd.nodeId, fd.dataTime, fd.maxData, fd.minData, fd.avgData ")
					.append("from MonitorFiveMinutesData fd ").append("where fd.nodeId in (:nodeIds) ")
					.append("and fd.dataTime>=:startTime and fd.dataTime<=:endTime ")
					.append("order by fd.nodeId asc,fd.dataTime asc");

			List<Object[]> results = monitorFiveMinutesDataService.getDAO().createQuery(sql.toString())
					.setParameterList("nodeIds", nodeIds)
					.setTimestamp("startTime", Timestamp.valueOf(date + " 00:00:00"))
					.setTimestamp("endTime", Timestamp.valueOf(date + " 23:59:59")).list();

			List<HistoryCurveResponse> values = new ArrayList<HistoryCurveResponse>(nodeIds.length);
			List<HistoryCurveModel> models = new ArrayList<HistoryCurveModel>();

			String currentNode = "";
			for (int i = 0; i < results.size(); i++) {
				Object[] result = results.get(i);
				String tempNode = String.valueOf(result[0]);

				// 第一个节点时，初始化currentNode
				if (i == 0) {
					currentNode = tempNode;
				}

				if (i != 0 && !currentNode.equals(tempNode)) {
					HistoryCurveResponse hcr = new HistoryCurveResponse();
					hcr.setNodeID(currentNode);
					hcr.setData(models);
					values.add(hcr);

					models = new ArrayList<HistoryCurveModel>();
					currentNode = tempNode;
				}

				HistoryCurveModel model = new HistoryCurveModel();
				model.setDataTime(Timestamp.valueOf(String.valueOf(result[1])));
				model.setMaxData(NumberUtils.toDouble(String.valueOf(result[2]), 0d));
				model.setMinData(NumberUtils.toDouble(String.valueOf(result[3]), 0d));
				model.setAvgData(NumberUtils.toDouble(String.valueOf(result[4]), 0d));

				models.add(model);

				// 最后一个节点时，自动加入
				if (i == results.size() - 1) {
					HistoryCurveResponse hcr = new HistoryCurveResponse();
					hcr.setNodeID(tempNode);
					hcr.setData(models);

					values.add(hcr);
				}
			}

			return new Response(values);
		}

		return new Response(null);
	}

}
