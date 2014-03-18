package cn.ccrise.spimp.location.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * PositionSchedule
 * <p>
 * 领导带班计划
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_schedule")
public class LocationSchedule extends IDEntity {
	/**
	 * 带班日期
	 */
	private Date date;

	/**
	 * 班次
	 */
	private String classes;

	/**
	 * 员工ID
	 */
	private String staffid;

	public String getClasses() {
		return classes;
	}

	public Date getDate() {
		return date;
	}

	public String getStaffid() {
		return staffid;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStaffid(String staffid) {
		this.staffid = staffid;
	}
}
