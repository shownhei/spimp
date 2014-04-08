/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.location.entity.id;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Embeddable
public class MineIdStaffIdEnterCurTime implements Serializable {
	private static final long serialVersionUID = 8743926520637773594L;
	private String mineId;
	private String staffId;
	private Timestamp enterCurTime;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MineIdStaffIdEnterCurTime other = (MineIdStaffIdEnterCurTime) obj;
		if (enterCurTime == null) {
			if (other.enterCurTime != null) {
				return false;
			}
		} else if (!enterCurTime.equals(other.enterCurTime)) {
			return false;
		}
		if (mineId == null) {
			if (other.mineId != null) {
				return false;
			}
		} else if (!mineId.equals(other.mineId)) {
			return false;
		}
		if (staffId == null) {
			if (other.staffId != null) {
				return false;
			}
		} else if (!staffId.equals(other.staffId)) {
			return false;
		}
		return true;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "entercurtime")
	public Timestamp getEnterCurTime() {
		return enterCurTime;
	}

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Column(name = "staffid")
	public String getStaffId() {
		return staffId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((enterCurTime == null) ? 0 : enterCurTime.hashCode());
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		return result;
	}

	public void setEnterCurTime(Timestamp enterCurTime) {
		this.enterCurTime = enterCurTime;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
