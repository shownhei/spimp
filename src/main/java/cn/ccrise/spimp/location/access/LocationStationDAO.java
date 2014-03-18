package cn.ccrise.spimp.location.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.location.entity.LocationStation;

/**
 * PositionStation DAO.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class LocationStationDAO extends HibernateDAOImpl<LocationStation, Long> {

}
