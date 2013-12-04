/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

import com.google.common.collect.Lists;

/**
 * Staff。
 * <p>
 * 人员信息。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spimp_staffs")
public class Staff extends IDEntity {
	/*
	 * 基本信息
	 */
	@NotBlank
	private String name; // 姓名
	private String gender; // 性别
	@NotBlank
	private String workType; // 工种
	private String qualification; // 资格证号
	@NotNull
	private GroupEntity groupEntity; // 人员所属于的机构
	private List<Alteration> alterations = Lists.newArrayList(); // 变更记录

	/*
	 * 其他信息
	 */
	private Timestamp createTime = new Timestamp(System.currentTimeMillis()); // 创建时间
	private Timestamp updateTime = new Timestamp(System.currentTimeMillis()); // 最后更新时间

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	public List<Alteration> getAlterations() {
		return alterations;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getGender() {
		return gender;
	}

	@ManyToOne(optional = false)
	public GroupEntity getGroupEntity() {
		return groupEntity;
	}

	public String getName() {
		return name;
	}

	public String getQualification() {
		return qualification;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public String getWorkType() {
		return workType;
	}

	public void setAlterations(List<Alteration> alterations) {
		this.alterations = alterations;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setGroupEntity(GroupEntity groupEntity) {
		this.groupEntity = groupEntity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}
}