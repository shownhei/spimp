package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import cn.ccrise.spimp.electr.service.CarService;
import cn.ccrise.spimp.electr.service.KilometerStaticsService;
import cn.ccrise.spimp.electr.service.RunLogService;
import cn.ccrise.spimp.util.DateUtil;

/**
 * 年度统计、月度统计公里
 * 
 * @author zpf
 * 
 */

@Controller
public class KilometerStaticsController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RunLogService runLogService;
	@Autowired
	private KilometerStaticsService kilometerStaticsService;
	@Autowired
	private CarService carService;

	/**
	 * 月度运行情况
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/electr/car/monthly-run/result", method = RequestMethod.GET)
	public ModelAndView getMonthlyRun(Long carId, Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		root.put("month", month);
		root.put("year", year);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		Date startDate = new Date(c.getTime().getTime());
		c.set(Calendar.MONTH, month - 1);
		c.set(Calendar.DAY_OF_MONTH, DateUtil.getCurrentMonthLastDay());
		Date endDate = new Date(c.getTime().getTime());
		// 运行日志中所有的车辆号码表
		List<String> carList = null;
		carList = getCarArray(startDate, endDate, carId);
		root.put("carList", carList);

		ArrayList<ArrayList<Long>> result = new ArrayList<ArrayList<Long>>();// 最终的结果集

		StringBuffer buff = new StringBuffer();
		buff.append("select l.car.carNo,sum(l.trainNumber),sum(l.distance),sum(l.refuelNumber),l.addDate,l.classType  ");
		buff.append(" from RunLog l where l.addDate between :startDate and :endDate  ");
		if (carId != null) {
			buff.append(" and l.car.id=:carId ");
		}
		buff.append("  group by l.car.carNo,l.addDate,l.classType ");
		Query query = runLogService.getDAO().getSession().createQuery(buff.toString());
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		if (carId != null) {
			query.setLong("carId", carId);
		}
		List<Object> runlogList = query.list();
		ArrayList<Long> sumList = new ArrayList<Long>();
		caculateMonthlyRunLog(carList, result, runlogList, sumList);
		root.put("sumList", sumList);
		root.put("result", result);
		return new ModelAndView("electr/car/monthly-run/result", root);
	}

	/**
	 * 月度情况计算,1:车号，2：车次，3：路程，4：加油，5：日期，6：班次
	 */
	private void caculateMonthlyRunLog(List<String> carList, ArrayList<ArrayList<Long>> result,
			List<Object> runlogsGroup, ArrayList<Long> sumList) {
		HashMap<Integer, Integer> monthRowIndexMap = new HashMap<Integer, Integer>();
		int colCount = carList.size() * 10;
		Iterator<String> carIt = carList.iterator();
		HashMap<String, Integer> car2baseIndexMap = new HashMap<String, Integer>();
		int baseIndex = 0;
		int step = 10;
		while (carIt.hasNext()) {
			car2baseIndexMap.put(carIt.next(), step * baseIndex++);
		}
		int maxDay = 31;
		for (int i = 0; i < maxDay; i++) {
			monthRowIndexMap.put(i, i);
			ArrayList<Long> row = new ArrayList<Long>(colCount);
			for (int j = 0; j < colCount; j++) {
				row.add(0l);
			}
			result.add(row);
		}
		Iterator<Object> it = runlogsGroup.iterator();
		Object rawarray[] = null;
		String carNo = null;
		Long trainNumber = null;
		Long distance = null;
		Long refuelNumber = null;
		Date addDate = null;
		String classType = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String day = null;
		int rowIndex = 0;
		ArrayList<Long> row = null;
		HashMap<String, Integer> class2Index = new HashMap<String, Integer>();
		// 班次排序 0、8、4
		class2Index.put("0", 0);
		class2Index.put("8", 3);
		class2Index.put("4", 6);
		for (int i = 0; i < colCount; i++) {
			sumList.add(0l);
		}
		while (it.hasNext()) {
			rawarray = (Object[]) it.next();
			carNo = (String) rawarray[0];
			trainNumber = (Long) rawarray[1];
			distance = (Long) rawarray[2];
			refuelNumber = (Long) rawarray[3];
			addDate = (Date) rawarray[4];
			day = sdf.format(addDate);
			rowIndex = Integer.parseInt(day) - 1;
			classType = (String) rawarray[5];
			row = result.get(rowIndex);
			baseIndex = car2baseIndexMap.get(carNo);
			int colIndex = baseIndex + class2Index.get(classType);
			row.set(colIndex, row.get(colIndex) + trainNumber);
			row.set(colIndex + 1, row.get(colIndex + 1) + distance);
			row.set(colIndex + 2, row.get(colIndex + 2) + refuelNumber);
			sumList.set(colIndex, row.get(colIndex));
			sumList.set(colIndex + 1, row.get(colIndex + 1));
			sumList.set(colIndex + 2, row.get(colIndex + 2));
		}
	}

	/**
	 * 年度运行情况
	 * 
	 * @param year
	 * @return
	 */
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
		List<String> carList = getCarArray(startDate, endDate, null);
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
	private List<String> getCarArray(Date startDate, Date endDate, Long carId) {
		String hql = "select distinct l.car.carNo from RunLog l where l.addDate between :startDate and :endDate ";
		if (carId != null) {
			hql += " and l.car.id=:carId";
		}
		Query query = runLogService.getDAO().getSession().createQuery(hql);
		query.setDate("startDate", startDate);
		query.setDate("endDate", endDate);
		if (carId != null) {
			query.setLong("carId", carId);
		}
		List<Object> carNoList = query.list();
		List<String> result = new ArrayList<String>();
		Iterator<?> it = carNoList.iterator();
		while (it.hasNext()) {
			result.add((String) it.next());
		}
		return result;
	}
}
