package cn.ccrise.spimp.monitor.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.monitor.entity.MonitorValueChange;

/**
 * MonitorValueChange DAO.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MonitorValueChangeDAO extends HibernateDAOImpl<MonitorValueChange, Long> {

}
