/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.system.entity.PositionEntity;

/**
 * 监测监控测点的基本和实时信息.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 * @author shelltea
 */
@Entity
@Table(name = "k_node")
public class MonitorNode extends PositionEntity {
	/**
	 * 系统数据id
	 */
	private String sysId;

	/**
	 * 节点id(测点编号)
	 */
	private String nodeId;

	/**
	 * 节点位置(安装位置)
	 */
	private String nodePlace;

	/**
	 * 基站id
	 */
	private String stationId;

	/**
	 * 传感器id(测点类型)
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
	 * 上边界值
	 */
	private Double boundUpperValue;

	/**
	 * 下边界值
	 */
	private Double boundLowerValue;

	/**
	 * 报警上限值
	 */
	private Double alarmUpperValue;

	/**
	 * 报警下限值
	 */
	private Double alarmLowerValue;

	/**
	 * 高断电值
	 */
	private Double hCutValue;

	/**
	 * 低断电值
	 */
	private Double lCutValue;

	/**
	 * 高复电值
	 */
	private Double hResetValue;

	/**
	 * 低复电值
	 */
	private Double lResetValue;

	/**
	 * 关联测点
	 */
	private String corRelativeNodes;

	/**
	 * 测点描述
	 */
	private String nodeMemo;

	/**
	 * 状态id
	 */
	private Integer stateId;

	/**
	 * 状态名称(重要：非持久化字段)
	 */
	private String stateName;

	/**
	 * 馈电状态
	 */
	private Long kdId;

	/**
	 * 当前数据 值
	 */
	private Double currentData;

	/**
	 * 数据上传时间
	 */
	private Timestamp dataTime;

	/**
	 * 5分钟统计开始时间
	 */
	private Timestamp fivStartTime;

	/**
	 * 5分钟统计结束时间
	 */
	private Timestamp fivEndTime;

	/**
	 * 5分钟内最大值
	 */
	private Double fivMaxData;

	/**
	 * 5分钟内最小值
	 */
	private Double fivMinData;

	/**
	 * 5分钟内均值
	 */
	private Double fivAvgData;

	/**
	 * 5分钟内最大值出现时间
	 */
	private Timestamp fivMaxTime;

	/**
	 * 5分钟内最小值出现时间
	 */
	private Timestamp fivMinTime;

	/**
	 * 5分钟内记录数据次数
	 */
	private Long fivTotRecCount;

	/**
	 * 5分钟值是否已保存
	 */
	private Boolean fivIsSave;

	/**
	 * 报警开始时间
	 */
	private Timestamp alaStartTime;

	/**
	 * 报警结束时间
	 */
	private Timestamp alaEndTime;

	/**
	 * 报警期间最大值
	 */
	private Double alaMaxData;

	/**
	 * 报警期间最小值
	 */
	private Double alaMinData;

	/**
	 * 报警期间均值
	 */
	private Double alaAvgData;

	/**
	 * 报警最大值出现时间
	 */
	private Timestamp alaMaxTime;

	/**
	 * 报警最小值出现时间
	 */
	private Timestamp alaMinTime;

	/**
	 * 报警状态id
	 */
	private Long alaStateId;

	/**
	 * 报警记录数量统计
	 */
	private Long alaTotRecCount;

	/**
	 * 报警是否已保存
	 */
	private Boolean alaIsSave;

	/**
	 * 开关量开始时间
	 */
	private Timestamp valChaStartTime;

	/**
	 * 开关量结束时间
	 */
	private Timestamp valChaEndTime;

	/**
	 * 开关量实时数据
	 */
	private Double valChaRealData;

	/**
	 * 开关量状态
	 */
	private Long valChaStateId;

	/**
	 * 开关量变化是否保存
	 */
	private Boolean valChaIsSave;

	/**
	 * 生成时间
	 */
	private Timestamp createTime;

	/**
	 * 该信息是否有效
	 */
	private Boolean isValid;

	/**
	 * 数据采集软件更新标志，1为有效
	 */
	private Boolean bUpdate;

	/**
	 * 系统类型
	 */
	private String sysType;

	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

