package cn.ccrise.spimp.location.entity;

public class DownMineRecord {
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
	 * 状态
	 */
	private String state;

	public String getDepartment() {
		return department;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getJobType() {
		return jobType;
	}

	public String getName() {
		return name;
	}

	public String getStaffId() {
		return staffId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getState() {
		return state;
	}

	public String getTroopName() {
		return troopName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTroopName(String troopName) {
		this.troopName = troopName;
	}

}
