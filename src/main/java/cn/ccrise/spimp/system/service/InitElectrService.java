/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.spimp.electr.entity.Car;
import cn.ccrise.spimp.electr.service.CarService;
import cn.ccrise.spimp.system.entity.Dictionary;

/**
 * 应急救援系统基础数据初始化服务。
 * 
 */
@Service
public class InitElectrService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	protected ResourceEntityServiceImpl resourceEntityServiceImpl;
	@Autowired
	private CarService carService;
	@Autowired
	private DictionaryService dictionaryService;

	/**
	 * 机电管理 --基础数据
	 */
	public void initCustomData() {
		HashMap<String, String> carNo2carCategory = new HashMap<String, String>();
		carNo2carCategory.put("FBR011", "人车");
		carNo2carCategory.put("FBR012", "人车");
		carNo2carCategory.put("FBR013", "人车");
		carNo2carCategory.put("FBR015", "人车");
		carNo2carCategory.put("FBR016", "人车");
		carNo2carCategory.put("FBR018", "人车");
		carNo2carCategory.put("FBR019", "人车");
		carNo2carCategory.put("FBR020", "人车");
		carNo2carCategory.put("FBR021", "人车");
		carNo2carCategory.put("FBR022", "人车");
		carNo2carCategory.put("FBR001", "客货车");
		carNo2carCategory.put("FBR002", "客货车");
		carNo2carCategory.put("FBL013", "两驱料车");
		carNo2carCategory.put("FBL015", "两驱料车");
		carNo2carCategory.put("FBL018", "两驱料车");
		carNo2carCategory.put("FBL019", "两驱料车");
		carNo2carCategory.put("FBL020", "其他未录入车型");
		carNo2carCategory.put("FBL016", "四驱料车");
		carNo2carCategory.put("FBL012", "支架搬运车");
		carNo2carCategory.put("FBZ001", "铲运车");
		carNo2carCategory.put("FBZ002", "铲运车");
		carNo2carCategory.put("FBZ003", "铲运车");
		carNo2carCategory.put("FBZ005", "洒水车");
		carNo2carCategory.put("铲板车", "其他未录入车型");
		String carNo[] = { "FBR011", "FBR012", "FBR013", "FBR015", "FBR016", "FBR018", "FBR019", "FBR020", "FBR021",
				"FBR022", "FBR002", "FBZ005", "FBL013", "FBL015", "FBL018", "FBL016", "FBL019", "FBL020", "FBZ001",
				"FBZ002", "FBZ003", "铲板车" };
		String carCategory[] = { "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R", "WC20R",
				"WC20R", "", "", "WC5E", "WC5E", "WC5E", "WC5E", "", "", "", "", "", "" };
		Car car = null;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		List<Dictionary> list = dictionaryService.find(Restrictions.eq("typeCode", "car_carCategory"));
		Iterator<Dictionary> dicTypeIt = list.iterator();
		Dictionary temp = null;
		HashMap<String, Dictionary> dicMap = new HashMap<String, Dictionary>();
		while (dicTypeIt.hasNext()) {
			temp = dicTypeIt.next();
			dicMap.put(temp.getItemName(), temp);
		}
		for (int i = 0; i < carNo.length; i++) {
			car = new Car();
			car.setCarCategory(dicMap.get(carNo2carCategory.get(carNo[i])));
			car.setCarNo(carNo[i]);
			car.setModels(carCategory[i]);
			car.setAddDateTime(timestamp);
			car.setCarStatus(Car.CAR_STATUS_NORMAL);
			carService.save(car);
		}
	}

	/**
	 * 机电管理 三级菜单
	 */
	public void initFourthLevelOperate() {
		/**
		 * 配件管理：采购计划 库存管理 入库管理 出库管理 配件统计
		 */
		String material = resourceEntityServiceImpl.getDefaultIdentifier("/electr/material", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("采购计划", "/electr/material/plan", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("库存管理", "/electr/material/stock", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("入库管理", "/electr/material/stock-putin", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("出库管理", "/electr/material/stock-sendout", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件统计", "/electr/material/statistics", material, "", i++);

		/**
		 * 车辆管理：车辆管理 运行日志 年度统计 月度统计
		 */
		String car = resourceEntityServiceImpl.getDefaultIdentifier("/electr/car", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/electr/car/car", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运行日志", "/electr/car/runlog", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度油耗统计", "/electr/car/annual-oil", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度公里统计", "/electr/car/annual-kilometer", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度油耗统计", "/electr/car/monthly-oil", car, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度运行统计", "/electr/car/monthly-run", car, "", i++);

		/**
		 * 维修保养：日常保养 定期保养 维修检测 故障记录
		 */
		String maintenance = resourceEntityServiceImpl.getDefaultIdentifier("/electr/maintenance", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("日常保养", "/electr/maintenance/daily", maintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养", "/electr/maintenance/schedule", maintenance, "", i++);
		resourceEntityServiceImpl
				.saveMenuResource("保养周期设置", "/electr/maintenance/regular-config", maintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("保养提醒", "/electr/maintenance/regular-remind", maintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修检测", "/electr/maintenance/maintenance-testing", maintenance, "",
				i++);
		resourceEntityServiceImpl.saveMenuResource("故障记录", "/electr/maintenance/problem", maintenance, "", i++);

		/**
		 * 设备管理：设备台账 检修计划 定期检修记录 定期检修提醒 定期检修设置
		 */
		String equipment = resourceEntityServiceImpl.getDefaultIdentifier("/electr/equipment", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("设备台账", "/electr/equipment/detail", equipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("检修计划", "/electr/equipment/plan", equipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修记录", "/electr/equipment/overhaul", equipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修提醒", "/electr/equipment/alert", equipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修设置", "/electr/equipment/settings", equipment, "", i++);
	}

	/**
	 * 机电管理二级菜单
	 */
	public void initThirdLevelMenu() {
		String electr = resourceEntityServiceImpl.getDefaultIdentifier("/electr", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("配件管理", "/electr/material", electr, "", i++);
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/electr/car", electr, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修保养", "/electr/maintenance", electr, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备管理", "/electr/equipment", electr, "", i++);
	}
}
