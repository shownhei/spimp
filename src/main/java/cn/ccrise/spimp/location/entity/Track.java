package cn.ccrise.spimp.location.entity;

public class Track {
	private String mineId;
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
	 * 停留时间
	 */
	private String indataTime;

	/**
	 * 状态
	 */
	private String state;

	
	
	public String getMineId() {
		return mineId;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public String getIndataTime() {
		return indataTime;
	}

	public void setIndataTime(String indataTime) {
		this.indataTime = indataTime;
	}

	public String getDepartment() {
		return department;
	}

	public String getEnterCurTime() {
		return enterCurTime;
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

	public String getState() {
		return state;
	}

	public String getStationId() {
		return stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public String getTroopName() {
		return troopName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setEnterCurTime(String enterCurTime) {
		this.enterCurTime = enterCurTime;
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

	public void setState(String state) {
		this.state = state;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public void setTroopName(String troopName) {
		this.troopName = troopName;
	}

}
