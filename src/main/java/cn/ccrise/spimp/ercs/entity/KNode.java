/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * KNode。
 * 
 */
@Entity
@Table(name = "k_nodes")
public class KNode extends IDEntity {
	// [SysId] [varchar](50) NOT NULL,
	private String sysId;

	// [NodeID] [varchar](50) NOT NULL,
	private String nodeID;

	// [NodePlace] [varchar](50) NULL,
	private String nodePlace;

	// [StationID] [varchar](50) NULL,
	private String stationID;

	// [SensorTypeID] [int] NOT NULL,
	private String sensorTypeID;

	// [SensorName] [varchar](50) NULL,
	private String sensorName;

	// [SensorUnit] [varchar](50) NULL,
	private String sensorUnit;

	// [BoundUpperValue] [decimal](9, 2) NULL,
	private Double boundUpperValue;

	// [BoundLowerValue] [decimal](9, 2) NULL,
	private Double boundLowerValue;

	// [AlarmUpperValue] [decimal](9, 2) NULL,
	private Double alarmUpperValue;

	// [AlarmLowerValue] [decimal](9, 2) NULL,
	private Double alarmLowerValue;

	// [HCutValue] [decimal](9, 2) NULL,
	private Double hCutValue;

	// [LCutValue] [decimal](9, 2) NULL,
	private Double lCutValue;

	// [HResetValue] [decimal](9, 2) NULL,
	private Double hResetValue;

	// [LResetValue] [decimal](9, 2) NULL,
	private Double lResetValue;

	// [CorrelativeNodes] [varchar](250) NULL,
	private String correlativeNodes;

	// [NodeMemo] [varchar](250) NULL,
	private String nodeMemo;

	// [StateID] [int] NULL,
	private Integer stateID;

	// [KDID] [int] NULL,
	private Integer kDID;

	// [CurrentData] [decimal](9, 2) NULL,
	private Double currentData;

	// [DataTime] [datetime] NULL,
	private Timestamp dataTime;

	// [FivStartTime] [datetime] NULL,
	private Timestamp fivStartTime;

	// [FivEndTime] [datetime] NULL,
	private Timestamp fivEndTime;

	// [FivMaxData] [decimal](9, 2) NULL,
	private Double fivMaxData;

	// [FivMinData] [decimal](9, 2) NULL,
	private Double fivMinData;

	// [FivAvgData] [decimal](9, 2) NULL,
	private Double fivAvgData;

	// [FivMaxTime] [datetime] NULL,
	private Timestamp fivMaxTime;

	// [FivMinTime] [datetime] NULL,
	private Timestamp fivMinTime;

	// [FivTotRecCount] [int] NULL,
	private Integer fivTotRecCount;

	// [FivIsSave] [bit] NULL,
	private Boolean fivIsSave;

	// [AlaStartTime] [datetime] NULL,
	private Timestamp alaStartTime;

	// [AlaEndTime] [datetime] NULL,
	private Timestamp alaEndTime;

	// [AlaMaxData] [decimal](9, 2) NULL,
	private Double alaMaxData;

	// [AlaMinData] [decimal](9, 2) NULL,
	private Double alaMinData;

	// [AlaAvgData] [decimal](9, 2) NULL,
	private Double alaAvgData;

	// [AlaMaxTime] [datetime] NULL,
	private Timestamp alaMaxTime;

	// [AlaMinTime] [datetime] NULL,
	private Timestamp alaMinTime;

	// [AlaStateID] [int] NULL,
	private Integer alaStateID;

	// [AlaTotRecCount] [int] NULL,
	private Integer alaTotRecCount;

	// [AlaIsSave] [bit] NULL,
	private Boolean alaIsSave;

	// [ValChaStartTime] [datetime] NULL,
	private Timestamp valChaStartTime;

	// [ValChaEndTime] [datetime] NULL,
	private Timestamp valChaEndTime;

	// [ValChaRealData] [decimal](9, 2) NULL,
	private Double valChaRealData;

	// [ValChaStateID] [int] NULL,
	private Integer valChaStateID;

	// [ValChaIsSave] [bit] NULL,
	private Boolean valChaIsSave;

	// [CreateTime] [datetime] NULL,
	private Timestamp createTime;

	// [IsValid] [bit] NOT NULL,
	private Boolean isValid;

	// [BUpdate] [bit] NULL,
	private Boolean bUpdate;

	// [SysType] [varchar](30) NULL,
	private String sysType;

	// [x] [float] NULL,
	/**
	 * x坐标.
	 */
	private Double x;

	// [y] [float] NULL,
	/**
	 * y坐标.
	 */
	private Double y;

