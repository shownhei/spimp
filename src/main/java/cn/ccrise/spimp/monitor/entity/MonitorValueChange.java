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
 * MonitorValueChange
 * <p>
 * 监测监控测点开关量历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_value_change")
public class MonitorValueChange extends IDEntity {
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
	 * 开始时间
	 */
	private Timestamp startTime;

	/**
	 * 结束时间
	 */
	private Timestamp endTime;

	/**
	 * 实时数据
	 */
	private Double realData;

	/**
	 * 数据生成数据
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

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreatetime() {
		return createtime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_value_change_etime")
	@Column(name = "endtime")
	public Timestamp getEndTime() {
		return endTime;
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

	@Column(name = "sensorname")
	public String getSensorName() {
		return sensorName;
	}

	@Column(name = "sensortypeid")
	public Integer getSensorTypeId() {
		return sensorTypeId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_k_value_change_stime")
	@Column(name = "starttime")
	public Timestamp getStartTime() {
		return startTime;
	}

	@Column(name = "sysid")
	public String getSysId() {
		return sysId;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}
}
