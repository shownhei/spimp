/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.util.AES;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/**
 * 登录控制器。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LoginController {
	private static final String PRINCIPAL_BLANK = "用户名不能为空";
	private static final String CREDENTIAL_BLANK = "密码不能为空";
	private static final String PRINCIPAL_OR_CREDENTIAL_WRONG = "用户名或密码不正确";
	private static final String PRINCIPAL_LOCKED = "此用户被锁定，请联系管理员";
	private static final String UNRFGISTERED = "软件未注册，请与供应商联系";
	private static final String EXPIRE = "软件已过试用期，请与供应商联系";
	private static final int MAX_INACTIVE_INTERVAL = 3600;

	public static final String KEY = "ercs-------1.0.0";

	public static void main(String[] args) {
		String day = new SimpleDateFormat("yyyy-MM-dd").format(DateUtils.addDays(new Date(System.currentTimeMillis()),
				5));
		String license = AES.encodeAes128(KEY, day);
		System.out.println(license);
		System.out.println(AES.decodeAes128(KEY, license));
	}

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ResourceEntityServiceImpl resourceEntityServiceImpl;

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	@ResponseBody
	public Response auth(String principal, String credential, HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
		// 用户名和密码不能为空
		boolean isPrincipalBlank = StringUtils.isBlank(principal);
		boolean isCredentialBlank = StringUtils.isBlank(credential);
		Map<String, String> errors = Maps.newHashMap();
		if (isPrincipalBlank) {
			errors.put("principal", PRINCIPAL_BLANK);
		}
		if (isCredentialBlank) {
			errors.put("credential", CREDENTIAL_BLANK);
		}

		// 判断是否注册和过期
		String license = PropertiesUtils.getString("app.license");
		if (Strings.isNullOrEmpty(license)) {
			errors.put("message", UNRFGISTERED);
			return new Response(errors);
		}
		Date expireDay = null;
		try {
			expireDay = new SimpleDateFormat("yyyy-MM-dd").parse(AES.decodeAes128(KEY, license));
		} catch (ParseException e) {
			errors.put("message", UNRFGISTERED);
			return new Response(errors);
		}
		if (expireDay.before(new Date())) {
			errors.put("message", EXPIRE);
			return new Response(errors);
		}

		// 验证用户名和密码
		if (!isPrincipalBlank && !isCredentialBlank) {
			if (accountService.auth(principal, credential)) {
				// 处理session
				Account loginAccount = accountService.get(principal);

				// 判断是否锁定
				if (loginAccount.isLocked()) {
					errors.put("message", PRINCIPAL_LOCKED);
				} else {
					httpSession.setAttribute(PropertiesUtils.getString(PropertiesUtils.SESSION_KEY_PROPERTY),
							loginAccount);
					httpSession.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);

					// 记录日志
					logEntityServiceImpl.info(LogEntityServiceImpl.DEFAULT_USER_NAME, "账户[" + principal + "]已登录",
							httpServletRequest.getRemoteAddr());
				}
			} else {
				errors.put("message", PRINCIPAL_OR_CREDENTIAL_WRONG);

				// 记录日志
				logEntityServiceImpl.info(LogEntityServiceImpl.DEFAULT_USER_NAME, "账户[" + principal + "]密码不正确",
						httpServletRequest.getRemoteAddr());
			}
		}

		return new Response(errors);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(HttpSession httpSession) {
		Map<String, Object> model = Maps.newHashMap();
		model.put("menus", menus(httpSession));
		model.put("account", WebUtils.getAccount(httpSession));
		ModelAndView modelAndView = new ModelAndView("index", model);
		return modelAndView;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		// 清除session
		httpSession.removeAttribute(PropertiesUtils.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		return "redirect:" + PropertiesUtils.getString(PropertiesUtils.LOGIN_PATH_PROPERTY);
	}

	@RequestMapping(value = "/menus", method = RequestMethod.GET)
	@ResponseBody
	public ResourceEntity menus(HttpSession httpSession) {
		return resourceEntityServiceImpl.getMenus(WebUtils.getRole(httpSession), true);
	}
}
