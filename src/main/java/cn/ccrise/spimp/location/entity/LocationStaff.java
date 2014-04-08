package cn.ccrise.spimp.location.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;
import cn.ccrise.spimp.location.entity.id.MineIdStaffId;

/**
 * PositionStaff
 * <p>
 * 下井人员基本和实时信息
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_staff")
public class LocationStaff {
	private MineIdStaffId id; // 复合主键
	/**
	 * 煤矿名称
	 */
	private String mineName;

	/**
	 * 员工姓名
	 */
	private String name;

	/**
	 * 员工所持考勤卡号
	 */
	private String cardId;

	/**
	 * 性别
	 */
	private String sex;

	/**
	 * 所属部门
	 */
	private String department;

	/**
	 * 出生日期
	 */
	private String birthday;

	/**
	 * 工种
	 */
	private Integer jobType;

	/**
	 * 岗位名称
	 */
	private String jobName;

	/**
	 * 班组
	 */
	private String troopName;

	/**
	 * 当前所在区域id
	 */
	private String curAreaId;

	/**
	 * 当前所在基站id
	 */
	private String curStationId;

	/**
	 * 状态
	 */
	private Integer state;

	/**
	 * 通过所在基站的时间
	 */
	private Timestamp indataTime;

	/**
	 * 数据是否有效
	 */
	private Boolean isValid;

	/**
	 * 数据采集软件更新标志，1为有效
	 */
	private Boolean bUpdate;

	/**
	 * 数据生成时间
	 */
	private Timestamp createTime;

	/**
	 * 入井时间
	 */
	private Timestamp inMineTime;

	private String stateString;

	@Column(name = "birthday")
	public String getBirthday() {
		return birthday;
	}

	@Column(name = "bupdate")
	public Boolean getbUpdate() {
		return bUpdate;
	}

	@Column(name = "cardid")
	public String getCardId() {
		return cardId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "createtime")
	public Timestamp getCreateTime() {
		return createTime;
	}

	@Column(name = "curareaid")
	public String getCurAreaId() {
		return curAreaId;
	}

	@Column(name = "curstationid")
	public String getCurStationId() {
		return curStationId;
	}

	@Column(name = "department")
	public String getDepartment() {
		return department;
	}

	@EmbeddedId
	public MineIdStaffId getId() {
		return id;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "indatatime")
	public Timestamp getIndataTime() {
		return indataTime;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "inminetime")
	public Timestamp getInMineTime() {
		return inMineTime;
	}

	@Column(name = "isvalid")
	public Boolean getIsValid() {
		return isValid;
	}

	@Column(name = "jobname")
	public String getJobName() {
		return jobName;
	}

	@Column(name = "jobtype")
	public Integer getJobType() {
		return jobType;
	}

	@Transient
	public String getMineName() {
		return mineName;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	@Column(name = "state")
	public Integer getState() {
		return state;
	}

	@Transient
	public String getStateString() {
		return stateString;
	}

	@Column(name = "troopname")
	public String getTroopName() {
		return troopName;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setbUpdate(Boolean bUpdate) {
		this.bUpdate = bUpdate;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setCurAreaId(String curAreaId) {
		this.curAreaId = curAreaId;
	}

	public void setCurStationId(String curStationId) {
		this.curStationId = curStationId;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setId(MineIdStaffId id) {
		this.id = id;
	}

	public void setIndataTime(Timestamp indataTime) {
		this.indataTime = indataTime;
	}

	public void setInMineTime(Timestamp inMineTime) {
		this.inMineTime = inMineTime;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setJobType(Integer jobType) {
		this.jobType = jobType;
	}

	public void setMineName(String mineName) {
		this.mineName = mineName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setStateString(String stateString) {
		this.stateString = stateString;
	}

	public void setTroopName(String troopName) {
		this.troopName = troopName;
	}
}
