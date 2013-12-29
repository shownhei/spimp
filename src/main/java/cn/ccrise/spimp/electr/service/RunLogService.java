/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.RunLogDAO;
import cn.ccrise.spimp.electr.entity.RegularMaintenanceRemind;
import cn.ccrise.spimp.electr.entity.RunLog;

/**
 * RunLog Service。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class RunLogService extends HibernateDataServiceImpl<RunLog, Long> {
	@Autowired
	private RunLogDAO runLogDAO;
	@Autowired
	private RegularMaintenanceRemindService regularMaintenanceRemindService;

	@Override
	public HibernateDAO<RunLog, Long> getDAO() {
		return runLogDAO;
	}

	public Page<RunLog> pageQuery(Page<RunLog> page, Long car, String search, Date startDate, Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.or(Restrictions.ilike("classType", search, MatchMode.ANYWHERE)));
		}

		if (startDate != null) {
			criterions.add(Restrictions.ge("addDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("addDate", endDate));
		}

		if (car != null) {
			criterions.add(Restrictions.eq("car.id", car));
		}

		return getPage(page, criterions.toArray(new Criterion[0]));
	}

	public boolean saveAnRunLog(RunLog log) {

		/**
		 * 如果有该车辆 该保养类型的记录 就累加 否则 就创建
		 */
		List<RegularMaintenanceRemind> levelOneList = regularMaintenanceRemindService.find(
				Restrictions.eq("car", log.getCar()), Restrictions.eq("maintenanceLevel", 1));
		if (levelOneList == null || levelOneList.size() == 0) {
			// 保存一条记录
			RegularMaintenanceRemind levelOne = new RegularMaintenanceRemind();
			levelOne.setCar(log.getCar());
			levelOne.setKilometres(new Long(log.getDistance()));
			levelOne.setMaintenanceLevel(1);
			levelOne.setMaintenanceStatus(RegularMaintenanceRemind.MAINTENANCE_STATUS_NO);
			regularMaintenanceRemindService.save(levelOne);
			RegularMaintenanceRemind secondOne = new RegularMaintenanceRemind();
			secondOne.setCar(log.getCar());
			secondOne.setKilometres(new Long(log.getDistance()));
			secondOne.setMaintenanceLevel(2);
			secondOne.setMaintenanceStatus(RegularMaintenanceRemind.MAINTENANCE_STATUS_NO);
			regularMaintenanceRemindService.save(secondOne);
			RegularMaintenanceRemind thirdOne = new RegularMaintenanceRemind();
			thirdOne.setCar(log.getCar());
			thirdOne.setKilometres(new Long(log.getDistance()));
			thirdOne.setMaintenanceLevel(3);
			thirdOne.setMaintenanceStatus(RegularMaintenanceRemind.MAINTENANCE_STATUS_NO);
			regularMaintenanceRemindService.save(thirdOne);
		} else {
			StringBuilder buffer = new StringBuilder();
			buffer.append(" update RegularMaintenanceRemind a set a.kilometres=a.kilometres+:kilometres,a.maintenanceStatus=:maintenanceStatus");
			buffer.append(" where a.car.id=:carId");
			Query query = regularMaintenanceRemindService.getDAO().getSession().createQuery(buffer.toString());
			query.setInteger("maintenanceStatus", RegularMaintenanceRemind.MAINTENANCE_STATUS_NO);
			query.setLong("kilometres", log.getDistance());
			query.setLong("carId", log.getCar().getId());
			query.executeUpdate();
		}
		return save(log);
	}
}