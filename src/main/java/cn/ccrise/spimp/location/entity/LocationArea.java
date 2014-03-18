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
 * PositionArea
 * <p>
 * 人员定位区域的基本和实时信息
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_area")
public class LocationArea extends IDEntity {
	/**
	 * 区域id
	 */
	private String areaId;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 所在位置描述
	 */
	private String pos;

	/**
	 * 区域类型
	 */
	private Integer type;

	/**
	 * 核定人数
	 */
	private Integer fixedPersons;

	/**
	 * 区域内当前人数
	 */
	private Integer curPersonNum;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 数据生成时间
	 */
	private Timestamp createTime;

	/**
	 * 数据是否有效
	 */
	private Boolean isValid;

	/**
	 * 数据采集软件更新标志，1为有效
	 */
	private Boolean bUpdate;
	private String typeString;

	@Column(name = "areaid")
	public String getAreaId() {
		return areaId;
	}

	@Column(name = "areaname")
	public String getAreaName() {
		return areaName;
	}

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

	@Column(name = "curpersonnum")
	public Integer getCurPersonNum() {
		return curPersonNum;
	}

	@Column(name = "fixedpersons")
	public Integer getFixedPersons() {
		return fixedPersons;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "pos")
	public String getPos() {
		return pos;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
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

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setbUpdate(Boolean bUpdate) {
		this.bUpdate = bUpdate;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCurPersonNum(Integer curPersonNum) {
		this.curPersonNum = curPersonNum;
	}

	public void setFixedPersons(Integer fixedPersons) {
		this.fixedPersons = fixedPersons;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}
}
