/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.spimp.system.entity.Account;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class Index3DController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	// 综合自动化管控平台
	@RequestMapping(value = "/ignore/auto", method = RequestMethod.GET)
	public String auto() {
		return "auto/index";
	}

	@RequestMapping(value = "/3d.html", method = RequestMethod.GET)
	public String dd(HttpSession httpSession) {
		return "3d/3d";
	}

	// 三维综合管理
	@RequestMapping(value = "/3d", method = RequestMethod.GET)
	public String ddd(HttpSession httpSession) {
		Account account = ((Account) WebUtils.getAccount(httpSession));
		if(account==null){
			return "redirect:" + PropertiesUtils.getString(PropertiesUtils.LOGIN_PATH_PROPERTY);
		}
		logger.debug("{}",account);
		return "3d/index";
	}
	//人机环对话框界面
	@RequestMapping(value = "/3d/rjh", method = RequestMethod.GET)
	public ModelAndView rjh(String areas) {
		System.out.println(areas);
		HashMap<String, Object> root = new HashMap<String, Object>();
		LinkedList<String> areaList=new LinkedList<String>();
		if(StringUtils.isNotBlank(areas)){
			for(String temp:areas.split(",")){
				areaList.add(temp);
			}
		}
		root.put("areas", areaList);
		return new ModelAndView("3d/rjh",root);
	}
	/**
	 * 轨迹回放
	 * @param areas
	 * @return
	 */
	@RequestMapping(value = "/3d/trace-playback", method = RequestMethod.GET)
	public ModelAndView tracePlayback(String areas) {
		System.out.println(areas);
		HashMap<String, Object> root = new HashMap<String, Object>();
		LinkedList<String> areaList=new LinkedList<String>();
		if(StringUtils.isNotBlank(areas)){
			for(String temp:areas.split(",")){
				areaList.add(temp);
			}
		}
		root.put("areas", areaList);
		return new ModelAndView("3d/trace-playback",root);
	}
	/**
	 * 点选一个物体
	 * @param areas
	 * @return
	 */
	@RequestMapping(value = "/3d/single-click", method = RequestMethod.POST)
	public ModelAndView singleClick(@RequestBody String content) {
		System.out.println(content);
		HashMap<String, Object> root = new HashMap<String, Object>();
		try {
			String decodeStr=java.net.URLDecoder.decode(content,"UTF-8");
			System.out.println(decodeStr);
			root.put("content", decodeStr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ModelAndView("3d/single-click",root);
	}
}
