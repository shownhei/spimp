/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.monitor.entity.id;

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
public class MineIdNodeIdDataTime implements Serializable {
	private static final long serialVersionUID = -4485758245013038835L;
	private String mineId;
	private String nodeId;
	private Timestamp dataTime;

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
		MineIdNodeIdDataTime other = (MineIdNodeIdDataTime) obj;
		if (dataTime == null) {
			if (other.dataTime != null) {
				return false;
			}
		} else if (!dataTime.equals(other.dataTime)) {
			return false;
		}
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

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	@Column(name = "datatime")
	public Timestamp getDataTime() {
		return dataTime;
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
		result = prime * result + ((dataTime == null) ? 0 : dataTime.hashCode());
		result = prime * result + ((mineId == null) ? 0 : mineId.hashCode());
		result = prime * result + ((nodeId == null) ? 0 : nodeId.hashCode());
		return result;
	}

	public void setDataTime(Timestamp dataTime) {
		this.dataTime = dataTime;
	}

	public void setMineId(String mineId) {
		this.mineId = mineId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
}
