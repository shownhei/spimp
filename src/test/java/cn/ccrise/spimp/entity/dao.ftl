/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package ${packageName}.access;

import org.springframework.stereotype.Repository;

import ${packageName}.entity.${entityName};
import cn.ccrise.ikjp.core.access.HibernateDAOImpl;

/**
 * ${entityName} DAO。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class ${entityName}DAO extends HibernateDAOImpl<${entityName}, Long> {
}