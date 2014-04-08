/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.location.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Embeddable
public class MineIdStaffId implements Serializable {
	private static final long serialVersionUID = -5043936734665549884L;
	private String mineId;
	private String staffId;

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
		MineIdStaffId other = (MineIdStaffId) obj;
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
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		result = prime * result + ((staffId == null) ? 0 : staffId.hashCode());
		return result;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
