/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

/**
 * 应急法规。
 * 
 */
@Entity
@Table(name = "ercs_emergency_laws")
public class EmergencyLaw extends IDEntity {
	 /**
     * 文件号
     */
     private String fileNo;
   
     /**
     * 文件名
     */
     private String fileName;
   
     /**
     * 发布单位
     */
     private GroupEntity department;
   
     /**
     * 发布时间
     */
     private  Timestamp addTime;

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@ManyToOne
	public GroupEntity getDepartment() {
		return department;
	}

	public void setDepartment(GroupEntity department) {
		this.department = department;
	}

	@Column(updatable=false)
	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getAddTime() {
		return addTime;
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}
     
     
}