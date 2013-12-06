/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * 机电管理模块
 */
@Controller
public class IndexElectrController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadedFileService uploadedFileService;

	/**
	 * 库存管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/material/stock", method = RequestMethod.GET)
	public String index() {
		return "electr/material/stock/index";
	}

	/**
	 * 入库管理
	 */
	@RequestMapping(value = "/electr/material/stock-putin", method = RequestMethod.GET)
	public String stockPutin() {
		return "/electr/material/stock-putin/index";
	}

	/**
	 * 出库管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/material/stock-sendout", method = RequestMethod.GET)
	public String ercsAlarm() {
		return "/electr/material/stock-sendout/index";
	}

	/**
	 * 车辆管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/car/car", method = RequestMethod.GET)
	public String car() {
		return "electr/car/car/index";
	}

	/**
	 * 车辆运行日志
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/car/runlog", method = RequestMethod.GET)
	public String carRunlog() {
		return "electr/car/runlog/index";
	}

	/**
	 * 日常维修的界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/daily", method = RequestMethod.GET)
	public String daily() {
		return "electr/maintenance/daily/index";
	}

	/**
	 * 定期维修的界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/schedule", method = RequestMethod.GET)
	public String schedule() {
		return "electr/maintenance/schedule/index";
	}

	/**
	 * 事故管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/accident/record", method = RequestMethod.GET)
	public String accidentRecord() {
		return "electr/accident/record/index";
	}

	/**
	 * 制度文件管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/regulation/file", method = RequestMethod.GET)
	public String regulationFile() {
		return "electr/regulation/file/index";
	}

	/**
	 * 奖惩记录
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/regulation/reward", method = RequestMethod.GET)
	public String regulationReward() {
		return "electr/regulation/reward/index";
	}

	/**
	 * 工作安排
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/regulation/arrange", method = RequestMethod.GET)
	public String workArrange() {
		return "electr/regulation/arrange/index";
	}

	/**
	 * 图纸管理
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/regulation/drawing", method = RequestMethod.GET)
	public String drawing() {
		return "electr/regulation/drawing/index";
	}

	/**
	 * 年度油耗统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/car/annual-oil", method = RequestMethod.GET)
	public String annualOil() {
		return "electr/car/annual-oil/index";
	}

	/**
	 * 月度油耗统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-oil", method = RequestMethod.GET)
	public String monthlyOil() {
		return "electr/car/monthly-oil/index";
	}

	/**
	 * 年度公里统计一览表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/car/annual-kilometer", method = RequestMethod.GET)
	public ModelAndView getAnnualKilometer() {
		HashMap<String, Object> root = new HashMap<String, Object>();
		return new ModelAndView("electr/car/annual-kilometer/index", root);
	}

	/**
	 * 月度运行公里统计情况
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	@RequestMapping(value = "/electr/car/monthly-run", method = RequestMethod.GET)
	public ModelAndView getMonthlyRun(Integer year, Integer month) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("year", year);
		root.put("month", month);
		return new ModelAndView("electr/car/monthly-run/index", root);
	}
}
