/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package ${packageName}.service;

<#if hasTextSearch>
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
</#if>
<#if hasTextSearch || dateQuery>
import org.hibernate.criterion.Restrictions;
</#if>
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

<#if dateQuery>
import java.sql.Date;
</#if>
<#if dateTimeQuery>
import java.sql.Timestamp;
</#if>
import java.util.ArrayList;
import java.util.List;
import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import ${packageName}.access.${entityName}DAO;
import ${packageName}.entity.${entityName};

/**
 * ${entityName} Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class ${entityName}Service extends HibernateDataServiceImpl<${entityName}, Long> {
	@Autowired
	private ${entityName}DAO ${entityName?uncap_first}DAO;

	@Override
	public HibernateDAO<${entityName}, Long> getDAO() {
		return ${entityName?uncap_first}DAO;
	}
	
	public Page<${entityName}> pageQuery(Page<${entityName}> page${queryParamsWithType}) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		
		<#if hasTextSearch>
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(${dbQuery}));
		}
		
		</#if>
		<#if dateQuery>
		if (startDate != null) {
			criterions.add(Restrictions.ge("${dateQueryField}", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("${dateQueryField}", endDate));
		}
		
		</#if>
		<#if dateTimeQuery>
		if (StringUtils.isNotBlank(startDate)) {
			criterions.add(Restrictions.ge("${dateQueryField}", Timestamp.valueOf(startDate + " 00:00:00")));
		}
		if (StringUtils.isNotBlank(endDate)) {
			criterions.add(Restrictions.le("${dateQueryField}", Timestamp.valueOf(endDate + " 23:59:59")));
		}
		
		</#if>
		<#if hasSelectSearch>
${selectQuery}		
		</#if>
		return getPage(page, criterions.toArray(new Criterion[0]));
	}
}