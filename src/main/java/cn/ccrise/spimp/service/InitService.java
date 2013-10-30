/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.DataInitAbstractService;
import cn.ccrise.spimp.entity.Account;

/**
 * 系统基础数据初始化服务。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Service
public class InitService extends DataInitAbstractService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountService accountService;

	@Override
	public void initAdmin() {
		Account admin = new Account();
		admin.setRealName("超级管理员");
		admin.setPrincipal(ADMIN_DEFAULT_PRINCIPAL);
		admin.setCredential(ADMIN_DEFAULT_CREDENTIAL);
		admin.setRoleEntity(roleEntityServiceImpl.findUniqueBy("name", ROOT_ROLE_NAME));
		admin.setGroupEntity(groupEntityServiceImpl.getRoot());
		admin.setLocked(false);

		accountService.save(admin);
	}

	@Override
	public void initCustomData() {
	}

	@Override
	public void initCustomSystemResource() {
	}

	@Override
	public void initFourthLevelOperate() {
		// 应急指示
		String indicate = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/indicate", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("二维展示", "/ercs/indicate/2d", indicate, "", 1);
		resourceEntityServiceImpl.saveMenuResource("三维展示", "/ercs/indicate/3d", indicate, "", 2);

		// 机电设备
		String electro = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/electro", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("设备检修跟踪", "/spmi/electro/repair", electro, "", 1);
		resourceEntityServiceImpl.saveMenuResource("设备参数查询", "/spmi/electro/query", electro, "", 2);

		// 重点工作和领导指示
		String instruction = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/instruction", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("重点工作", "/spmi/instruction/focus", instruction, "", 1);
		resourceEntityServiceImpl.saveMenuResource("领导指示", "/spmi/instruction/instruction", instruction, "", 2);

		// 调度管理
		String schedule = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/schedule", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("矿井原煤产量", "/spmi/schedule/output", schedule, "", 1);
		resourceEntityServiceImpl.saveMenuResource("矿井掘进进尺", "/spmi/schedule/dig", schedule, "", 2);
		resourceEntityServiceImpl.saveMenuResource("生产准备情况", "/spmi/schedule/prepare", schedule, "", 3);

		// 质量标准化
		String quality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/quality", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("调度专业", "/spmi/quality/dispatch", quality, "", 1);
	}

	@Override
	public void initSecondLevelGroup() {
		GroupEntity rootGroupEntity = groupEntityServiceImpl.getRoot();

		GroupEntity g1 = new GroupEntity();
		g1.setName("王庄煤业");
		g1.setQueryLabel("mine");
		g1.setCategory("煤矿");
		g1.setParentId(rootGroupEntity.getId());
		g1.setTopLevel(false);
		g1.setNumber("wangzhuang");
		groupEntityServiceImpl.save(g1);

		rootGroupEntity.getGroupEntities().add(g1);
		groupEntityServiceImpl.update(rootGroupEntity);
	}

	@Override
	public void initSecondLevelMenu() {
		String rootMenuIdentifier = resourceEntityServiceImpl.getRootMenu().getIdentifier();
		resourceEntityServiceImpl.saveMenuResource("安全生产管理", "/spmi", rootMenuIdentifier, "icon-wrench", 1);
		resourceEntityServiceImpl.saveMenuResource("应急救援指挥", "/ercs", rootMenuIdentifier, "icon-medkit", 2);
		resourceEntityServiceImpl.saveMenuResource("系统管理", "/system", rootMenuIdentifier, "icon-cogs", 3);
	}

	@Override
	public void initThirdLevelGroup() {
	}

	@Override
	public void initThirdLevelMenu() {
		// 安全生产管理
		String spmi = resourceEntityServiceImpl.getDefaultIdentifier("/spmi", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/quality", spmi, "", 1);
		resourceEntityServiceImpl.saveMenuResource("机电设备", "/spmi/electro", spmi, "", 2);
		resourceEntityServiceImpl.saveMenuResource("调度管理", "/spmi/schedule", spmi, "", 3);
		resourceEntityServiceImpl.saveMenuResource("重点工作和领导指示", "/spmi/instruction", spmi, "", 4);

		// 应急救援指挥
		String ercs = resourceEntityServiceImpl.getDefaultIdentifier("/ercs", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("应急报警", "/ercs/alarm", ercs, "", 1);
		resourceEntityServiceImpl.saveMenuResource("应急指示", "/ercs/indicate", ercs, "", 2);
		resourceEntityServiceImpl.saveMenuResource("应急资源", "/ercs/material", ercs, "", 3);
		resourceEntityServiceImpl.saveMenuResource("应急预案", "/ercs/plan", ercs, "", 4);
		resourceEntityServiceImpl.saveMenuResource("应急救援人员", "/ercs/staff", ercs, "", 5);
		resourceEntityServiceImpl.saveMenuResource("避难场所", "/ercs/place", ercs, "", 6);
		resourceEntityServiceImpl.saveMenuResource("应急法规", "/ercs/law", ercs, "", 7);
		resourceEntityServiceImpl.saveMenuResource("现场处置方案", "/ercs/scheme", ercs, "", 8);
		resourceEntityServiceImpl.saveMenuResource("应急机构", "/ercs/organization", ercs, "", 9);
		resourceEntityServiceImpl.saveMenuResource("三维应急演练", "/ercs/3d", ercs, "", 10);
		resourceEntityServiceImpl.saveMenuResource("字典管理", "/ercs/dictionary", ercs, "", 11);
		resourceEntityServiceImpl.saveMenuResource("物资使用记录", "/ercs/use-record", ercs, "", 12);
		resourceEntityServiceImpl.saveMenuResource("医护器材", "/ercs/medical-supply", ercs, "", 13);
		resourceEntityServiceImpl.saveMenuResource("救援专家", "/ercs/specia-list", ercs, "", 14);

		// 系统管理
		String system = resourceEntityServiceImpl.getDefaultIdentifier("/system", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("用户管理", "/system/account", system, "", 1);
		resourceEntityServiceImpl.saveMenuResource("角色管理", "/system/role", system, "", 2);
		resourceEntityServiceImpl.saveMenuResource("机构管理", "/system/group", system, "", 3);
		resourceEntityServiceImpl.saveMenuResource("菜单管理", "/system/resource", system, "", 4);
		resourceEntityServiceImpl.saveMenuResource("日志查询", "/system/log", system, "", 5);
	}
}
