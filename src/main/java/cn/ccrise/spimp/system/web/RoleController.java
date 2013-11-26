/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.ArrayUtils;
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

import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.ikjp.core.security.entity.RoleEntity;
import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.ikjp.core.security.service.impl.RoleEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.collect.Lists;

/**
 * Role Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class RoleController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoleEntityServiceImpl roleEntityServiceImpl;
	@Autowired
	private ResourceEntityServiceImpl resourceEntityServiceImpl;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/system/roles/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		RoleEntity roleEntity = roleEntityServiceImpl.get(id);
		return response(roleEntity, roleEntityServiceImpl.delete(roleEntity), "delete");
	}

	@RequestMapping(value = "/system/roles/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(roleEntityServiceImpl.get(id));
	}

	/**
	 * 获取角色账户。
	 * 
	 * @param id
	 *            角色id
	 * @return 角色包含账户
	 */
	@RequestMapping(value = "/system/roles/{id}/accounts", method = RequestMethod.GET)
	@ResponseBody
	public Response getAccounts(Page<Account> page, @PathVariable long id) {
		RoleEntity role = roleEntityServiceImpl.get(id);
		Page<Account> newPage = accountService.getPage(page, Restrictions.eq("roleEntity", role));

		ResponseDataFilter.filter(newPage.getResult());

		return new Response(newPage);
	}

	/**
	 * 获取角色资源。默认为树形。
	 * 
	 * @param id
	 *            角色id
	 * @param type
	 *            显示类型，tree为树形，list为列表
	 * @return 角色资源树
	 */
	@RequestMapping(value = "/system/roles/{id}/resources", method = RequestMethod.GET)
	@ResponseBody
	public Response getResources(@PathVariable long id, String type) {
		if ("list".equals(type)) {
			// 获取当前角色拥有的资源列表
			List<ResourceEntity> resourcesInRole = roleEntityServiceImpl.get(id).getResourceEntities();

			// 过滤
			for (ResourceEntity resourceEntity : resourcesInRole) {
				ResponseDataFilter.filter(resourceEntity);
			}
			return new Response(resourcesInRole);
		}

		// 获取完整资源树
		ResourceEntity root = resourceEntityServiceImpl.getRoot();

		return new Response(root);
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/system/roles", method = RequestMethod.GET)
	@ResponseBody
	public Response list() {
		// Stopwatch stopwatch = new Stopwatch().start();
		List<RoleEntity> roles = roleEntityServiceImpl.getAll();

		// 过滤不必要的数据
		for (RoleEntity roleEntity : roles) {
			roleEntity.setResourceEntities(null);
		}

		// logger.debug(stopwatch.stop().toString());
		return new Response(roles);
	}

	@RequestMapping(value = "/system/roles", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody RoleEntity roleEntity) {
		// 业务验证
		if (roleEntityServiceImpl.isExist(roleEntity.getName())) {
			Map<String, String> result = ValidationUtils.renderResultMap("Unique.roleEntity.name", messageSource);
			return new Response(result);
		}

		// 保存
		ResourceEntity root = resourceEntityServiceImpl.getRoot();
		List<ResourceEntity> resourceEntities = Lists.newArrayList();
		resourceEntities.add(root);
		roleEntity.setResourceEntities(resourceEntities);
		boolean result = roleEntityServiceImpl.save(roleEntity);

		return response(roleEntity, result, "save");
	}

	/**
	 * 保存角色资源。
	 * 
	 * @param id
	 *            角色id
	 * @param resourceIds
	 *            资源ids
	 * @return
	 */
	@RequestMapping(value = "/system/roles/{id}/resources", method = RequestMethod.PUT)
	@ResponseBody
	public Response saveResources(@PathVariable long id, long[] resourceIds) {
		RoleEntity role = roleEntityServiceImpl.get(id);
		role.getResourceEntities().clear();

		// 判断是否包含根资源，若没有自动添加
		ResourceEntity root = resourceEntityServiceImpl.getRoot();
		if (!ArrayUtils.contains(resourceIds, root.getId())) {
			role.getResourceEntities().add(root);
		}

		for (long resourceId : resourceIds) {
			role.getResourceEntities().add(resourceEntityServiceImpl.get(resourceId));
		}
		roleEntityServiceImpl.save(role);

		// 过滤返回数据
		role.setResourceEntities(null);
		return new Response(role);
	}

	@RequestMapping(value = "/system/roles/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody RoleEntity roleEntity, @PathVariable long id) {
		// 业务验证
		RoleEntity roleInDB = roleEntityServiceImpl.get(id);
		// 更新时如果角色名称更改，则需要验证角色名称唯一性
		if (!roleInDB.getName().equals(roleEntity.getName())) {
			if (roleEntityServiceImpl.isExist(roleEntity.getName())) {
				Map<String, String> result = ValidationUtils.renderResultMap("Unique.roleEntity.name", messageSource);
				return new Response(result);
			}
		}

		// 更新
		roleEntity.setResourceEntities(roleInDB.getResourceEntities());
		boolean result = roleEntityServiceImpl.merge(roleEntity);

		// 返回
		return response(roleInDB, result, "update");
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
	private Response response(RoleEntity roleEntity, boolean result, String messageKey) {
		String[] args = { roleEntity.getName() };
		if (result) {
			logEntityServiceImpl.info(messageSource.getMessage("role." + messageKey + ".success", args,
					Locale.getDefault()));
			roleEntity.setResourceEntities(null);
			return new Response(roleEntity);
		} else {
			logEntityServiceImpl.warn(messageSource.getMessage("role." + messageKey + ".failure", args,
					Locale.getDefault()));
			return new Response(false);
		}
	}
}