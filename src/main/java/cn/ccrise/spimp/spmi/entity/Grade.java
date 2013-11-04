/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import cn.ccrise.ikjp.core.entity.IDEntity;
import cn.ccrise.ikjp.core.util.JsonTimeDeserializer;
import cn.ccrise.ikjp.core.util.JsonTimeSerializer;

import com.google.common.collect.Lists;

/**
 * Grade。
 * <p>
 * 质量标准化评分。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_grades")
public class Grade extends IDEntity {
	/**
	 * 年份。
	 */
	@NotNull
	private Integer year;
	/**
	 * 月份。
	 */
	@NotNull
	private Integer month;
	/**
	 * 分类/专业。
	 */
	@NotBlank
	private String category;
	/**
	 * 分数，提交时前端计算。
	 */
	private Double score;
	/**
	 * 评分详细记录。
	 */
	private List<GradeRecord> gradeRecords = Lists.newArrayList();
	/**
	 * 评分时间。
	 */
	private Timestamp gradedTime = new Timestamp(System.currentTimeMillis());
	/**
	 * 最后更新时间。
	 */
	private Timestamp updateTime;

	@Column(nullable = false)
	public String getCategory() {
		return category;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getGradedTime() {
		return gradedTime;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, orphanRemoval = true)
	@Fetch(FetchMode.SUBSELECT)
	public List<GradeRecord> getGradeRecords() {
		return gradeRecords;
	}

	@Column(nullable = false)
	public Integer getMonth() {
		return month;
	}

	public Double getScore() {
		return score;
	}

	@JsonSerialize(using = JsonTimeSerializer.class)
	@JsonDeserialize(using = JsonTimeDeserializer.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	@Column(nullable = false)
	public Integer getYear() {
		return year;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setGradedTime(Timestamp gradedTime) {
		this.gradedTime = gradedTime;
	}

	public void setGradeRecords(List<GradeRecord> gradeRecords) {
		this.gradeRecords = gradeRecords;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}