	@Column(name = "alaAvgData")
	public Double getAlaAvgData() {
		return alaAvgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alaEndTime")
	public Timestamp getAlaEndTime() {
		return alaEndTime;
	}

	@Column(name = "alaIsSave")
	public Boolean getAlaIsSave() {
		return alaIsSave;
	}

	@Column(name = "alaMaxData")
	public Double getAlaMaxData() {
		return alaMaxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alaMaxTime")
	public Timestamp getAlaMaxTime() {
		return alaMaxTime;
	}

	@Column(name = "alaMinData")
	public Double getAlaMinData() {
		return alaMinData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alaMinTime")
	public Timestamp getAlaMinTime() {
		return alaMinTime;
	}

	@Column(name = "alarmLowerValue")
	public Double getAlarmLowerValue() {
		return alarmLowerValue;
	}

	@Column(name = "alarmUpperValue")
	public Double getAlarmUpperValue() {
		return alarmUpperValue;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "alaStartTime")
	public Timestamp getAlaStartTime() {
		return alaStartTime;
	}

	@Column(name = "alaStateID")
	public Integer getAlaStateID() {
		return alaStateID;
	}

	@Column(name = "alaTotRecCount")
	public Integer getAlaTotRecCount() {
		return alaTotRecCount;
	}

	@Column(name = "boundLowerValue")
	public Double getBoundLowerValue() {
		return boundLowerValue;
	}

	@Column(name = "BoundUpperValue")
	public Double getBoundUpperValue() {
		return boundUpperValue;
	}

	@Column(name = "bUpdate")
	public Boolean getbUpdate() {
		return bUpdate;
	}

	@Column(name = "correlativeNodes")
	public String getCorrelativeNodes() {
		return correlativeNodes;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createTime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column(name = "currentData")
	public Double getCurrentData() {
		return currentData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "dataTime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@Column(name = "fivAvgData")
	public Double getFivAvgData() {
		return fivAvgData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivEndTime")
	public Timestamp getFivEndTime() {
		return fivEndTime;
	}

	@Column(name = "fivIsSave")
	public Boolean getFivIsSave() {
		return fivIsSave;
	}

	@Column(name = "fivMaxData")
	public Double getFivMaxData() {
		return fivMaxData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivMaxTime")
	public Timestamp getFivMaxTime() {
		return fivMaxTime;
	}

	@Column(name = "fivMinData")
	public Double getFivMinData() {
		return fivMinData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivMinTime")
	public Timestamp getFivMinTime() {
		return fivMinTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "fivStartTime")
	public Timestamp getFivStartTime() {
		return fivStartTime;
	}

	@Column(name = "fivTotRecCount")
	public Integer getFivTotRecCount() {
		return fivTotRecCount;
	}

	@Column(name = "hCutValue")
	public Double gethCutValue() {
		return hCutValue;
	}

	@Column(name = "hResetValue")
	public Double gethResetValue() {
		return hResetValue;
	}

	@Column(name = "isValid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "kDID")
	public Integer getkDID() {
		return kDID;
	}

	@Column(name = "lCutValue")
	public Double getlCutValue() {
		return lCutValue;
	}

	@Column(name = "LResetValue")
	public Double getlResetValue() {
		return lResetValue;
	}

	@Column(name = "nodeID")
	public String getNodeID() {
		return nodeID;
	}

	@Column(name = "nodeMemo")
	public String getNodeMemo() {
		return nodeMemo;
	}

	@Column(name = "nodePlace")
	public String getNodePlace() {
		return nodePlace;
	}

	@Column(name = "sensorName")
	public String getSensorName() {
		return sensorName;
	}

	@Column(name = "sensorTypeID")
	public String getSensorTypeID() {
		return sensorTypeID;
	}

	@Column(name = "sensorUnit")
	public String getSensorUnit() {
		return sensorUnit;
	}

	@Column(name = "stateID")
	public Integer getStateID() {
		return stateID;
	}

	@Column(name = "stationID")
	public String getStationID() {
		return stationID;
	}

	@Column(name = "SysId")
	public String getSysId() {
		return sysId;
	}

	@Column(name = "sysType")
	public String getSysType() {
		return sysType;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "valChaEndTime")
	public Timestamp getValChaEndTime() {
		return valChaEndTime;
	}

	@Column(name = "valChaIsSave")
	public Boolean getValChaIsSave() {
		return valChaIsSave;
	}

	@Column(name = "valChaRealData")
	public Double getValChaRealData() {
		return valChaRealData;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "valChaStartTime")
	public Timestamp getValChaStartTime() {
		return valChaStartTime;
	}

	@Column(name = "valChaStateID")
	public Integer getValChaStateID() {
		return valChaStateID;
	}

	public Double getX() {
		return x;
	}

	public Double getY() {
		return y;
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

	public void setAlaStateID(Integer alaStateID) {
		this.alaStateID = alaStateID;
	}

	public void setAlaTotRecCount(Integer alaTotRecCount) {
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

	public void setCorrelativeNodes(String correlativeNodes) {
		this.correlativeNodes = correlativeNodes;
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

	public void setFivTotRecCount(Integer fivTotRecCount) {
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

	public void setkDID(Integer kDID) {
		this.kDID = kDID;
	}

	public void setlCutValue(Double lCutValue) {
		this.lCutValue = lCutValue;
	}

	public void setlResetValue(Double lResetValue) {
		this.lResetValue = lResetValue;
	}

	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
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

	public void setSensorTypeID(String sensorTypeID) {
		this.sensorTypeID = sensorTypeID;
	}

	public void setSensorUnit(String sensorUnit) {
		this.sensorUnit = sensorUnit;
	}

	public void setStateID(Integer stateID) {
		this.stateID = stateID;
	}

	public void setStationID(String stationID) {
		this.stationID = stationID;
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

	public void setValChaStateID(Integer valChaStateID) {
		this.valChaStateID = valChaStateID;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public void setY(Double y) {
		this.y = y;
	}
}