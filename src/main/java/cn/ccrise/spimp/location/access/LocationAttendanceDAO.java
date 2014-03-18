package cn.ccrise.spimp.location.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.location.entity.LocationAttendance;

/**
 * PositionAttendance DAO.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class LocationAttendanceDAO extends HibernateDAOImpl<LocationAttendance, Long> {

}
