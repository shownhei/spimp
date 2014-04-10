package cn.ccrise.spimp.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorRealDatasDAO;
import cn.ccrise.spimp.monitor.entity.MonitorRealDatas;

/**
 * MonitorRealData Service.
 * 
 * @author shelltea
 */
@Service
public class MonitorRealDatasService extends HibernateDataServiceImpl<MonitorRealDatas, Long> {
	@Autowired
	private MonitorRealDatasDAO monitorRealDatasDAO;

	@Override
	public HibernateDAO<MonitorRealDatas, Long> getDAO() {
		return monitorRealDatasDAO;
	}
}
