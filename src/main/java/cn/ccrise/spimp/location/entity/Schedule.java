package cn.ccrise.spimp.location.entity;

import java.sql.Date;

public class Schedule {
	private long id;
	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 员工姓名
	 */
	private String name;

	/**
	 * 所属部门
	 */
	private String department;

	/**
	 * 工种
	 */
	private String jobType;
	/**
	 * 班次
	 */
	private String classes;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	/**
	 * 带班日期
	 */
	private Date date;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
