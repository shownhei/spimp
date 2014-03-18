package cn.ccrise.spimp.monitor.web;

import java.util.List;

import cn.ccrise.spimp.monitor.entity.HistoryCurveModel;

public class HistoryCurveResponse {
	private String nodeID;

	private List<HistoryCurveModel> data;

	public List<HistoryCurveModel> getData() {
		return data;
	}

	public String getNodeID() {
		return nodeID;
	}

	public void setData(List<HistoryCurveModel> data) {
		this.data = data;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
}
