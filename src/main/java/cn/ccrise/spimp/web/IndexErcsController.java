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

import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexErcsController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UploadedFileService uploadedFileService;

	/**
	 * 接警处理
	 */
	@RequestMapping(value = "/ercs/alarm", method = RequestMethod.GET)
	public String ercsAlarm() {
		return "ercs/alarm/index";
	}

	/**
	 * 应急指示-二维展示
	 */
	@RequestMapping(value = "/ercs/indicate-index/2d", method = RequestMethod.GET)
	public String ercsIndicate2d() {
		return "ercs/indicate-index/2d/index";
	}

	/**
	 * 应急指示-三维维展示
	 */
	@RequestMapping(value = "/ercs/indicate-index/3d", method = RequestMethod.GET)
	public String ercsIndicate3d() {
		return "ercs/indicate-index/3d/index";
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
	@RequestMapping(value = "/ercs/material-index/material", method = RequestMethod.GET)
	public String ercsMaterial() {
		return "ercs/material-index/material/index";
	}

	/**
	 * 医护器材
	 */
	@RequestMapping(value = "/ercs/material-index/medical-supply", method = RequestMethod.GET)
	public String ercsMedicalSupply() {
		return "ercs/material-index/medical-supply/index";
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
	@RequestMapping(value = "/plan-index/ercs/plan", method = RequestMethod.GET)
	public String ercsPlan() {
		return "ercs/plan-index/plan/index";
	}

	/**
	 * 应急物资使用记录
	 */
	@RequestMapping(value = "/ercs/material-index/use-record", method = RequestMethod.GET)
	public String ercsResourceUseRecord() {
		return "ercs/material-index/use-record/index";
	}

	/**
	 * 现场处置方案
	 */
	@RequestMapping(value = "/ercs/scheme", method = RequestMethod.GET)
	public String ercsScheme() {
		return "ercs/scheme/index";
	}

	/**
	 * 救援专家
	 * 
	 * @return
	 */
	@RequestMapping(value = "/ercs/staff-index/specia-list", method = RequestMethod.GET)
	public String ercsSpeciaList() {
		return "ercs/staff-index/specia-list/index";
	}

	/**
	 * 应急救援人员
	 */
	@RequestMapping(value = "/ercs/staff-index/staff", method = RequestMethod.GET)
	public String ercsStaff() {
		return "ercs/staff-index/staff/index";
	}

	/**
	 * pdf 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/ercs/view-pdf/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView ercsViewPdf(@PathVariable long id) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		root.put("file", uploadedFileService.findUniqueBy("id", id));
		return new ModelAndView("/ercs/view-pdf", root);
	}

	/**
	 * 应急预案救援任务管理
	 */
	@RequestMapping(value = "/ercs/perform-rescue/task-manage", method = RequestMethod.GET)
	public String indexTaskManage() {
		return "ercs/perform-rescue/task-manage/index";
	}

	/**
	 * 应急预案救援任务查看
	 */
	@RequestMapping(value = "/ercs/perform-rescue/task-view", method = RequestMethod.GET)
	public String indexTaskView() {
		return "ercs/perform-rescue/task-view/index";
	}

	/**
	 * 应急预案救援措施模板
	 */
	@RequestMapping(value = "/ercs/plan-index/template", method = RequestMethod.GET)
	public String indexTemplate() {
		return "ercs/plan-index/template/index";
	}

}
