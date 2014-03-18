package cn.ccrise.spimp.location.entity;

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
 * PositionTrack
 * <p>
 * 人员井下轨迹历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_track_detail")
public class LocationTrack extends IDEntity {

	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 所在区域id
	 */
	private String areaId;

	/**
	 * 基站id
	 */
	private String stationId;

	/**
	 * 进入当前区域时间
	 */
	private Timestamp enterCurTime;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 数据上传时间
	 */
	private Timestamp dataTime;
	private String stateString;

	@Column(name = "areaid")
	public String getAreaId() {
		return areaId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "entercurtime")
	public Timestamp getEnterCurTime() {
		return enterCurTime;
	}

	@Column(name = "staffid")
	public String getStaffId() {
		return staffId;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	@Transient
	public String getStateString() {
		return stateString;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setEnterCurTime(Timestamp enterCurTime) {
		this.enterCurTime = enterCurTime;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setStateString(String stateString) {
		this.stateString = stateString;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
}
