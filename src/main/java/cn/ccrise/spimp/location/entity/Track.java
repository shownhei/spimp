package cn.ccrise.spimp.location.entity;

public class Track {
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
	 * 基站id
	 */
	private String stationId;
	private String stationName;
	/**
	 * 进入当前区域时间
	 */
	private String enterCurTime;

	/**
	 * 状态
	 */
	private String state;

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

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

	public String getTroopName() {
		return troopName;
	}

	public void setTroopName(String troopName) {
		this.troopName = troopName;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getEnterCurTime() {
		return enterCurTime;
	}

	public void setEnterCurTime(String enterCurTime) {
		this.enterCurTime = enterCurTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
