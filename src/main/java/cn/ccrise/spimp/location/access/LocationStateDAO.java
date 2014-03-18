package cn.ccrise.spimp.location.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.location.entity.LocationState;

@Repository
public class LocationStateDAO extends HibernateDAOImpl<LocationState, Long> {

}
