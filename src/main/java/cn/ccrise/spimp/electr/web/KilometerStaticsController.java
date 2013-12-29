package cn.ccrise.spimp.electr.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.spimp.electr.entity.AnnualOil;
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.service.KilometerStaticsService;
import cn.ccrise.spimp.util.ExcelCallBackInteface;
import cn.ccrise.spimp.util.ExcelHelper;

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
	private KilometerStaticsService kilometerStaticsService;

	/**
	 * 月度运行情况
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-run/result", method = RequestMethod.GET)
	public ModelAndView getMonthlyRun(Long carId, Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.monthlyRunQuery(carId, year, month, root);
		return new ModelAndView("electr/car/monthly-run/result", root);
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
		kilometerStaticsService.annualOil(year, root);
		return new ModelAndView("electr/car/annual-kilometer/result", root);
	}

	/**
	 * 年度运行情况
	 * 
	 * @param year
	 * @return
	 */
	@RequestMapping(value = "/electr/car/annual-kilometer/export", method = RequestMethod.GET)
	public void getAnnualOilExport(HttpSession httpSession, HttpServletResponse response, Integer year) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.annualOil(year, root);
		new ExcelHelper<AnnualOil>().genExcelWithTel(httpSession, response, "electr/car/annual-kilometer.xls", root,
				year + "无轨胶轮车行程公里一览表", new String[] { year + "无轨胶轮车行程公里一览表" }, new ExcelCallBackInteface() {
					@Override
					public void process(Workbook book, HashMap<String, Object> root) {
						Sheet sheet = book.getSheetAt(0);
					}
				});
	}

	@RequestMapping(value = "/electr/car/annual-kilometer/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, int year) throws Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		kilometerStaticsService.annualOil(year, root);
		ExcelHelper<MaterialsPlan> helper = new ExcelHelper<MaterialsPlan>();
		helper.genExcelWithTel(httpSession, response, "electr/annual-kilometer.xls", root, "导出文档",
				new String[] { "材料计划" });
	}

}
