package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.system.entity.MineIdAndName;

/**
 * MonitorRealData
 * <p>
 * 监测监控测点密采历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
// @Entity
// @Table(name = "k_realdata")
public class MonitorRealData extends MineIdAndName {
	private Long realId;

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

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@Column(name = "kdid")
	public Integer getKdId() {
		return kdId;
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

	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Id
	@Column(name = "realid")
	public Long getRealId() {
		return realId;
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

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setKdId(Integer kdId) {
		this.kdId = kdId;
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

	public void setRealId(Long realId) {
		this.realId = realId;
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
