package cn.ccrise.spimp.monitor.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * MonitorStation
 * <p>
 * 监测监控分站的基本和实时信息
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_station")
public class MonitorStation extends IDEntity {
	/**
	 * 站点id
	 */
	private String stationId;

	/**
	 * 站点位置
	 */
	private String stationPlace;

	/**
	 * 站点描述
	 */
	private String stationMemo;

	/**
	 * 分站数据
	 */
	private String stationData;

	/**
	 * 数据上传时间
	 */
	private String dataTime;

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

	/**
	 * 煤矿编号
	 */
	private String mineId;

	/**
	 * 煤矿名称
	 */
	private String mineName;

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
	public String getDataTime() {
		return dataTime;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	@Column(name = "stationdata")
	public String getStationData() {
		return stationData;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
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

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public void setStationData(String stationData) {
		this.stationData = stationData;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setStationMemo(String stationMemo) {
		this.stationMemo = stationMemo;
	}

	public void setStationPlace(String stationPlace) {
		this.stationPlace = stationPlace;
	}
}
