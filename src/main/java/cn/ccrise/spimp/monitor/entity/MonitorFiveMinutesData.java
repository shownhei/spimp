package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.system.entity.MineIdAndName;

/**
 * MonitorFiveMinutesData
 * <p>
 * 监测监控测点密采历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_fiveminutedata")
public class MonitorFiveMinutesData extends MineIdAndName {
	private Long fiveMinuteDataId;
	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 节点位置
	 */
	private String nodePlace;

	/**
	 * 传感器id
	 */
	private Integer sensorTypeId;

	/**
	 * 传感器名称
	 */
	private String sensorName;

	/**
	 * 传感器测量单位
	 */
	private String sensorUnit;

	/**
	 * 上传时间
	 */
	private Timestamp dataTime;

	/**
	 * 报警最大值
	 */
	private Double maxData;

	/**
	 * 报警最小值
	 */
	private Double minData;

	/**
	 * 平均值
	 */
	private Double avgData;

	/**
	 * 最大值出现时间
	 */
	private Timestamp maxDataTime;

	/**
	 * 最小值出现时间
	 */
	private Timestamp minDataTime;

	/**
	 * 报警总数
	 */
	private Long totRecCount;

	/**
	 * 生成时间
	 */
	private Timestamp createtime;

	@Column(name = "avgdata")
	public Double getAvgData() {
		return avgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreatetime() {
		return createtime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Id
	@Column(name = "fiveminutedataid")
	public Long getFiveMinuteDataId() {
		return fiveMinuteDataId;
	}

	@Column(name = "maxdata")
	public Double getMaxData() {
		return maxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "maxdatatime")
	public Timestamp getMaxDataTime() {
		return maxDataTime;
	}

	@Column(name = "mindata")
	public Double getMinData() {
		return minData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "mindatatime")
	public Timestamp getMinDataTime() {
		return minDataTime;
	}

	@Column(name = "nodeid")
	public String getNodeId() {
		return nodeId;
	}

	@Column(name = "nodeplace")
	public String getNodePlace() {
		return nodePlace;
	}

	@Column(name = "sensorname")
	public String getSensorName() {
		return sensorName;
	}

	@Column(name = "sensortypeid")
	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	@Column(name = "sensorunit")
	public String getSensorUnit() {
		return sensorUnit;
	}

	@Column(name = "totreccount")
	public Long getTotRecCount() {
		return totRecCount;
	}

	public void setAvgData(Double avgData) {
		this.avgData = avgData;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setFiveMinuteDataId(Long fiveMinuteDataId) {
		this.fiveMinuteDataId = fiveMinuteDataId;
	}

	public void setMaxData(Double maxData) {
		this.maxData = maxData;
	}

	public void setMaxDataTime(Timestamp maxDataTime) {
		this.maxDataTime = maxDataTime;
	}

	public void setMinData(Double minData) {
		this.minData = minData;
	}

	public void setMinDataTime(Timestamp minDataTime) {
		this.minDataTime = minDataTime;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public void setNodePlace(String nodePlace) {
		this.nodePlace = nodePlace;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
	}

	public void setTotRecCount(Long totRecCount) {
		this.totRecCount = totRecCount;
	}
}
