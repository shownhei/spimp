package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * 用于格式化存储历史图表所需数据.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 * @author shelltea
 */
public class HistoryCurveModel {
	private Timestamp dataTime;
	private Double maxData;
	private Double minData;
	private Double avgData;

	public Double getAvgData() {
		return avgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getDataTime() {
		return dataTime;
	}

	public Double getMaxData() {
		return maxData;
	}

	public Double getMinData() {
		return minData;
	}

	public void setAvgData(Double avgData) {
		this.avgData = avgData;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setMaxData(Double maxData) {
		this.maxData = maxData;
	}

	public void setMinData(Double minData) {
		this.minData = minData;
	}
}
