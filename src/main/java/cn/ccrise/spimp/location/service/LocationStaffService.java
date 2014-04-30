/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.service;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.location.access.LocationStaffDAO;
import cn.ccrise.spimp.location.entity.LocationStaff;

/**
 * LocationStaff Service。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class LocationStaffService extends HibernateDataServiceImpl<LocationStaff, Long> {
	@Autowired
	private LocationStaffDAO locationStaffDAO;

	@Override
	public HibernateDAO<LocationStaff, Long> getDAO() {
		return locationStaffDAO;
	}
	/**
	 * 获得所有的部门信息列表
	 */
	public List<?> getAllDepartment(){
		SQLQuery query=getDAO().getSession().createSQLQuery("SELECT DISTINCT department FROM M_Staff");
		return query.list();
	}
	/**
	 * 获得给定部门中的所有的人员信息的列表
	 * @param department
	 * @return
	 */
	public List<LocationStaff> getDepartmentStaff(String department){
		return find(Restrictions.eq("department", department));
	}
}