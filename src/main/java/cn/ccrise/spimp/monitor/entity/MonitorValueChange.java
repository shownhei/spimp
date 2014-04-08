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
 * MonitorValueChange
 * <p>
 * 监测监控测点开关量历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_valchange")
public class MonitorValueChange extends MineIdAndName {
	private Long staChangeId;

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

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreatetime() {
		return createtime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "endtime")
	public Timestamp getEndTime() {
		return endTime;
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

	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Id
	@Column(name = "stachangeid")
	public Long getStaChangeId() {
		return staChangeId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "starttime")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
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

	public void setStaChangeId(Long staChangeId) {
		this.staChangeId = staChangeId;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
}
