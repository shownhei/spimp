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
 * MonitorRealData
 * <p>
 * 监测监控测点密采历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_real_data_detail")
public class MonitorRealData extends IDEntity {
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
	 * 传感器名称(重要：非持久化字段)
	 */
	private String sensorName;

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

	/**
	 * 
	 */
	private Integer kdId;

	/**
	 * 上传时间
	 */
	private Timestamp dataTime;

	/**
	 * 数据生成时间
	 */
	private Timestamp createTime;

	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_realdata_time")
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@Column(name = "kdid")
	public Integer getKdId() {
		return kdId;
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

	@Column(name = "realdata")
	public Double getRealData() {
		return realData;
	}

	@Transient
	public String getSensorName() {
		return sensorName;
	}

	@Column(name = "sensortypeid")
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

	@Column(name = "sysid")
	public String getSysId() {
		return sysId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setKdId(Integer kdId) {
		this.kdId = kdId;
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

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
}
