package cn.ccrise.spimp.electr.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.access.HibernateDAO;
import cn.ccrise.ikjp.core.service.HibernateDataServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.spimp.electr.access.RegularMaintenanceRemindDAO;
import cn.ccrise.spimp.electr.entity.RegularMaintenanceRemind;

/**
 * 定期保养维修提醒
 * 
 */
@Service
public class RegularMaintenanceRemindService extends HibernateDataServiceImpl<RegularMaintenanceRemind, Long> {
	@Autowired
	private RegularMaintenanceRemindDAO regularMaintenanceRemindDAO;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private MaintenanceDetailService maintenanceDetailService;

	@Override
	public HibernateDAO<RegularMaintenanceRemind, Long> getDAO() {
		return regularMaintenanceRemindDAO;
	}

	public void closeRemind(Long remindId) {
		/**
		 * 关闭一个提醒 重置本记录的行程公里数为0 保养状态为已保养1
		 */
		RegularMaintenanceRemind remind = findUniqueBy("id", remindId);
		remind.setMaintenanceStatus(RegularMaintenanceRemind.MAINTENANCE_STATUS_YES);
		remind.setKilometres(0l);
		this.save(remind);
	}

	public Page<RegularMaintenanceRemind> pageQuery(Page<RegularMaintenanceRemind> page, Long car) {
		StringBuilder buffer = new StringBuilder();
		buffer.append("select rmd.id from RegularMaintenanceRemind rmd,RegularMaintenanceConfig cfg ");
		buffer.append("where rmd.maintenanceLevel=cfg.maintenanceLevel ");
		buffer.append(" and rmd.kilometres>=cfg.kilometres ");
		buffer.append(" and rmd.maintenanceStatus=0");
		List<?> remindIdList = getDAO().getSession().createQuery(buffer.toString()).list();
		Iterator<?> remindIdIt = remindIdList.iterator();
		ArrayList<Long> remindIdArray = new ArrayList<Long>(remindIdList.size());
		while (remindIdIt.hasNext()) {
			remindIdArray.add((Long) remindIdIt.next());
		}

		List<Criterion> criterions = new ArrayList<Criterion>();

		if (car != null) {
			criterions.add(Restrictions.eq("car.id", car));
		}
		if (remindIdArray.size() > 0) {
			criterions.add(Restrictions.in("id", remindIdArray.toArray(new Long[0])));
			return getPage(page, criterions.toArray(new Criterion[0]));
		}
		Page<RegularMaintenanceRemind> pageReturn = new Page<RegularMaintenanceRemind>();
		pageReturn.setPageSize(page.getPageSize());
		return pageReturn;
	}
}