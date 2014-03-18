package cn.ccrise.spimp.location.entity;

public class Attendance {
	/**
	 * 员工id
	 */
	private String staffId;
	/**
	 * 员工姓名
	 */
	private String name;

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * 所属部门
	 */
	private String department;

	/**
	 * 工种
	 */
	private String jobType;

	/**
	 * 班组
	 */
	private String troopName;

	/**
	 * 下井时间
	 */
	private String startTime;

	/**
	 * 升井时间
	 */
	private String endTime;
	/**
	 * 持续时间
	 */
	private String timeRegion;

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

	public String getTroopName() {
		return troopName;
	}

	public void setTroopName(String troopName) {
		this.troopName = troopName;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTimeRegion() {
		return timeRegion;
	}

	public void setTimeRegion(String timeRegion) {
		this.timeRegion = timeRegion;
	}

}
