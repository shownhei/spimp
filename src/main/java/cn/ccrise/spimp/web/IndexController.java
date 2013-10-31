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
	@RequestMapping(value = "/system/dictionary", method = RequestMethod.GET)
	public String ercsDictionary() {
		return "system/dictionary/index";
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
	 * 医护器材
	 */
	@RequestMapping(value = "/ercs/medical-supply", method = RequestMethod.GET)
	public String ercsMedicalSupply() {
		return "ercs/medical-supply/index";
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
	 * 应急物资使用记录
	 */
	@RequestMapping(value = "/ercs/use-record", method = RequestMethod.GET)
	public String ercsResourceUseRecord() {
		return "ercs/use-record/index";
	}

	/**
	 * 现场处置方案
	 */
	@RequestMapping(value = "/ercs/scheme", method = RequestMethod.GET)
	public String ercsScheme() {
		return "ercs/scheme/index";
	}

	@RequestMapping(value = "/ercs/specia-list", method = RequestMethod.GET)
	public String ercsSpeciaList() {
		return "ercs/specia-list/index";
	}

	/**
	 * 应急救援人员
	 */
	@RequestMapping(value = "/ercs/staff", method = RequestMethod.GET)
	public String ercsStaff() {
		return "ercs/staff/index";
	}

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
	@RequestMapping(value = "/ercs/task-manage", method = RequestMethod.GET)
	public String indexTaskManage() {
		return "ercs/task-manage/index";
	}

	/**
	 * 应急预案救援任务查看
	 */
	@RequestMapping(value = "/ercs/task-view", method = RequestMethod.GET)
	public String indexTaskView() {
		return "ercs/task-view/index";
	}

	/**
	 * 应急预案救援措施模板
	 */
	@RequestMapping(value = "/ercs/template", method = RequestMethod.GET)
	public String indexTemplate() {
		return "ercs/template/index";
	}

	/**
	 * 调度系统 矿井掘进进尺
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/schedule/dig", method = RequestMethod.GET)
	public String scheduleDig() {
		return "spmi/schedule/dig/index";
	}

	/**
	 * 调度管理 矿井原煤产量
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/schedule/output", method = RequestMethod.GET)
	public String scheduleOutput() {
		return "spmi/schedule/output/index";
	}

	/**
	 * 调度系统 生产准备工作情况
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/schedule/prepare", method = RequestMethod.GET)
	public String schedulePrepare() {
		return "spmi/schedule/prepare/index";
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
	 * 质量标准化-调度专业
	 */
	@RequestMapping(value = "/spmi/quality/dispatch", method = RequestMethod.GET)
	public String spmiQualityDispatch() {
		return "spmi/quality/dispatch/index";
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
