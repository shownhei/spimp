package cn.ccrise.spimp.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorAlarmDAO;
import cn.ccrise.spimp.monitor.entity.MonitorAlarm;

/**
 * MonitorAlarm Service
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorAlarmService extends HibernateDataServiceImpl<MonitorAlarm, Long> {

	@Autowired
	private MonitorAlarmDAO monitorAlarmDAO;

	@Override
	public HibernateDAO<MonitorAlarm, Long> getDAO() {
		return monitorAlarmDAO;
	}
}
