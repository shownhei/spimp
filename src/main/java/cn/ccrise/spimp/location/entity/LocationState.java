package cn.ccrise.spimp.location.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * LocationStateType
 * <p>
 * 井下人员监控状态
 * 
 * @author Jackie Tsui
 */
@Entity
@Table(name = "m_state")
public class LocationState extends IDEntity {
	/**
	 * 状态编号
	 */
	private Integer stateId;
	/**
	 * 状态名称
	 */
	private String stateName;

	public Integer getStateId() {
		return stateId;
	}

	public String getStateName() {
		return stateName;
	}

	@Column(name = "stateid")
	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	@Column(name = "statename")
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
