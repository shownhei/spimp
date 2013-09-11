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

import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceEntityServiceImpl resourceEntityServiceImpl;

	@RequestMapping(value = "/system/account", method = RequestMethod.GET)
	public String systemAccount() {
		return "system/account/index";
	}
	/**
	 * 字典管理
	 * @return
	 */
	@RequestMapping(value = "/ercs/dictionary", method = RequestMethod.GET)
	public String ercsDictionary() {
		return "ercs/dictionary/index";
	}
	/**
	 * 应急方案管理
	 * @return
	 */
	@RequestMapping(value = "/ercs/plan", method = RequestMethod.GET)
	public String ercsPlan() {
		return "ercs/plan/index";
	}
	@RequestMapping(value = "/system/log", method = RequestMethod.GET)
	public String systemLog() {
		return "system/log/index";
	}
	/**
	 * 应急救援人员
	 * @return
	 */
	@RequestMapping(value = "/ercs/staff", method = RequestMethod.GET)
	public String index() {
		return "ercs/staff/index";
	}
}
