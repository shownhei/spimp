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
public class IndexController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// 三维综合管理
	@RequestMapping(value = "/3d", method = RequestMethod.GET)
	public String ddd() {
		return "3d/index";
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
	 * 字典管理
	 */
	@RequestMapping(value = "/system/dictionary", method = RequestMethod.GET)
	public String systemDictionary() {
		return "system/dictionary/index";
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

	/**
	 * 人员管理
	 */
	@RequestMapping(value = "/system/staff", method = RequestMethod.GET)
	public String systemStaff() {
		return "system/staff/index";
	}
}
