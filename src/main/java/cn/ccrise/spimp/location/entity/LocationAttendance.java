package cn.ccrise.spimp.location.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Index;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * PositionAttendance
 * <p>
 * 人员井下考勤历史数据
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "m_attendance")
public class LocationAttendance extends IDEntity {

	/**
	 * 员工id
	 */
	private String staffId;

	/**
	 * 下井时间
	 */
	private Timestamp startTime;

	/**
	 * 升井时间
	 */
	private Timestamp endTime;

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_m_attendance_etime")
	@Column(name = "endtime")
	public Timestamp getEndTime() {
		return endTime;
	}

	@Column(name = "staffid")
	public String getStaffId() {
		return staffId;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Index(name = "index_m_attendance_stime")
	@Column(name = "starttime")
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
}
