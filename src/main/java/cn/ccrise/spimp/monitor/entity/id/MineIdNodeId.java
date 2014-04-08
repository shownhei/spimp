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
public class MineIdNodeId implements Serializable {
	private static final long serialVersionUID = 2695956163106998410L;
	private String mineId;
	private String nodeId;

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
		MineIdNodeId other = (MineIdNodeId) obj;
		if (mineId == null) {
			if (other.mineId != null) {
				return false;
			}
		} else if (!mineId.equals(other.mineId)) {
			return false;
		}
		if (nodeId == null) {
			if (other.nodeId != null) {
				return false;
			}
		} else if (!nodeId.equals(other.nodeId)) {
			return false;
		}
		return true;
	}

	@Column(name = "mineid")
	public String getMineId() {
		return mineId;
	}

	@Column(name = "nodeid")
	public String getNodeId() {
		return nodeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
