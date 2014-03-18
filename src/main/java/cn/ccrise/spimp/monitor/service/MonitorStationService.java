package cn.ccrise.spimp.monitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorStationDAO;
import cn.ccrise.spimp.monitor.entity.MonitorStation;

/**
 * MonitorStation Service.
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorStationService extends HibernateDataServiceImpl<MonitorStation, Long> {

	@Autowired
	private MonitorStationDAO monitorStationDAO;

	@Override
	public HibernateDAO<MonitorStation, Long> getDAO() {
		return monitorStationDAO;
	}
}
