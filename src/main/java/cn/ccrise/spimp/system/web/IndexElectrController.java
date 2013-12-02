/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

}
