/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.GroupEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * 组控制器。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class GroupController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private GroupEntityServiceImpl groupEntityServiceImpl;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/system/groups/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		GroupEntity groupEntity = groupEntityServiceImpl.get(id);
		return response(groupEntity, groupEntityServiceImpl.associatedDelete(groupEntity), "delete");
	}

	@RequestMapping(value = "/system/groups/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id, String[] label, boolean filter, HttpSession httpSession) {
		if (filter) {
			GroupEntity groupEntity = groupEntityServiceImpl.get(WebUtils.getGroup(httpSession).getId());
			return new Response(groupEntityServiceImpl.filterByQueryLabel(groupEntity.getId(), label));
		} else {
			return new Response(groupEntityServiceImpl.filterByQueryLabel(id, label));
		}
	}

	@RequestMapping(value = "/system/groups/{id}/accounts", method = RequestMethod.GET)
	@ResponseBody
	public Response getGroupAccounts(@PathVariable long id, String type) {
		GroupEntity groupEntity = groupEntityServiceImpl.get(id);

		List<Account> result = accountService.findBy("groupEntity", groupEntity);

		if ("count".equals(type)) {
			return new Response(result.size());
		}

		ResponseDataFilter.filter(result);
		return new Response(result);
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/system/groups", method = RequestMethod.GET)
	@ResponseBody
	public Response list(String[] label, String q) {
		// 用于查询
		if (!Strings.isNullOrEmpty(q)) {
			List<GroupEntity> queryResults = groupEntityServiceImpl.find(Restrictions.ilike("name", q,
					MatchMode.ANYWHERE));

			// 过滤不必要的数据
			for (GroupEntity groupEntity : queryResults) {
				ResponseDataFilter.filter(groupEntity);
			}

			return new Response(queryResults);
		}

		if (label == null) {
			return new Response(groupEntityServiceImpl.getAll());
		} else {
			List<GroupEntity> result = Lists.newArrayList();

			result.addAll(groupEntityServiceImpl.getByQueryLabel(label));

			// 过滤不必要的数据
			for (GroupEntity groupEntity : result) {
				ResponseDataFilter.filter(groupEntity);
			}

			return new Response(result);
		}
	}

	@RequestMapping(value = "/system/groups", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody GroupEntity groupEntity) {
		// 业务验证
		if (groupEntityServiceImpl.findUniqueBy("number", groupEntity.getNumber()) != null) {
			Map<String, String> result = ValidationUtils.renderResultMap("Unique.groupEntity.number", messageSource);
			return new Response(result);
		}

		// 保存
		boolean result = groupEntityServiceImpl.associatedSave(groupEntity);

		// 返回
		return response(groupEntity, result, "save");
	}

	@RequestMapping(value = "/system/groups/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody GroupEntity groupEntity, @PathVariable long id) {
		// 业务验证
		GroupEntity groupInDB = groupEntityServiceImpl.get(id);
		// 更新时如果机构编号更改，则需要验证机构编号唯一性
		if (!groupInDB.getNumber().equals(groupEntity.getNumber())) {
			if (groupEntityServiceImpl.findUniqueBy("number", groupEntity.getNumber()) != null) {
				Map<String, String> result = ValidationUtils
						.renderResultMap("Unique.groupEntity.number", messageSource);
				return new Response(result);
			}
		}

		// 更新
		boolean result = groupEntityServiceImpl.associatedUpdate(groupEntity);

		return response(groupEntity, result, "update");
	}

	/**
	 * 根据结果返回并记录日志。
	 * 
	 * @param result
	 *            处理结果
	 * @param messageKey
	 *            日志消息源中的key
	 * @return 兼容前端的结果
	 */
	private Response response(GroupEntity groupEntity, boolean result, String messageKey) {
		String[] args = { groupEntity.getName() };
		if (result) {
			logEntityServiceImpl.info(messageSource.getMessage("group." + messageKey + ".success", args,
					Locale.getDefault()));
			ResponseDataFilter.filter(groupEntity);
			return new Response(groupEntity);
		} else {
			logEntityServiceImpl.warn(messageSource.getMessage("group." + messageKey + ".failure", args,
					Locale.getDefault()));
			return new Response(false);
		}
	}
}
