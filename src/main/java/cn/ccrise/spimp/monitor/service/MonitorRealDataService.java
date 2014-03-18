package cn.ccrise.spimp.monitor.service;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorRealDataDAO;
import cn.ccrise.spimp.monitor.entity.MonitorRealData;

/**
 * MonitorRealData Service.
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorRealDataService extends HibernateDataServiceImpl<MonitorRealData, Long> {

	@Autowired
	private MonitorRealDataDAO monitorRealDataDAO;

	@Override
	public HibernateDAO<MonitorRealData, Long> getDAO() {
		return monitorRealDataDAO;
	}

	/**
	 * 取出最新一条数据的监测值
	 * 
	 * @param type
	 * 
	 * @return
	 */
	public Double lastData(String id) {

		StringBuffer sql = new StringBuffer("SELECT mr.realData FROM MonitorRealData mr ");
		sql.append("WHERE mr.nodeId=:id  ");
		sql.append("ORDER by mr.createTime desc");
		Query query = createQuery(sql.toString());
		query.setString("id", id);
		Double results = (Double) query.uniqueResult();

		return results == null ? 0 : results;
	}
}