	@Column(name = "alaavgdata")
	public Double getAlaAvgData() {
		return alaAvgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alaendtime")
	public Timestamp getAlaEndTime() {
		return alaEndTime;
	}

	@Column(name = "alaissave")
	public Boolean getAlaIsSave() {
		return alaIsSave;
	}

	@Column(name = "alamaxdata")
	public Double getAlaMaxData() {
		return alaMaxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alamaxtime")
	public Timestamp getAlaMaxTime() {
		return alaMaxTime;
	}

	@Column(name = "alamindata")
	public Double getAlaMinData() {
		return alaMinData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alamintime")
	public Timestamp getAlaMinTime() {
		return alaMinTime;
	}

	@Column(name = "alarmlowervalue")
	public Double getAlarmLowerValue() {
		return alarmLowerValue;
	}

	@Column(name = "alarmuppervalue")
	public Double getAlarmUpperValue() {
		return alarmUpperValue;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alastarttime")
	public Timestamp getAlaStartTime() {
		return alaStartTime;
	}

	@Column(name = "alastateid")
	public Long getAlaStateId() {
		return alaStateId;
	}

	@Column(name = "alatotreccount")
	public Long getAlaTotRecCount() {
		return alaTotRecCount;
	}

	@Column(name = "boundlowervalue")
	public Double getBoundLowerValue() {
		return boundLowerValue;
	}

	@Column(name = "bounduppervalue")
	public Double getBoundUpperValue() {
		return boundUpperValue;
	}

	@Column(name = "bupdate")
	public Boolean getbUpdate() {
		return bUpdate;
	}

	@Column(name = "correlativenodes")
	public String getCorRelativeNodes() {
		return corRelativeNodes;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column(name = "currentdata")
	public Double getCurrentData() {
		return currentData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@Column(name = "fivavgdata")
	public Double getFivAvgData() {
		return fivAvgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivendtime")
	public Timestamp getFivEndTime() {
		return fivEndTime;
	}

	@Column(name = "fivissave")
	public Boolean getFivIsSave() {
		return fivIsSave;
	}

	@Column(name = "fivmaxdata")
	public Double getFivMaxData() {
		return fivMaxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivmaxtime")
	public Timestamp getFivMaxTime() {
		return fivMaxTime;
	}

	@Column(name = "fivmindata")
	public Double getFivMinData() {
		return fivMinData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivmintime")
	public Timestamp getFivMinTime() {
		return fivMinTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivstarttime")
	public Timestamp getFivStartTime() {
		return fivStartTime;
	}

	@Column(name = "fivtotreccount")
	public Long getFivTotRecCount() {
		return fivTotRecCount;
	}

	@Column(name = "hcutvalue")
	public Double gethCutValue() {
		return hCutValue;
	}

	@Column(name = "hresetvalue")
	public Double gethResetValue() {
		return hResetValue;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "kdid")
	public Long getKdId() {
		return kdId;
	}

	@Column(name = "lcutvalue")
	public Double getlCutValue() {
		return lCutValue;
	}

	@Column(name = "lresetvalue")
	public Double getlResetValue() {
		return lResetValue;
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

	@Column(name = "nodememo")
	public String getNodeMemo() {
		return nodeMemo;
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

	@Column(name = "stateid")
	public Integer getStateId() {
		return stateId;
	}

	@Transient
	public String getStateName() {
		return stateName;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
	}

	@Column(name = "sysid")
	public String getSysId() {
		return sysId;
	}

	@Column(name = "systype")
	public String getSysType() {
		return sysType;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "valchaendtime")
	public Timestamp getValChaEndTime() {
		return valChaEndTime;
	}

	@Column(name = "valchaissave")
	public Boolean getValChaIsSave() {
		return valChaIsSave;
	}

	@Column(name = "valcharealdata")
	public Double getValChaRealData() {
		return valChaRealData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "valchastarttime")
	public Timestamp getValChaStartTime() {
		return valChaStartTime;
	}

	@Column(name = "valchastateid")
	public Long getValChaStateId() {
		return valChaStateId;
	}

	public void setAlaAvgData(Double alaAvgData) {
		this.alaAvgData = alaAvgData;
	}

	public void setAlaEndTime(Timestamp alaEndTime) {
		this.alaEndTime = alaEndTime;
	}

	public void setAlaIsSave(Boolean alaIsSave) {
		this.alaIsSave = alaIsSave;
	}

	public void setAlaMaxData(Double alaMaxData) {
		this.alaMaxData = alaMaxData;
	}

	public void setAlaMaxTime(Timestamp alaMaxTime) {
		this.alaMaxTime = alaMaxTime;
	}

	public void setAlaMinData(Double alaMinData) {
		this.alaMinData = alaMinData;
	}

	public void setAlaMinTime(Timestamp alaMinTime) {
		this.alaMinTime = alaMinTime;
	}

	public void setAlarmLowerValue(Double alarmLowerValue) {
		this.alarmLowerValue = alarmLowerValue;
	}

	public void setAlarmUpperValue(Double alarmUpperValue) {
		this.alarmUpperValue = alarmUpperValue;
	}

	public void setAlaStartTime(Timestamp alaStartTime) {
		this.alaStartTime = alaStartTime;
	}

	public void setAlaStateId(Long alaStateId) {
		this.alaStateId = alaStateId;
	}

	public void setAlaTotRecCount(Long alaTotRecCount) {
		this.alaTotRecCount = alaTotRecCount;
	}

	public void setBoundLowerValue(Double boundLowerValue) {
		this.boundLowerValue = boundLowerValue;
	}

	public void setBoundUpperValue(Double boundUpperValue) {
		this.boundUpperValue = boundUpperValue;
	}

	public void setbUpdate(Boolean bUpdate) {
		this.bUpdate = bUpdate;
	}

	public void setCorRelativeNodes(String corRelativeNodes) {
		this.corRelativeNodes = corRelativeNodes;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCurrentData(Double currentData) {
		this.currentData = currentData;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setFivAvgData(Double fivAvgData) {
		this.fivAvgData = fivAvgData;
	}

	public void setFivEndTime(Timestamp fivEndTime) {
		this.fivEndTime = fivEndTime;
	}

	public void setFivIsSave(Boolean fivIsSave) {
		this.fivIsSave = fivIsSave;
	}

	public void setFivMaxData(Double fivMaxData) {
		this.fivMaxData = fivMaxData;
	}

	public void setFivMaxTime(Timestamp fivMaxTime) {
		this.fivMaxTime = fivMaxTime;
	}

	public void setFivMinData(Double fivMinData) {
		this.fivMinData = fivMinData;
	}

	public void setFivMinTime(Timestamp fivMinTime) {
		this.fivMinTime = fivMinTime;
	}

	public void setFivStartTime(Timestamp fivStartTime) {
		this.fivStartTime = fivStartTime;
	}

	public void setFivTotRecCount(Long fivTotRecCount) {
		this.fivTotRecCount = fivTotRecCount;
	}

	public void sethCutValue(Double hCutValue) {
		this.hCutValue = hCutValue;
	}

	public void sethResetValue(Double hResetValue) {
		this.hResetValue = hResetValue;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setKdId(Long kdId) {
		this.kdId = kdId;
	}

	public void setlCutValue(Double lCutValue) {
		this.lCutValue = lCutValue;
	}

	public void setlResetValue(Double lResetValue) {
		this.lResetValue = lResetValue;
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

	public void setNodeMemo(String nodeMemo) {
		this.nodeMemo = nodeMemo;
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

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public void setValChaEndTime(Timestamp valChaEndTime) {
		this.valChaEndTime = valChaEndTime;
	}

	public void setValChaIsSave(Boolean valChaIsSave) {
		this.valChaIsSave = valChaIsSave;
	}

	public void setValChaRealData(Double valChaRealData) {
		this.valChaRealData = valChaRealData;
	}

	public void setValChaStartTime(Timestamp valChaStartTime) {
		this.valChaStartTime = valChaStartTime;
	}

	public void setValChaStateId(Long valChaStateId) {
		this.valChaStateId = valChaStateId;
	}
}
