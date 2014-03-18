package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Index;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * MonitorFiveMinutesData
 * <p>
 * 监测监控测点密采历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_five_minute_data")
public class MonitorFiveMinutesData extends IDEntity {
	/**
	 * 系统数据id
	 */
	private String sysId;

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

	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

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

	@Column(name = "maxdata")
	public Double getMaxData() {
		return maxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_five_minute_data_time")
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

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Transient
	public String getMineName() {
		return mineName;
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

	@Column(name = "sysid")
	public String getSysId() {
		return sysId;
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

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
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

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setTotRecCount(Long totRecCount) {
		this.totRecCount = totRecCount;
	}
}
