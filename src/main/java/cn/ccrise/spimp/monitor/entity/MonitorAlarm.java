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
 * MonitorAlarm
 * <p>
 * 监测监控测点报警历史明细数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_alarm")
public class MonitorAlarm extends IDEntity {
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
	 * 报警开始时间
	 */
	private Timestamp startTime;

	/**
	 * 报警结束时间
	 */
	private Timestamp endTime;

	/**
	 * 报警最大值
	 */
	private Double maxData;

	/**
	 * 最大值出现时间
	 */
	private Timestamp maxDataTime;

	/**
	 * 报警最小值
	 */
	private Double minData;

	/**
	 * 最小值出现时间
	 */
	private Timestamp minDataTime;

	/**
	 * 平均值
	 */
	private Double avgData;

	/**
	 * 报警总数
	 */
	private Long totRecCount;

	/**
	 * 状态id 瓦斯k_state
	 */
	private Integer stateId;

	/**
	 * 状态名称(重要：非持久化字段)
	 */
	private String stateName;

	/**
	 * 生成时间
	 */
	private Timestamp createtime;

	/**
	 * 当前数据 值
	 */
	private Double currentData;

	/**
	 * 报警上限值(重要：非持久化字段)
	 */
	private Double alarmUpperValue;

	/**
	 * 高断电值(重要：非持久化字段)
	 */
	private Double hCutValue;

	/**
	 * 累计报警次数(重要：非持久化字段)
	 */
	private Integer totalAlarmCount;

	/**
	 * 累计报警时长(重要：非持久化字段)
	 */
	private String timeDiff;

	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

	@Transient
	public Double getAlarmUpperValue() {
		return alarmUpperValue;
	}

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

	@Transient
	public Double getCurrentData() {
		return currentData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_alarm_etime")
	@Column(name = "endtime")
	public Timestamp getEndTime() {
		return endTime;
	}

	@Transient
	public Double gethCutValue() {
		return hCutValue;
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

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_alarm_stime")
	@Column(name = "starttime")
	public Timestamp getStartTime() {
		return startTime;
	}

	@Column(name = "stateid")
	public Integer getStateId() {
		return stateId;
	}

	@Transient
	public String getStateName() {
		return stateName;
	}

	@Column(name = "sysid")
	public String getSysId() {
		return sysId;
	}

	@Transient
	public String getTimeDiff() {
		return timeDiff;
	}

	@Transient
	public Integer getTotalAlarmCount() {
		return totalAlarmCount;
	}

	@Column(name = "totreccount")
	public Long getTotRecCount() {
		return totRecCount;
	}

	public void setAlarmUpperValue(Double alarmUpperValue) {
		this.alarmUpperValue = alarmUpperValue;
	}

	public void setAvgData(Double avgData) {
		this.avgData = avgData;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setCurrentData(Double currentData) {
		this.currentData = currentData;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void sethCutValue(Double hCutValue) {
		this.hCutValue = hCutValue;
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

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}

	public void setTotalAlarmCount(Integer totalAlarmCount) {
		this.totalAlarmCount = totalAlarmCount;
	}

	public void setTotRecCount(Long totRecCount) {
		this.totRecCount = totRecCount;
	}
}
