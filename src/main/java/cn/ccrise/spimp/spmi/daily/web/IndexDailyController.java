/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class IndexDailyController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 事故记录
	 */
	@RequestMapping(value = "/spmi/daily/accident", method = RequestMethod.GET)
	public String accident() {
		return "spmi/daily/accident/index";
	}

	/**
	 * 产量统计
	 */
	@RequestMapping(value = "/spmi/daily/output", method = RequestMethod.GET)
	public String output() {
		return "spmi/daily/output/index";
	}

	// 日常工作

	/**
	 * 图片管理
	 */
	@RequestMapping(value = "/spmi/daily/picture", method = RequestMethod.GET)
	public String picture() {
		return "spmi/daily/picture/index";
	}

	/**
	 * 工作安排
	 */
	@RequestMapping(value = "/spmi/daily/plan", method = RequestMethod.GET)
	public String plan() {
		return "spmi/daily/plan/index";
	}

	/**
	 * 整改通知单
	 */
	@RequestMapping(value = "/spmi/daily/reform", method = RequestMethod.GET)
	public String reform() {
		return "spmi/daily/reform/index";
	}

	/**
	 * 生产日报表
	 */
	@RequestMapping(value = "/spmi/daily/report", method = RequestMethod.GET)
	public String report() {
		return "spmi/daily/report/index";
	}

	/**
	 * 奖惩记录
	 */
	@RequestMapping(value = "/spmi/daily/reward", method = RequestMethod.GET)
	public String reward() {
		return "spmi/daily/reward/index";
	}

	/**
	 * 每月总结
	 */
	@RequestMapping(value = "/spmi/daily/summary", method = RequestMethod.GET)
	public String summary() {
		return "spmi/daily/summary/index";
	}

	/**
	 * 培训计划
	 */
	@RequestMapping(value = "/spmi/daily/training", method = RequestMethod.GET)
	public String training() {
		return "spmi/daily/training/index";
	}
}
