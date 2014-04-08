/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.monitor.entity.id;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Embeddable
public class MineIdStationId implements Serializable {
	private static final long serialVersionUID = 5718920848030063838L;
	private String mineId;
	private String stationId;

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
		MineIdStationId other = (MineIdStationId) obj;
		if (mineId == null) {
			if (other.mineId != null) {
				return false;
			}
		} else if (!mineId.equals(other.mineId)) {
			return false;
		}
		if (stationId == null) {
			if (other.stationId != null) {
				return false;
			}
		} else if (!stationId.equals(other.stationId)) {
			return false;
		}
		return true;
	}

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Column(name = "stationid")
	public String getStationId() {
		return stationId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		result = prime * result + ((stationId == null) ? 0 : stationId.hashCode());
		return result;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

}
