package cn.ccrise.spimp.electr.web;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.service.OilStaticsService;
import cn.ccrise.spimp.electr.service.RunLogService;
import cn.ccrise.spimp.util.DateUtil;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

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
		doQueryAnnualOil(year, root);
		return new ModelAndView("electr/car/annual-oil/result", root);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/electr/car/annual-oil/export", method = RequestMethod.GET)
	public void getAnnualOilExport(HttpSession httpSession, HttpServletResponse response, Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		doQueryAnnualOil(year, root);
		final Collection<ArrayList<AnnualOil>> groups = (Collection<ArrayList<AnnualOil>>) root.get("category");
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		ArrayList<AnnualOil> recordList = null;
		ArrayList<AnnualOil> result = new ArrayList<AnnualOil>();
		while (groupIt.hasNext()) {
			recordList = groupIt.next();
			Iterator<AnnualOil> recordIt = recordList.iterator();
			while (recordIt.hasNext()) {
				result.add(recordIt.next());
			}
		}
		root.put("result", result);
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response, "electr/car/annual-oil.xls", root, year
				+ "年无轨胶轮车百公里油耗计算表", new String[] { year + "年无轨胶轮车百公里油耗计算表" }, new ExcelCallBackInteface() {

			@Override
			public void process(Workbook book, HashMap<String, Object> root) {
				Sheet sheet = book.getSheetAt(0);
				final Collection<ArrayList<AnnualOil>> groups = (Collection<ArrayList<AnnualOil>>) root.get("category");
				ArrayList<AnnualOil> recordList = null;
				Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
				int start_row = 2;
				int start_col_1 = 4;
				int start_col_2 = 6;
				while (groupIt.hasNext()) {
					recordList = groupIt.next();
					/**
					 * 合并日平均运行 次数（次）
					 */
					CellRangeAddress temp = new CellRangeAddress(start_row, start_row + recordList.size() - 1,
							start_col_1, start_col_1);
					sheet.addMergedRegion(temp);

					/**
					 * 平均油耗（升）
					 */
					temp = new CellRangeAddress(start_row, start_row + recordList.size() - 1, start_col_2, start_col_2);
					sheet.addMergedRegion(temp);
					start_row += recordList.size();
				}
			}

		});
	}

	@RequestMapping(value = "/electr/car/annual-oil/resultchart", method = RequestMethod.GET)
	@ResponseBody
	public Response getAnnualOilForChart(Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		doQueryAnnualOil(year, root);
		logger.debug("{}", root);
		return new Response(root);
	}

	/**
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-oil/result", method = RequestMethod.GET)
	public ModelAndView getMonthlyOil(Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		monthOilQuery(year, month, root);
		return new ModelAndView("electr/car/monthly-oil/result", root);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/electr/car/monthly-oil/export", method = RequestMethod.GET)
	public void getmonthlyOilExport(HttpSession httpSession, HttpServletResponse response, Integer year, int month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		monthOilQuery(year, month, root);
		final Collection<ArrayList<AnnualOil>> groups = (Collection<ArrayList<AnnualOil>>) root.get("category");
		Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
		ArrayList<AnnualOil> recordList = null;
		ArrayList<AnnualOil> result = new ArrayList<AnnualOil>();
		while (groupIt.hasNext()) {
			recordList = groupIt.next();
			Iterator<AnnualOil> recordIt = recordList.iterator();
			while (recordIt.hasNext()) {
				result.add(recordIt.next());
			}
		}
		root.put("result", result);
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response, "electr/car/monthly-oil.xls", root, year
				+ "年" + month + "月无轨胶轮车百公里油耗计算表", new String[] { year + "年" + month + "月无轨胶轮车百公里油耗计算表" },
				new ExcelCallBackInteface() {

					@Override
					public void process(Workbook book, HashMap<String, Object> root) {
						Sheet sheet = book.getSheetAt(0);
						final Collection<ArrayList<AnnualOil>> groups = (Collection<ArrayList<AnnualOil>>) root
								.get("category");
						ArrayList<AnnualOil> recordList = null;
						Iterator<ArrayList<AnnualOil>> groupIt = groups.iterator();
						int start_row = 2;
						int start_col_1 = 4;
						int start_col_2 = 6;
						while (groupIt.hasNext()) {
							recordList = groupIt.next();
							/**
							 * 合并日平均运行 次数（次）
							 */
							CellRangeAddress temp = new CellRangeAddress(start_row, start_row + recordList.size() - 1,
									start_col_1, start_col_1);
							sheet.addMergedRegion(temp);

							/**
							 * 平均油耗（升）
							 */
							temp = new CellRangeAddress(start_row, start_row + recordList.size() - 1, start_col_2,
									start_col_2);
							sheet.addMergedRegion(temp);
							start_row += recordList.size();
						}
					}

				});
	}

	/**
	 * 年度油耗查询统计
	 * 
	 * @param year
	 * @param root
	 */
	private void doQueryAnnualOil(Integer year, HashMap<String, Object> root) {
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
		df.applyPattern("0.00");
		while (groupIt.hasNext()) {
			oilStaticsService.oilAVG(groupIt.next(), df, DateUtil.getMaxDaysOfYear(DateUtil.getCurrentYear()));
		}
		root.put("category", groups);
	}

	private void monthOilQuery(Integer year, Integer month, HashMap<String, Object> root) {
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
	}

}
