package cn.ccrise.spimp.electr.service;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.util.DateUtil;

/**
 * 油耗统计服务：年度油耗统计、月度油耗统计
 * 
 * 
 */
@Service
public class OilStaticsService {
	@Autowired
	private RunLogService runLogService;

	/**
	 * 计算平均油耗（升） 同属一种类型的车的百公里油耗sum除以同种车型的条数
	 * 
	 * @param result
	 * @param df
	 */
	public void oilAVG(Collection<AnnualOil> result, DecimalFormat df, int days) {
		df.applyPattern("0.00");
		Iterator<AnnualOil> it = result.iterator();
		AnnualOil temp = null;
		int size = result.size();
		double sum = 0;
		Long trainNumber = 0l;
		double dayTrainNumber = 0l;
		while (it.hasNext()) {
			temp = it.next();
			sum += temp.getOilDistance();
			trainNumber += temp.getTrainNumber();
		}
		double avg = sum / size;
		dayTrainNumber = trainNumber / new Long(days);
		it = result.iterator();
		DecimalFormat df3 = new DecimalFormat(".###");
		df3.applyPattern("0.00");
		while (it.hasNext()) {
			temp = it.next();
			temp.setAvgOilDistance(df.format(avg));
			temp.setDayTrainNumberDisplay(df3.format(dayTrainNumber));
		}
	}

	/**
	 * 月度统计
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryByMonth(Integer year, Integer month) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = new Date(c.getTime().getTime());
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, DateUtil.getCurrentMonthLastDay());
		Date endDate = new Date(c.getTime().getTime());
		StringBuffer buff = new StringBuffer();
		buff.append("select l.car.carNo,sum(l.trainNumber) as c, sum(l.distance) as a, sum(l.refuelNumber) as b,l.car.carCategory.itemName  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate group by l.car.carNo,l.car.carCategory.itemName");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		return query.list();
	}

	/**
	 * 按照年统计
	 * 
	 * @param year
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> queryByYear(Integer year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = new Date(c.getTime().getTime());
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 31);
		Date endDate = new Date(c.getTime().getTime());
		StringBuffer buff = new StringBuffer();
		buff.append("select l.car.carNo,sum(l.trainNumber) as c, sum(l.distance) as a, sum(l.refuelNumber) as b,l.car.carCategory.itemName  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate group by l.car.carNo,l.car.carCategory.itemName");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		return query.list();
	}
}
