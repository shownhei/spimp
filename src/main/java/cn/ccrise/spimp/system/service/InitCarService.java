/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.service;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.spimp.spmi.car.entity.Car;
import cn.ccrise.spimp.spmi.car.service.CarService;

/**
 * 应急救援系统基础数据初始化服务。
 * 
 */
@Service
public class InitCarService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ResourceEntityServiceImpl resourceEntityServiceImpl;
	@Autowired
	private CarService carService;

	/**
	 * 胶轮车管理 --基础数据
	 */
	public void initCustomData() {

		String carNo[] = { "FBR011", "FBR012", "FBR013", "FBR015", "FBR016", "FBR018", "FBR019", "FBR020", "FBR021",
				"FBR022", "FBR002", "FBZ005", "FBL013", "FBL015", "FBL018", "FBL016", "FBL019", "FBL020", "FBZ001",
				"FBZ002", "FBZ003", "铲板车" };
		String carCategory[] = { "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R",
				"WC20R", "", "", "WC5E", "WC5E", "WC5E", "WC5E", "", "", "", "", "", "" };
		Car car = null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		for (int i = 0; i < carNo.length; i++) {
			car = new Car();
			car.setCarNo(carNo[i]);
			car.setModels(carCategory[i]);
			car.setAddDateTime(timestamp);
			car.setCarStatus(Car.CAR_STATUS_NORMAL);
			carService.save(car);
		}
	}

	/**
	 * 胶轮车管理 三级菜单
	 */
	public void initFourthLevelOperate() {
		/**
		 * 胶轮车管理--配件管理--备件管理
		 */
		String material = resourceEntityServiceImpl.getDefaultIdentifier("/car/material", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("采购计划", "/car/material/plan", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("库存管理", "/car/material/stock", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("入库管理", "/car/material/stock_putin", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("出库管理", "/car/material/stock_sendout", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件统计", "/car/material/statistics", material, "", i++);
		/**
		 * 胶轮车管理--运行日志
		 */
		String runlog = resourceEntityServiceImpl.getDefaultIdentifier("/car/runlog", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("运行日志", "/car/runlog/runlog", runlog, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度统计", "/car/runlog/monthly_statistics", runlog, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度统计", "/car/runlog/annual_statistics", runlog, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度统计图表", "/car/runlog/monthly_statistics_chart", runlog, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度统计图表", "/car/runlog/annual_statistics_chart", runlog, "", i++);
		/**
		 * 胶轮车管理--运行日志
		 */
		String maintenance = resourceEntityServiceImpl.getDefaultIdentifier("/car/maintenance", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("日常保养", "/car/maintenance/daily", maintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养", "/car/maintenance/schedule", maintenance, "", i++);
		resourceEntityServiceImpl
				.saveMenuResource("维修监测", "/car/maintenance/maintenance-testing", maintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("故障记录", "/car/maintenance/problem", maintenance, "", i++);

	}

	/**
	 * 胶轮车管理 二级菜单
	 */
	public void initThirdLevelMenu() {
		String car = resourceEntityServiceImpl.getDefaultIdentifier("/car", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/car/car", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("备件管理", "/car/material", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运行日志", "/car/runlog", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修保养", "/car/maintenance", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("规章制度", "/car/regulation", car, "", i++);
	}
}
