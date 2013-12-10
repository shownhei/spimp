package cn.ccrise.spimp.electr.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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
 * 油耗计算 年度 月度
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
		List<Object> list = oilStaticsService.queryByYear(year);
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
		}
		Collection<ArrayList<AnnualOil>> groups = map.values();
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		DecimalFormat df = new DecimalFormat(".##");
		while (groupIt.hasNext()) {
			oilStaticsService.oilAVG(groupIt.next(), df, DateUtil.getMaxDaysOfYear(DateUtil.getCurrentYear()));
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
		List<Object> list = oilStaticsService.queryByMonth(year, month);
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
		}
		Collection<ArrayList<AnnualOil>> groups = map.values();
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		DecimalFormat df = new DecimalFormat(".##");
		while (groupIt.hasNext()) {
			oilStaticsService.oilAVG(groupIt.next(), df, DateUtil.getCurrentMonthLastDay());
		}
		root.put("category", groups);
		return new ModelAndView("electr/car/monthly-oil/result", root);
	}

}
