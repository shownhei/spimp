package cn.ccrise.spimp.ercs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.ercs.access.LocationStationDAO;
import cn.ccrise.spimp.ercs.entity.LocationStation;

/**
 * LocationStation Service。
 * 
 */
@Service
public class LocationStationService extends HibernateDataServiceImpl<LocationStation, Long> {
	@Autowired
	private LocationStationDAO locationStationDAO;

	@Override
	public HibernateDAO<LocationStation, Long> getDAO() {
		return locationStationDAO;
	}
}