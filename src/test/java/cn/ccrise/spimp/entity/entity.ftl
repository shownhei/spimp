/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package ${packageName}.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.ccrise.ikjp.core.entity.IDEntity;

/**
 * ${entityName}。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Entity
@Table(name = "${databasePrefix}_${tableName}")
public class ${entityName} extends IDEntity {
}