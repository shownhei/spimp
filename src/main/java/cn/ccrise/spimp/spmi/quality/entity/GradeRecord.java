/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.quality.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * GradeRecord。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Entity
@Table(name = "spmi_grade_records")
public class GradeRecord extends IDEntity {
	/**
	 * 行。
	 */
	@NotNull
	private Integer row;
	/**
	 * 列。
	 */
	@NotNull
	private Integer col;
	/**
	 * 内容。
	 */
	private String content;

	@Column(nullable = false)
	public Integer getCol() {
		return col;
	}

	@Lob
	public String getContent() {
		return content;
	}

	@Column(nullable = false)
	public Integer getRow() {
		return row;
	}

	public void setCol(Integer col) {
		this.col = col;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setRow(Integer row) {
		this.row = row;
	}
}