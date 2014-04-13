/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * active表关联映射
 * <p>
 * 配件。
 * 
 */
@Entity
@Table(name = "electr_active_table_config")
public class ActiveTablesConfig extends IDEntity {
	private String tableName;//表名称
	private String tableCode;//表代码
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableCode() {
		return tableCode;
	}
	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
}