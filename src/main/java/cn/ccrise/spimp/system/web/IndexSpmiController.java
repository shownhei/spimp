/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexSpmiController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/spmi/aqk/daily/plan", method = RequestMethod.GET)
	public String spmiAqkDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/aqk/daily/reform", method = RequestMethod.GET)
	public String spmiAqkDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/aqk/document/input", method = RequestMethod.GET)
	public String spmiAqkDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/aqk/document/query", method = RequestMethod.GET)
	public String spmiAqkDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/aqk/quality/facilities", method = RequestMethod.GET)
	public String spmiAqkQualityFacilities() {
		return "spmi/quality/facilities/index";
	}

	@RequestMapping(value = "/spmi/aqk/quality/safety", method = RequestMethod.GET)
	public String spmiAqkQualitySafety() {
		return "spmi/quality/safety/index";
	}

	@RequestMapping(value = "/spmi/dck/daily/plan", method = RequestMethod.GET)
	public String spmiDckDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/dck/daily/reform", method = RequestMethod.GET)
	public String spmiDckDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/dck/document/input", method = RequestMethod.GET)
	public String spmiDckDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/dck/document/query", method = RequestMethod.GET)
	public String spmiDckDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/dck/quality/water", method = RequestMethod.GET)
	public String spmiDckQualityWater() {
		return "spmi/quality/water/index";
	}

	@RequestMapping(value = "/spmi/dds/daily/plan", method = RequestMethod.GET)
	public String spmiDdsDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/dds/daily/reform", method = RequestMethod.GET)
	public String spmiDdsDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/dds/document/input", method = RequestMethod.GET)
	public String spmiDdsDocumentInput() {
		return "spmi/document/schedule/index";
	}

	@RequestMapping(value = "/spmi/dds/document/query", method = RequestMethod.GET)
	public String spmiDdsDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/dds/quality/dispatch", method = RequestMethod.GET)
	public String spmiDdsQualityDispatch() {
		return "spmi/quality/dispatch/index";
	}

	@RequestMapping(value = "/spmi/dds/quality/rescue", method = RequestMethod.GET)
	public String spmiDdsQualityRescue() {
		return "spmi/quality/rescue/index";
	}

	// 安全生产管理信息系统
	/**
	 * 设备参数查询
	 */
	@RequestMapping(value = "/spmi/electro/query", method = RequestMethod.GET)
	public String spmiElectroQuery() {
		return "spmi/electro/query/index";
	}

	/**
	 * 机电故障维修跟踪
	 */
	@RequestMapping(value = "/spmi/electro/repair", method = RequestMethod.GET)
	public String spmiElectroRepair() {
		return "spmi/electro/repair/index";
	}

	@RequestMapping(value = "/spmi/fzs/daily/plan", method = RequestMethod.GET)
	public String spmiFzsDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/fzs/daily/reform", method = RequestMethod.GET)
	public String spmiFzsDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/fzs/document/input", method = RequestMethod.GET)
	public String spmiFzsDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/fzs/document/query", method = RequestMethod.GET)
	public String spmiFzsDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/fzs/quality/water", method = RequestMethod.GET)
	public String spmiFzsQualityWater() {
		return "spmi/quality/water/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/annual-kilometer", method = RequestMethod.GET)
	public String spmiJddCarAnnualKilometer() {
		return "electr/car/annual-kilometer/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/annual-oil", method = RequestMethod.GET)
	public String spmiJddCarAnnualOil() {
		return "electr/car/annual-oil/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/car", method = RequestMethod.GET)
	public String spmiJddCarCar() {
		return "electr/car/car/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/monthly-oil", method = RequestMethod.GET)
	public String spmiJddCarMonthlyOil() {
		return "electr/car/monthly-oil/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/monthly-run", method = RequestMethod.GET)
	public String spmiJddCarMonthlyRun() {
		return "electr/car/monthly-run/index";
	}

	@RequestMapping(value = "/spmi/jdd/car/runlog", method = RequestMethod.GET)
	public String spmiJddCarRunlog() {
		return "electr/car/runlog/index";
	}

	@RequestMapping(value = "/spmi/jdd/daily/picture", method = RequestMethod.GET)
	public String spmiJddDailyPicture() {
		return "spmi/daily/picture/index";
	}

	@RequestMapping(value = "/spmi/jdd/daily/plan", method = RequestMethod.GET)
	public String spmiJddDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/jdd/daily/reward", method = RequestMethod.GET)
	public String spmiJddDailyReward() {
		return "spmi/daily/reward/index";
	}

	@RequestMapping(value = "/spmi/jdd/daily/summary", method = RequestMethod.GET)
	public String spmiJddDailySummary() {
		return "spmi/daily/summary/index";
	}

	@RequestMapping(value = "/spmi/jdd/document/input", method = RequestMethod.GET)
	public String spmiJddDocumentInput() {
		return "spmi/document/wind/index";
	}

	@RequestMapping(value = "/spmi/jdd/document/query", method = RequestMethod.GET)
	public String spmiJddDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/jdd/equipment/alert", method = RequestMethod.GET)
	public String spmiJddEquipmentAlert() {
		return "electr/equipment/alert/index";
	}

	@RequestMapping(value = "/spmi/jdd/equipment/detail", method = RequestMethod.GET)
	public String spmiJddEquipmentDetail() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/spmi/jdd/equipment/overhaul", method = RequestMethod.GET)
	public String spmiJddEquipmentOverhaul() {
		return "electr/equipment/overhaul/index";
	}

	@RequestMapping(value = "/spmi/jdd/equipment/plan", method = RequestMethod.GET)
	public String spmiJddEquipmentPlan() {
		return "electr/equipment/plan/index";
	}

	@RequestMapping(value = "/spmi/jdd/equipment/settings", method = RequestMethod.GET)
	public String spmiJddEquipmentSettings() {
		return "electr/equipment/settings/index";
	}

	@RequestMapping(value = "/spmi/jdd/group", method = RequestMethod.GET)
	public String spmiJddGroup() {
		return "system/group/index";
	}

	@RequestMapping(value = "/spmi/jdd/maintenance/daily", method = RequestMethod.GET)
	public String spmiJddMaintenanceDaily() {
		return "electr/maintenance/daily/index";
	}

	@RequestMapping(value = "/spmi/jdd/maintenance/maintenance-testing", method = RequestMethod.GET)
	public String spmiJddMaintenanceMaintenanceTesting() {
		return "electr/maintenance/maintenance-testing/index";
	}

	@RequestMapping(value = "/spmi/jdd/maintenance/problem", method = RequestMethod.GET)
	public String spmiJddMaintenanceProblem() {
		return "electr/maintenance/problem/index";
	}

	@RequestMapping(value = "/spmi/jdd/maintenance/schedule", method = RequestMethod.GET)
	public String spmiJddMaintenanceSchedule() {
		return "electr/maintenance/schedule/index";
	}

	@RequestMapping(value = "/spmi/jdd/material/plan", method = RequestMethod.GET)
	public String spmiJddMaterialPlan() {
		return "electr/material/plan/index";
	}

	@RequestMapping(value = "/spmi/jdd/material/statistics", method = RequestMethod.GET)
	public String spmiJddMaterialStatistics() {
		return "electr/material/statistics/index";
	}

	@RequestMapping(value = "/spmi/jdd/material/stock", method = RequestMethod.GET)
	public String spmiJddMaterialStock() {
		return "electr/material/stock/index";
	}

	@RequestMapping(value = "/spmi/jdd/material/stock-putin", method = RequestMethod.GET)
	public String spmiJddMaterialStockPutin() {
		return "electr/material/stock-putin/index";
	}

	@RequestMapping(value = "/spmi/jdd/material/stock-sendout", method = RequestMethod.GET)
	public String spmiJddMaterialStockSendout() {
		return "electr/material/stock-sendout/index";
	}

	@RequestMapping(value = "/spmi/jdd/staff", method = RequestMethod.GET)
	public String spmiJddStaff() {
		return "system/staff/index";
	}

	@RequestMapping(value = "/spmi/jdk/daily/plan", method = RequestMethod.GET)
	public String spmiJdkDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/jdk/daily/reform", method = RequestMethod.GET)
	public String spmiJdkDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/jdk/document/input", method = RequestMethod.GET)
	public String spmiJdkDocumentInput() {
		return "spmi/document/machine/index";
	}

	@RequestMapping(value = "/spmi/jdk/document/query", method = RequestMethod.GET)
	public String spmiJdkDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/jdk/quality/electro", method = RequestMethod.GET)
	public String spmiJdkQualityElectro() {
		return "spmi/quality/electro/index";
	}

	@RequestMapping(value = "/spmi/jdk/quality/transportation", method = RequestMethod.GET)
	public String spmiJdkQualityTransportation() {
		return "spmi/quality/transportation/index";
	}

	@RequestMapping(value = "/spmi/jje/daily/plan", method = RequestMethod.GET)
	public String spmiJjeDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/jje/document/input", method = RequestMethod.GET)
	public String spmiJjeDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/jje/document/query", method = RequestMethod.GET)
	public String spmiJjeDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/jje/quality/tunnelling", method = RequestMethod.GET)
	public String spmiJjeQualityTunnelling() {
		return "spmi/quality/tunnelling/index";
	}

	@RequestMapping(value = "/spmi/jjy/daily/plan", method = RequestMethod.GET)
	public String spmiJjyDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/jjy/document/input", method = RequestMethod.GET)
	public String spmiJjyDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/jjy/document/query", method = RequestMethod.GET)
	public String spmiJjyDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/jjy/quality/tunnelling", method = RequestMethod.GET)
	public String spmiJjyQualityTunnelling() {
		return "spmi/quality/tunnelling/index";
	}

	@RequestMapping(value = "/spmi/kte/daily/plan", method = RequestMethod.GET)
	public String spmiKteDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/kte/document/input", method = RequestMethod.GET)
	public String spmiKteDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/kte/document/query", method = RequestMethod.GET)
	public String spmiKteDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/kte/quality/tunnelling", method = RequestMethod.GET)
	public String spmiKteQualityTunnelling() {
		return "spmi/quality/tunnelling/index";
	}

	@RequestMapping(value = "/spmi/kty/daily/plan", method = RequestMethod.GET)
	public String spmiKtyDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/kty/document/input", method = RequestMethod.GET)
	public String spmiKtyDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/kty/document/query", method = RequestMethod.GET)
	public String spmiKtyDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/kty/quality/tunnelling", method = RequestMethod.GET)
	public String spmiKtyQualityTunnelling() {
		return "spmi/quality/tunnelling/index";
	}

	/**
	 * 质量标准化评分-信息调度专业
	 */
	@RequestMapping(value = "/spmi/quality/dispatch", method = RequestMethod.GET)
	public String spmiQualityDispatch() {
		return "spmi/quality/dispatch/index";
	}

	/**
	 * 质量标准化评分-机电专业
	 */
	@RequestMapping(value = "/spmi/quality/electro", method = RequestMethod.GET)
	public String spmiQualityElectro() {
		return "spmi/quality/electro/index";
	}

	/**
	 * 质量标准化评分-地面设施专业
	 */
	@RequestMapping(value = "/spmi/quality/facilities", method = RequestMethod.GET)
	public String spmiQualityFacilities() {
		return "spmi/quality/facilities/index";
	}

	/**
	 * 质量标准化评分-职业健康专业
	 */
	@RequestMapping(value = "/spmi/quality/health", method = RequestMethod.GET)
	public String spmiQualityHealth() {
		return "spmi/quality/health/index";
	}

	/**
	 * 质量标准化评分-采煤专业
	 */
	@RequestMapping(value = "/spmi/quality/mining", method = RequestMethod.GET)
	public String spmiQualityMining() {
		return "spmi/quality/mining/index";
	}

	/**
	 * 质量标准化评分-应急救援专业
	 */
	@RequestMapping(value = "/spmi/quality/rescue", method = RequestMethod.GET)
	public String spmiQualityRescue() {
		return "spmi/quality/rescue/index";
	}

	/**
	 * 质量标准化评分-安全管理专业
	 */
	@RequestMapping(value = "/spmi/quality/safety", method = RequestMethod.GET)
	public String spmiQualitySafety() {
		return "spmi/quality/safety/index";
	}

	/**
	 * 质量标准化评分-运输专业
	 */
	@RequestMapping(value = "/spmi/quality/transportation", method = RequestMethod.GET)
	public String spmiQualityTransportation() {
		return "spmi/quality/transportation/index";
	}

	/**
	 * 质量标准化评分-掘进专业
	 */
	@RequestMapping(value = "/spmi/quality/tunnelling", method = RequestMethod.GET)
	public String spmiQualityTunnelling() {
		return "spmi/quality/tunnelling/index";
	}

	/**
	 * 质量标准化评分-地测防治水专业
	 */
	@RequestMapping(value = "/spmi/quality/water", method = RequestMethod.GET)
	public String spmiQualityWater() {
		return "spmi/quality/water/index";
	}

	/**
	 * 质量标准化评分-一通三防专业
	 */
	@RequestMapping(value = "/spmi/quality/wind", method = RequestMethod.GET)
	public String spmiQualityWind() {
		return "spmi/quality/wind/index";
	}

	@RequestMapping(value = "/spmi/sjk/daily/plan", method = RequestMethod.GET)
	public String spmiSjkDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/sjk/daily/reform", method = RequestMethod.GET)
	public String spmiSjkDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/sjk/document/input", method = RequestMethod.GET)
	public String spmiSjkDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/sjk/document/query", method = RequestMethod.GET)
	public String spmiSjkDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/sjk/quality/mining", method = RequestMethod.GET)
	public String spmiSjkQualityMining() {
		return "spmi/quality/mining/index";
	}

	@RequestMapping(value = "/spmi/tfk/daily/plan", method = RequestMethod.GET)
	public String spmiTfkDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/tfk/daily/reform", method = RequestMethod.GET)
	public String spmiTfkDailyReform() {
		return "spmi/daily/reform/index";
	}

	@RequestMapping(value = "/spmi/tfk/daily/training", method = RequestMethod.GET)
	public String spmiTfkDailyTraining() {
		return "spmi/daily/training/index";
	}

	@RequestMapping(value = "/spmi/tfk/document/input", method = RequestMethod.GET)
	public String spmiTfkDocumentInput() {
		return "spmi/document/wind/index";
	}

	@RequestMapping(value = "/spmi/tfk/document/query", method = RequestMethod.GET)
	public String spmiTfkDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/tfk/equipment/alert", method = RequestMethod.GET)
	public String spmiTfkEquipmentAlert() {
		return "electr/equipment/alert/index";
	}

	@RequestMapping(value = "/spmi/tfk/equipment/detail", method = RequestMethod.GET)
	public String spmiTfkEquipmentDetail() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/spmi/tfk/equipment/overhaul", method = RequestMethod.GET)
	public String spmiTfkEquipmentOverhaul() {
		return "electr/equipment/overhaul/index";
	}

	@RequestMapping(value = "/spmi/tfk/equipment/plan", method = RequestMethod.GET)
	public String spmiTfkEquipmentPlan() {
		return "electr/equipment/plan/index";
	}

	@RequestMapping(value = "/spmi/tfk/equipment/settings", method = RequestMethod.GET)
	public String spmiTfkEquipmentSettings() {
		return "electr/equipment/settings/index";
	}

	@RequestMapping(value = "/spmi/tfk/maintenance/daily", method = RequestMethod.GET)
	public String spmiTfkMaintenanceDaily() {
		return "electr/maintenance/daily/index";
	}

	@RequestMapping(value = "/spmi/tfk/maintenance/maintenance-testing", method = RequestMethod.GET)
	public String spmiTfkMaintenanceMaintenanceTesting() {
		return "electr/maintenance/maintenance-testing/index";
	}

	@RequestMapping(value = "/spmi/tfk/maintenance/problem", method = RequestMethod.GET)
	public String spmiTfkMaintenanceProblem() {
		return "electr/maintenance/problem/index";
	}

	@RequestMapping(value = "/spmi/tfk/maintenance/schedule", method = RequestMethod.GET)
	public String spmiTfkMaintenanceSchedule() {
		return "electr/maintenance/schedule/index";
	}

	@RequestMapping(value = "/spmi/tfk/material/plan", method = RequestMethod.GET)
	public String spmiTfkMaterialPlan() {
		return "electr/material/plan/index";
	}

	@RequestMapping(value = "/spmi/tfk/material/statistics", method = RequestMethod.GET)
	public String spmiTfkMaterialStatistics() {
		return "electr/material/statistics/index";
	}

	@RequestMapping(value = "/spmi/tfk/material/stock", method = RequestMethod.GET)
	public String spmiTfkMaterialStock() {
		return "electr/material/stock/index";
	}

	@RequestMapping(value = "/spmi/tfk/material/stock-putin", method = RequestMethod.GET)
	public String spmiTfkMaterialStockPutin() {
		return "electr/material/stock-putin/index";
	}

	@RequestMapping(value = "/spmi/tfk/material/stock-sendout", method = RequestMethod.GET)
	public String spmiTfkMaterialStockSendout() {
		return "electr/material/stock-sendout/index";
	}

	@RequestMapping(value = "/spmi/tfk/quality/health", method = RequestMethod.GET)
	public String spmiTfkQualityHealth() {
		return "spmi/quality/health/index";
	}

	@RequestMapping(value = "/spmi/tfk/quality/wind", method = RequestMethod.GET)
	public String spmiTfkQualityWind() {
		return "spmi/quality/wind/index";
	}

	@RequestMapping(value = "/spmi/tfk/staff", method = RequestMethod.GET)
	public String spmiTfkStaff() {
		return "system/staff/index";
	}

	@RequestMapping(value = "/spmi/tfs/daily/plan", method = RequestMethod.GET)
	public String spmiTfsDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/tfs/document/input", method = RequestMethod.GET)
	public String spmiTfsDocumentInput() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/tfs/document/query", method = RequestMethod.GET)
	public String spmiTfsDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/annual-kilometer", method = RequestMethod.GET)
	public String spmiZcdCarAnnualKilometer() {
		return "electr/car/annual-kilometer/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/annual-oil", method = RequestMethod.GET)
	public String spmiZcdCarAnnualOil() {
		return "electr/car/annual-oil/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/car", method = RequestMethod.GET)
	public String spmiZcdCarCar() {
		return "electr/car/car/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/monthly-oil", method = RequestMethod.GET)
	public String spmiZcdCarMonthlyOil() {
		return "electr/car/monthly-oil/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/monthly-run", method = RequestMethod.GET)
	public String spmiZcdCarMonthlyRun() {
		return "electr/car/monthly-run/index";
	}

	@RequestMapping(value = "/spmi/zcd/car/runlog", method = RequestMethod.GET)
	public String spmiZcdCarRunlog() {
		return "electr/car/runlog/index";
	}

	@RequestMapping(value = "/spmi/zcd/daily/output", method = RequestMethod.GET)
	public String spmiZcdDailyOutput() {
		return "spmi/daily/output/index";
	}

	@RequestMapping(value = "/spmi/zcd/daily/plan", method = RequestMethod.GET)
	public String spmiZcdDailyPlan() {
		return "spmi/daily/plan/index";
	}

	@RequestMapping(value = "/spmi/zcd/daily/report", method = RequestMethod.GET)
	public String spmiZcdDailyReport() {
		return "spmi/daily/report/index";
	}

	@RequestMapping(value = "/spmi/zcd/document/input", method = RequestMethod.GET)
	public String spmiZcdDocumentInput() {
		return "spmi/document/wind/index";
	}

	@RequestMapping(value = "/spmi/zcd/document/query", method = RequestMethod.GET)
	public String spmiZcdDocumentQuery() {
		return "spmi/document/query/index";
	}

	@RequestMapping(value = "/spmi/zcd/equipment/alert", method = RequestMethod.GET)
	public String spmiZcdEquipmentAlert() {
		return "electr/equipment/alert/index";
	}

	@RequestMapping(value = "/spmi/zcd/equipment/detail", method = RequestMethod.GET)
	public String spmiZcdEquipmentDetail() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/spmi/zcd/equipment/overhaul", method = RequestMethod.GET)
	public String spmiZcdEquipmentOverhaul() {
		return "electr/equipment/overhaul/index";
	}

	@RequestMapping(value = "/spmi/zcd/equipment/plan", method = RequestMethod.GET)
	public String spmiZcdEquipmentPlan() {
		return "electr/equipment/plan/index";
	}

	@RequestMapping(value = "/spmi/zcd/equipment/settings", method = RequestMethod.GET)
	public String spmiZcdEquipmentSettings() {
		return "electr/equipment/settings/index";
	}

	@RequestMapping(value = "/spmi/zcd/maintenance/daily", method = RequestMethod.GET)
	public String spmiZcdMaintenanceDaily() {
		return "electr/maintenance/daily/index";
	}

	@RequestMapping(value = "/spmi/zcd/maintenance/maintenance-testing", method = RequestMethod.GET)
	public String spmiZcdMaintenanceMaintenanceTesting() {
		return "electr/maintenance/maintenance-testing/index";
	}

	@RequestMapping(value = "/spmi/zcd/maintenance/problem", method = RequestMethod.GET)
	public String spmiZcdMaintenanceProblem() {
		return "electr/maintenance/problem/index";
	}

	@RequestMapping(value = "/spmi/zcd/maintenance/schedule", method = RequestMethod.GET)
	public String spmiZcdMaintenanceSchedule() {
		return "electr/maintenance/schedule/index";
	}

	@RequestMapping(value = "/spmi/zcd/quality/mining", method = RequestMethod.GET)
	public String spmiZcdQualityMining() {
		return "spmi/quality/mining/index";
	}

	@RequestMapping(value = "/spmi/zcd/staff", method = RequestMethod.GET)
	public String spmiZcdStaff() {
		return "system/staff/index";
	}
}
