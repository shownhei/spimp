package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.spimp.electr.service.KilometerStaticsService;
import cn.ccrise.spimp.electr.service.RunLogService;

/**
 * 年度统计、月度统计公里
 * 
 * @author zpf
 * 
 */

@Controller
public class KilometerStaticsController {
	@Autowired
	private RunLogService runLogService;
	@Autowired
	private KilometerStaticsService kilometerStaticsService;

	@RequestMapping(value = "/electr/car/annual-kilometer/result", method = RequestMethod.GET)
	public ModelAndView getAnnualOil(Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = new Date(c.getTime().getTime());
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 31);
		Date endDate = new Date(c.getTime().getTime());
		// 运行日志中所有的车辆号码表
		List<String> carList = getCarArray(startDate, endDate);
		Iterator<String> carNoIt = carList.iterator();
		int carListSize = carList.size();
		// 准备月份行数对应map
		HashMap<String, Integer> monthRowIndexMap = new HashMap<String, Integer>();
		String months[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
		ArrayList<ArrayList<Long>> result = new ArrayList<ArrayList<Long>>();// 最终的结果集
		for (int i = 0; i < months.length; i++) {
			monthRowIndexMap.put(months[i], i);
			ArrayList<Long> row = new ArrayList<Long>(carListSize);
			for (int j = 0; j < carListSize; j++) {
				row.add(0l);
			}
			result.add(row);
		}
		// 存储车号和车对应的月份数据的
		HashMap<String, Integer> carIndexMap = new HashMap<String, Integer>();
		int i = 0;
		while (carNoIt.hasNext()) {
			carIndexMap.put(carNoIt.next(), i++);
		}// 位置存储完毕

		StringBuffer buff = new StringBuffer();
		buff.append("select car.carNo,sum(l.distance),month(l.addDate)  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate group by l.car.carNo,month(l.addDate)");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		// root.put("test", query.list());
		Iterator<?> it = query.list().iterator();
		// Object raw = null;
		Object rawArray[] = null;
		String carNo = null;
		Long distance = null;
		String month = null;
		ArrayList<Long> monthArray = null;
		Long oldDistance = null;
		int colIndex = 0;
		while (it.hasNext()) {
			rawArray = (Object[]) it.next();
			carNo = (String) rawArray[0];
			distance = (Long) rawArray[1];
			month = String.valueOf(rawArray[2]);
			System.out.println(month + "," + monthRowIndexMap.get(month) + ","
					+ result.get(monthRowIndexMap.get(month)));
			monthArray = result.get(monthRowIndexMap.get(month));
			colIndex = carIndexMap.get(carNo);
			oldDistance = monthArray.get(colIndex);
			if (oldDistance == null) {
				oldDistance = 0l;
			}
			monthArray.set(colIndex, oldDistance + distance);
		}
		root.put("carList", carList);
		sum(result);
		root.put("result", result);
		return new ModelAndView("electr/car/annual-kilometer/result", root);
	}

	private void sum(ArrayList<ArrayList<Long>> result) {
		Iterator<ArrayList<Long>> it = result.iterator();
		ArrayList<Long> row = null;
		Long sum = 0l;
		while (it.hasNext()) {
			row = it.next();
			Iterator<Long> col = row.iterator();
			sum = 0l;
			while (col.hasNext()) {
				sum += col.next();
			}
			row.add(sum);
		}
	}

	@SuppressWarnings("unchecked")
	private List<String> getCarArray(Date startDate, Date endDate) {
		String hql = "select distinct l.car.carNo from RunLog l where l.addDate between :startDate and :endDate";
		Query query = runLogService.getDAO().getSession().createQuery(hql);
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		List<Object> carNoList = query.list();
		List<String> result = new ArrayList<String>();
		Iterator<?> it = carNoList.iterator();
		while (it.hasNext()) {
			result.add((String) it.next());
		}
		return result;
	}
}
