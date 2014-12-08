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

	@RequestMapping(value = "/location/foreman", method = RequestMethod.GET)
	public String locationForeman() {
		return "location/foreman/index";
	}

	@RequestMapping(value = "/location/info", method = RequestMethod.GET)
	public String locationInfo() {
		return "location/info/index";
	}

	@RequestMapping(value = "/location/query", method = RequestMethod.GET)
	public String locationQuery() {
		return "location/query/index";
	}

	@RequestMapping(value = "/location/realtime", method = RequestMethod.GET)
	public String locationRealtime() {
		return "location/realtime/index";
	}

	@RequestMapping(value = "/monitor/curve", method = RequestMethod.GET)
	public String monitorCurve() {
		return "monitor/curve/index";
	}

	@RequestMapping(value = "/monitor/info", method = RequestMethod.GET)
	public String monitorInfo() {
		return "monitor/info/index";
	}

	@RequestMapping(value = "/monitor/query", method = RequestMethod.GET)
	public String monitorQuery() {
		return "monitor/query/index";
	}

	@RequestMapping(value = "/monitor/realtime", method = RequestMethod.GET)
	public String monitorRealtime() {
		return "monitor/realtime/index";
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

	/**
	 * 监察监控三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monitor/realtime3D", method = RequestMethod.GET)
	public String monitor3D() {
		return "monitor/realtime/index3D";
	}

	/**
	 * 人员定位三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/location/realtime3D", method = RequestMethod.GET)
	public String location3D() {
		return "location/realtime/index3D";
	}

	/**
	 * 人员定位历史查询三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/location/query3D", method = RequestMethod.GET)
	public String locationQuery3D() {
		return "location/query/index3D";
	}

	/**
	 * 人员定位基本信息三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/location/info3D", method = RequestMethod.GET)
	public String locationInfo3D() {
		return "location/info/index3D";
	}

	/**
	 * 监测监控历史曲线三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monitor/curve3D", method = RequestMethod.GET)
	public String monitorCurve3D() {
		return "monitor/curve/index3D";
	}

	/**
	 * 监测监控历史查询三维显示接口界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/monitor/query3D", method = RequestMethod.GET)
	public String monitorQuery3D() {
		return "monitor/query/index3D";
	}
}
