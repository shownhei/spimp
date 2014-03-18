package cn.ccrise.spimp.location.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.location.entity.LocationTrack;

/**
 * PositionTrack DAO.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class LocationTrackDAO extends HibernateDAOImpl<LocationTrack, Long> {

}
