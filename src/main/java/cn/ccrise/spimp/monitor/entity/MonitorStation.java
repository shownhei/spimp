package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.monitor.entity.id.MineIdStationId;

/**
 * MonitorStation
 * <p>
 * 监测监控分站的基本和实时信息
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_station")
public class MonitorStation {
	private MineIdStationId id; // 复合主键
	/**
	 * 煤矿名称
	 */
	private String mineName;
	/**
	 * 站点位置
	 */
	private String stationPlace;

	/**
	 * 站点描述
	 */
	private String stationMemo;

	/**
	 * 分站数据：0故障/1正常
	 */
	private String stationData;

	/**
	 * 数据上传时间
	 */
	private Timestamp dataTime;

	/**
	 * 数据是都有效
	 */
	private Boolean isValid;

	/**
	 * 数据生成时间
	 */
	private Timestamp createTime;

	/**
	 * 数据采集软件更新标志，1为有效
	 */
	private Boolean bUpdate;

	@Column(name = "bupdate")
	public Boolean getbUpdate() {
		return bUpdate;
	}

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

	@EmbeddedId
	public MineIdStationId getId() {
		return id;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	@Column(name = "stationdata")
	public String getStationData() {
		return stationData;
	}

	@Column(name = "stationmemo")
	public String getStationMemo() {
		return stationMemo;
	}

	@Column(name = "stationplace")
	public String getStationPlace() {
		return stationPlace;
	}

	public void setbUpdate(Boolean bUpdate) {
		this.bUpdate = bUpdate;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setId(MineIdStationId id) {
		this.id = id;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public void setStationData(String stationData) {
		this.stationData = stationData;
	}

	public void setStationMemo(String stationMemo) {
		this.stationMemo = stationMemo;
	}

	public void setStationPlace(String stationPlace) {
		this.stationPlace = stationPlace;
	}
}
