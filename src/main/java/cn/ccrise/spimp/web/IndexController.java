/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.web;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadedFileService uploadedFileService;
	@Autowired
	private ResourceEntityServiceImpl resourceEntityServiceImpl;

	/**
	 * 接警处理
	 */
	@RequestMapping(value = "/ercs/alarm", method = RequestMethod.GET)
	public String ercsAlarm() {
		return "ercs/alarm/index";
	}

	/**
	 * 字典管理
	 */
	@RequestMapping(value = "/ercs/dictionary", method = RequestMethod.GET)
	public String ercsDictionary() {
		return "ercs/dictionary/index";
	}

	/**
	 * 应急指示-二维展示
	 */
	@RequestMapping(value = "/ercs/indicate/2d", method = RequestMethod.GET)
	public String ercsIndicate2d() {
		return "ercs/indicate/2d/index";
	}

	/**
	 * 应急法规
	 */
	@RequestMapping(value = "/ercs/law", method = RequestMethod.GET)
	public String ercsLaw() {
		return "ercs/law/index";
	}

	/**
	 * 应急资源
	 */
	@RequestMapping(value = "/ercs/material", method = RequestMethod.GET)
	public String ercsMaterial() {
		return "ercs/material/index";
	}

	/**
	 * 应急机构
	 */
	@RequestMapping(value = "/ercs/organization", method = RequestMethod.GET)
	public String ercsOrganization() {
		return "ercs/organization/index";
	}

	/**
	 * 避难场所
	 */
	@RequestMapping(value = "/ercs/place", method = RequestMethod.GET)
	public String ercsPlace() {
		return "ercs/place/index";
	}

	/**
	 * 应急方案管理
	 */
	@RequestMapping(value = "/ercs/plan", method = RequestMethod.GET)
	public String ercsPlan() {
		return "ercs/plan/index";
	}

	/**
	 * 现场处置方案
	 */
	@RequestMapping(value = "/ercs/scheme", method = RequestMethod.GET)
	public String ercsScheme() {
		return "ercs/scheme/index";
	}

	/**
	 * 应急救援人员
	 */
	@RequestMapping(value = "/ercs/staff", method = RequestMethod.GET)
	public String ercsStaff() {
		return "ercs/staff/index";
	}

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

	@RequestMapping(value = "/ercs/view-pdf/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView get(@PathVariable long id) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("file", uploadedFileService.findUniqueBy("id", id));
		return new ModelAndView("/ercs/view-pdf", root);
	}
}
