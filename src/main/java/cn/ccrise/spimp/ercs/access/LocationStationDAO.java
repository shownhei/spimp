package cn.ccrise.spimp.ercs.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.ercs.entity.LocationStation;

/**
 * PositionStation DAO.
 * 
 */
@Repository
public class LocationStationDAO extends HibernateDAOImpl<LocationStation, Long> {

}
