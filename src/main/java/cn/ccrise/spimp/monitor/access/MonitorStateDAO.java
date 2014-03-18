package cn.ccrise.spimp.monitor.access;

import org.springframework.stereotype.Repository;

import cn.ccrise.ikjp.core.access.HibernateDAOImpl;
import cn.ccrise.spimp.monitor.entity.MonitorState;

/**
 * MonitorState DAO.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Repository
public class MonitorStateDAO extends HibernateDAOImpl<MonitorState, Long> {

}
