package cn.ccrise.spimp.monitor.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.spimp.monitor.access.MonitorStationDAO;
import cn.ccrise.spimp.monitor.entity.MonitorStation;
import cn.ccrise.spimp.system.service.GroupService;

import com.google.common.collect.Lists;

/**
 * MonitorStation Service.
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Service
public class MonitorStationService extends HibernateDataServiceImpl<MonitorStation, Long> {
	@Autowired
	private MonitorStationDAO monitorStationDAO;
	@Autowired
	private GroupService groupService;

	@Override
	public HibernateDAO<MonitorStation, Long> getDAO() {
		return monitorStationDAO;
	}

	public String statisticByData(String mineId, Boolean state) {
		ArrayList<Criterion> criterions = Lists.newArrayList();

		if (mineId != null) {
			criterions.add(Restrictions.in("mineId", groupService.getChildrenMines(mineId)));
		}
		if (state != null) {
			if (state) {
				criterions.add(Restrictions.eq("stationData", "1"));
			} else {
				criterions.add(Restrictions.ne("stationData", "1"));
			}
		}

		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("stationData"));
		projectionList.add(Projections.rowCount());

		@SuppressWarnings("unchecked")
		List<Object[]> res = getDAO().createCriteria(criterions.toArray(new Criterion[0]))
				.setProjection(projectionList).list();

		long data1 = 0L;
		long dataOthers = 0L;
		for (Object[] o : res) {
			long count = Long.parseLong(o[1].toString());
			if (o[0].equals("1")) {
				data1 += count;
			} else {
				dataOthers += count;
			}
		}

		return "分站总数:" + (data1 + dataOthers) + " 其中：正常" + data1 + "、故障" + dataOthers;
	}
}
