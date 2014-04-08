package cn.ccrise.spimp.monitor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * MonitorState
 * <p>
 * 监测监控传感器状态
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "k_state")
public class MonitorState extends IDEntity {
	/**
	 * 状态编号
	 */
	private Integer stateId;
	/**
	 * 报警状态名称
	 */
	private String stateName;
	/**
	 * 状态颜色
	 */
	private String stateColor;
	/**
	 * 是否报警
	 */
	private Boolean isAlarm;
	/**
	 * 是否开启声音
	 */
	private Boolean isSound;
	/**
	 * 是否弹出
	 */
	private Boolean isPop;
	/**
	 * 是否闪烁
	 */
	private Boolean isFlash;

	@Column(name = "isalarm")
	public Boolean getIsAlarm() {
		return isAlarm;
	}

	@Column(name = "isflash")
	public Boolean getIsFlash() {
		return isFlash;
	}

	@Column(name = "ispop")
	public Boolean getIsPop() {
		return isPop;
	}

	@Column(name = "issound")
	public Boolean getIsSound() {
		return isSound;
	}

	@Column(name = "statecolor")
	public String getStateColor() {
		return stateColor;
	}

	@Column(name = "stateid")
	public Integer getStateId() {
		return stateId;
	}

	@Column(name = "statename")
	public String getStateName() {
		return stateName;
	}

	public void setIsAlarm(Boolean isAlarm) {
		this.isAlarm = isAlarm;
	}

	public void setIsFlash(Boolean isFlash) {
		this.isFlash = isFlash;
	}

	public void setIsPop(Boolean isPop) {
		this.isPop = isPop;
	}

	public void setIsSound(Boolean isSound) {
		this.isSound = isSound;
	}

	public void setStateColor(String stateColor) {
		this.stateColor = stateColor;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
}
