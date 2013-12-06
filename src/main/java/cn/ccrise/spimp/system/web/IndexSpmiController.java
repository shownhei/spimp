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
}
