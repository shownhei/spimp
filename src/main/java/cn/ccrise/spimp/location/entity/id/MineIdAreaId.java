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
public class MineIdAreaId implements Serializable {
	private static final long serialVersionUID = 9168532061038498263L;
	private String mineId;
	private String areaId;

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
		MineIdAreaId other = (MineIdAreaId) obj;
		if (areaId == null) {
			if (other.areaId != null) {
				return false;
			}
		} else if (!areaId.equals(other.areaId)) {
			return false;
		}
		if (mineId == null) {
			if (other.mineId != null) {
				return false;
			}
		} else if (!mineId.equals(other.mineId)) {
			return false;
		}
		return true;
	}

	@Column(name = "areaid")
	public String getAreaId() {
		return areaId;
	}

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((areaId == null) ? 0 : areaId.hashCode());
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		return result;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}
}