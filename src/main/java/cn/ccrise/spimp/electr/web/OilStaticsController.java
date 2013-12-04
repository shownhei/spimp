package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.service.OilStaticsService;
import cn.ccrise.spimp.electr.service.RunLogService;
import cn.ccrise.spimp.util.DateUtil;

/**
 */
@Controller
public class OilStaticsController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RunLogService runLogService;
	@Autowired
	private OilStaticsService oilStaticsService;

	@RequestMapping(value = "/electr/car/annual-oil/result", method = RequestMethod.GET)
	public ModelAndView getAnnualOil(Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		List<Object> list = queryByYear(year);
		// root.put("result", query.list());
		Iterator<Object> it = list.iterator();
		// ArrayList<AnnualOil> result = new ArrayList<AnnualOil>(24);
		Object[] array = null;
		AnnualOil instance = null;
		int i = 0;
		HashMap<String, ArrayList<AnnualOil>> map = new HashMap<String, ArrayList<AnnualOil>>();
		while (it.hasNext()) {
			array = (Object[]) it.next();
			instance = new AnnualOil();
			i = 0;
			instance.setCarNo((String) array[i++]);
			instance.setTrainNumber((Long) array[i++]);
			instance.setDistance((Long) array[i++]);
			instance.setRefuelNumber((Long) array[i++]);
			instance.setCarCategory((String) array[i++]);
			instance.count();
			if (!map.containsKey(instance.getCarCategory())) {
				map.put(instance.getCarCategory(), new ArrayList<AnnualOil>());
			}
			map.get(instance.getCarCategory()).add(instance);
			// result.add(instance);
		}
		Collection<ArrayList<AnnualOil>> groups = map.values();
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		DecimalFormat df = new DecimalFormat(".##");
		while (groupIt.hasNext()) {
			oilStaticsService.oilAVG(groupIt.next(), df);
		}
		root.put("category", groups);
		return new ModelAndView("electr/car/annual-oil/result", root);
	}

	/**
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-oil/result", method = RequestMethod.GET)
	public ModelAndView getMonthlyOil(Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		root.put("month", month);
		List<Object> list = queryByMonth(year, month);
		Iterator<Object> it = list.iterator();
		Object[] array = null;
		AnnualOil instance = null;
		int i = 0;
		HashMap<String, ArrayList<AnnualOil>> map = new HashMap<String, ArrayList<AnnualOil>>();
		while (it.hasNext()) {
			array = (Object[]) it.next();
			instance = new AnnualOil();
			i = 0;
			instance.setCarNo((String) array[i++]);
			instance.setTrainNumber((Long) array[i++]);
			instance.setDistance((Long) array[i++]);
			instance.setRefuelNumber((Long) array[i++]);
			instance.setCarCategory((String) array[i++]);
			instance.count();
			if (!map.containsKey(instance.getCarCategory())) {
				map.put(instance.getCarCategory(), new ArrayList<AnnualOil>());
			}
			map.get(instance.getCarCategory()).add(instance);
			// result.add(instance);
		}
		Collection<ArrayList<AnnualOil>> groups = map.values();
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		DecimalFormat df = new DecimalFormat(".##");
		while (groupIt.hasNext()) {
			oilStaticsService.oilAVG(groupIt.next(), df);
		}
		root.put("category", groups);
		return new ModelAndView("electr/car/monthly-oil/result", root);
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
		c.set(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = new Date(c.getTime().getTime());
		c.set(Calendar.MONTH, 12);
		c.set(Calendar.DAY_OF_MONTH, 31);
		Date endDate = new Date(c.getTime().getTime());
		StringBuffer buff = new StringBuffer();
		buff.append("select car.carNo,sum(l.trainNumber) as c, sum(l.distance) as a, sum(l.refuelNumber) as b,l.car.carCategory.itemName  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate group by l.car.carNo");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		return query.list();
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
		buff.append("select car.carNo,sum(l.trainNumber) as c, sum(l.distance) as a, sum(l.refuelNumber) as b,l.car.carCategory.itemName  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate group by l.car.carNo");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		return query.list();
	}
}
