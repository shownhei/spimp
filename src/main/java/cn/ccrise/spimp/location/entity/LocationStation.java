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
 * PositionStation
 * <p>
 * 人员定位分站基本和实时信息
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_station")
public class LocationStation extends IDEntity {

	/**
	 * 区域id
	 */
	private String areaId;

	/**
	 * 基站id
	 */
	private String stationId;

	/**
	 * 基站所在位置描述
	 */
	private String pos;

	/**
	 * 基站类型
	 */
	private Integer type;
	/**
	 * 分站当前人数
	 */
	private Long curPersonNum;

	/**
	 * 状态
	 */
	private Integer curState;

	/**
	 * 数据时间
	 */
	private Timestamp dataTime;

	/**
	 * 数据是否有效
	 */
	private Boolean isValid;

	/**
	 * 数据采集软件更新标志，1为有效
	 */
	private Boolean bUpdate;
	private String sysType;
	private String typeString;

	@Column(name = "areaid")
	public String getAreaId() {
		return areaId;
	}

	@Column(name = "bupdate")
	public Boolean getbUpdate() {
		return bUpdate;
	}

	@Transient
	public Long getCurPersonNum() {
		return curPersonNum;
	}

	@Column(name = "curstate")
	public Integer getCurState() {
		return curState;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "pos")
	public String getPos() {
		return pos;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
	}

	@Column(name = "systype")
	public String getSysType() {
		return sysType;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	@Transient
	public String getTypeString() {
		return typeString;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setbUpdate(Boolean bUpdate) {
		this.bUpdate = bUpdate;
	}

	public void setCurPersonNum(Long curPersonNum) {
		this.curPersonNum = curPersonNum;
	}

	public void setCurState(Integer curState) {
		this.curState = curState;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setSysType(String sysType) {
		this.sysType = sysType;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
}
