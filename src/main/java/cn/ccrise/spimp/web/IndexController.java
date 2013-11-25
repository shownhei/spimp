/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadedFileService uploadedFileService;

	/**
	 * 字典管理
	 */
	@RequestMapping(value = "/system/dictionary", method = RequestMethod.GET)
	public String ercsDictionary() {
		return "system/dictionary/index";
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

	/**
	 * 质量标准化评分-信息调度专业
	 */
	@RequestMapping(value = "/spmi/quality/dispatch", method = RequestMethod.GET)
	public String spmiQualityDispatch() {
		return "spmi/quality/dispatch/index";
	}

	/**
	 * 质量标准化评分-职业健康专业
	 */
	@RequestMapping(value = "/spmi/quality/health", method = RequestMethod.GET)
	public String spmiQualityHealth() {
		return "spmi/quality/health/index";
	}

	/**
	 * 质量标准化评分-运输专业
	 */
	@RequestMapping(value = "/spmi/quality/transportation", method = RequestMethod.GET)
	public String spmiQualityTransportation() {
		return "spmi/quality/transportation/index";
	}

	/**
	 * 质量标准化评分-一通三防专业
	 */
	@RequestMapping(value = "/spmi/quality/wind", method = RequestMethod.GET)
	public String spmiQualityWind() {
		return "spmi/quality/wind/index";
	}

	// 系统管理
	/**
	 * 用户管理
	 */
	@RequestMapping(value = "/system/account", method = RequestMethod.GET)
	public String systemAccount() {
		return "system/account/index";
	}

	/**
	 * 机构管理
	 */
	@RequestMapping(value = "/system/group", method = RequestMethod.GET)
	public String systemGroup() {
		return "system/group/index";
	}

	/**
	 * 日志查询
	 */
	@RequestMapping(value = "/system/log", method = RequestMethod.GET)
	public String systemLog() {
		return "system/log/index";
	}

	/**
	 * 菜单管理
	 */
	@RequestMapping(value = "/system/resource", method = RequestMethod.GET)
	public String systemResource() {
		return "system/resource/index";
	}

	/**
	 * 角色管理
	 */
	@RequestMapping(value = "/system/role", method = RequestMethod.GET)
	public String systemRole() {
		return "system/role/index";
	}
}
