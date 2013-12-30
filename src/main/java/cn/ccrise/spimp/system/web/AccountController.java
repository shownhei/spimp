/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Account Controller。
 * <p>
 * 用户管理控制器。请谨慎修改源码！
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class AccountController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountService accountService;
	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/system/accounts/change", method = RequestMethod.PUT)
	@ResponseBody
	public Response change(HttpSession httpSession, String oldPassword, String newPassword) {
		Account account = ((Account) WebUtils.getAccount(httpSession));
		Account accountInDB = accountService.get(account.getId());

		// 验证旧密码是否正确
		if (DigestUtils.sha256Hex(oldPassword).equals(accountInDB.getCredential())) {
			// 设置新密码
			accountInDB.setCredential(DigestUtils.sha256Hex(newPassword));
			accountService.update(accountInDB);
			String[] args = { account.getPrincipal() };
			logEntityServiceImpl.warn(messageSource.getMessage("account.change.password", args, Locale.getDefault()));
			return new Response(true);
		} else {
			Map<String, String> errors = Maps.newHashMap();
			errors.put("password", messageSource.getMessage("Incorrect.account.password", null, Locale.getDefault()));
			return new Response(errors);
		}
	}

	@RequestMapping(value = "/system/accounts/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		Account account = accountService.get(id);
		return response(account, accountService.delete(account), "delete");
	}

	@RequestMapping(value = "/system/accounts/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		Account account = accountService.get(id);
		ResponseDataFilter.filter(account);
		return new Response(account);
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/system/accounts", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Account> page, String search, String q) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (search != null) {
			criterions.add(Restrictions.or(Restrictions.ilike("principal", search, MatchMode.ANYWHERE),
					Restrictions.ilike("realName", search, MatchMode.ANYWHERE)));
		}
		if (q != null) {
			List<Account> results = accountService.find(Restrictions.or(
					Restrictions.ilike("principal", q, MatchMode.ANYWHERE),
					Restrictions.ilike("realName", q, MatchMode.ANYWHERE)));

			ResponseDataFilter.filter(results);

			return new Response(results);
		}

		accountService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤不必有的数据
		for (Account account : page.getResult()) {
			ResponseDataFilter.filter(account);
		}

		return new Response(page);
	}

	@RequestMapping(value = "/system/accounts", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Account account) {
		// 业务验证
		// 用户名唯一性
		if (accountService.isExist(account.getPrincipal())) {
			return new Response(ValidationUtils.renderResultMap("Unique.account.principal", messageSource));
		}

		boolean result = accountService.save(account);
		return response(account, result, "save");
	}

	@RequestMapping(value = "/system/accounts/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Account account, @PathVariable long id) {
		// 业务验证
		Account accountInDB = accountService.get(id);
		// 更新时如果用户名更改，则需要验证用户名唯一性
		if (!accountInDB.getPrincipal().equals(account.getPrincipal())) {
			if (accountService.isExist(account.getPrincipal())) {
				return new Response(ValidationUtils.renderResultMap("Unique.account.principal", messageSource));
			}
		}

		boolean result = accountService.update(accountInDB, account, account.getGroupEntity().getId(), account
				.getRoleEntity().getId());
		return response(accountInDB, result, "update");
	}

	/**
	 * 提供给前端的验证。
	 * <p>
	 * 目前支持的验证：
	 * <li>用户名是否已存在</li>
	 * <li>密码是否正确</li>
	 * 
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/system/accounts/validate", method = RequestMethod.GET)
	@ResponseBody
	public Response valid(HttpSession httpSession, String principal, String oldPassword, String principal1,
			String principal2, String credential1, String credential2) {
		if (!Strings.isNullOrEmpty(principal)) {
			if (accountService.isExist(principal)) {
				Map<String, String> result = ValidationUtils.renderResultMap("Unique.account.principal", messageSource);
				return new Response(result);
			} else {
				return new Response(true);
			}
		}
		if (!Strings.isNullOrEmpty(oldPassword)) {
			String credenial = ((Account) WebUtils.getAccount(httpSession)).getCredential();
			return new Response(credenial.equals(DigestUtils.sha256Hex(oldPassword)));
		}
		return new Response(false);
	}

	/**
	 * 根据结果返回并记录日志。
	 * 
	 * @param account
	 *            用户，用于日志记录
	 * @param result
	 *            处理结果
	 * @param messageKey
	 *            日志消息源中的key
	 * @return 兼容前端的结果
	 */
	private Response response(Account account, boolean result, String messageKey) {
		String[] args = { account.getPrincipal(), account.getRealName(), account.getRoleEntity().getName(),
				account.getGroupEntity().getName() };
		if (result) {
			logEntityServiceImpl.warn(messageSource.getMessage("account." + messageKey + ".success", args,
					Locale.getDefault()));
			ResponseDataFilter.filter(account);
			return new Response(account);
		} else {
			logEntityServiceImpl.severe(messageSource.getMessage("account." + messageKey + ".failure", args,
					Locale.getDefault()));
			return new Response(false);
		}
	}
}