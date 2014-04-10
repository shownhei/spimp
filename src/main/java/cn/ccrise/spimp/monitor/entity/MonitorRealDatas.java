package cn.ccrise.spimp.monitor.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.ccrise.spimp.monitor.entity.id.MineIdNodeIdDataTime;

/**
 * MonitorRealDatas
 * <p>
 * 监测监控测点密采历史数据
 * 
 * @author shelltea
 */
@Entity
@Table(name = "k_realdatas")
public class MonitorRealDatas {
	private MineIdNodeIdDataTime id; // 复合主键

	/**
	 * 实时数据
	 */
	private Double realData;

	/**
	 * 状态id
	 */
	private Integer stateId;

	/**
	 * 状态名称(重要：非持久化字段)
	 */
	private String stateName;

	private Integer kdId;

	private String mineName; // 煤矿名称，非持久化字段
	private String nodePlace; // 安装位置，非持久化字段
	private Integer sensorTypeId; // 测点类型，非持久化字段
	private String sensorName; // 传感器名称，非持久化字段

	@EmbeddedId
	public MineIdNodeIdDataTime getId() {
		return id;
	}

	@Column(name = "kdid")
	public Integer getKdId() {
		return kdId;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	@Transient
	public String getNodePlace() {
		return nodePlace;
	}

	@Column(name = "realdata")
	public Double getRealData() {
		return realData;
	}

	@Transient
	public String getSensorName() {
		return sensorName;
	}

	@Transient
	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	@Column(name = "stateid")
	public Integer getStateId() {
		return stateId;
	}

	@Transient
	public String getStateName() {
		return stateName;
	}

	public void setId(MineIdNodeIdDataTime id) {
		this.id = id;
	}

	public void setKdId(Integer kdId) {
		this.kdId = kdId;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public void setNodePlace(String nodePlace) {
		this.nodePlace = nodePlace;
	}

	public void setRealData(Double realData) {
		this.realData = realData;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public void setSensorTypeId(Integer sensorTypeId) {
		this.sensorTypeId = sensorTypeId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
