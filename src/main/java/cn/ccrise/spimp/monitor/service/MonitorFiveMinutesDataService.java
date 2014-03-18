package cn.ccrise.spimp.monitor.service;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorFiveMinutesDataDAO;
import cn.ccrise.spimp.monitor.entity.MonitorFiveMinutesData;

/**
 * MonitorFiveMinutesData Service.
 * <p>
 * 
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorFiveMinutesDataService extends HibernateDataServiceImpl<MonitorFiveMinutesData, Long> {

	@Autowired
	private MonitorFiveMinutesDataDAO monitorFiveMinutesDataDAO;

	/**
	 * 查询一个小时内监测值的平均值
	 * 
	 * @param type
	 * 
	 * @return
	 */
	public Double fiveMinuteValue(String id) {

		StringBuffer sql = new StringBuffer("SELECT SUM(AvgData) FROM k_five_minute_data ");
		sql.append("WHERE AvgData in (SELECT TOP 12 AvgData FROM k_five_minute_data ");
		sql.append("WHERE k_five_minute_data.NodeID=:id  ");
		sql.append("ORDER by CreateTime desc)");
		Query query = createSQLQuery(sql.toString());
		query.setString("id", id);
		Double results = (Double) query.uniqueResult();

		return results == null ? 0 : results;
	}

	@Override
	public HibernateDAO<MonitorFiveMinutesData, Long> getDAO() {
		return monitorFiveMinutesDataDAO;
	}
}
