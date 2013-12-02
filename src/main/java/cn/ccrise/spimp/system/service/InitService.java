/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.DataInitAbstractService;
import cn.ccrise.spimp.system.entity.Account;

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
	@Autowired
	private InitErcsService initErcsService;
	/**
	 * 胶轮车管理
	 */
	@Autowired
	private InitCarService initCarService;

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
		initCarService.initCustomData();
	}

	@Override
	public void initCustomSystemResource() {
	}

	@Override
	public void initFourthLevelOperate() {
		initErcsService.initFourthLevelOperate();
		initCarService.initFourthLevelOperate();
		// 质量标准化评分
		String quality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/quality", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("一通三防专业", "/spmi/quality/wind", quality, "", 1);
		resourceEntityServiceImpl.saveMenuResource("地测防治水专业", "/spmi/quality/water", quality, "", 2);
		resourceEntityServiceImpl.saveMenuResource("采煤专业", "/spmi/quality/mining", quality, "", 3);
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/quality/tunnelling", quality, "", 4);
		resourceEntityServiceImpl.saveMenuResource("机电专业", "/spmi/quality/electro", quality, "", 5);
		resourceEntityServiceImpl.saveMenuResource("运输专业", "/spmi/quality/transportation", quality, "", 6);
		resourceEntityServiceImpl.saveMenuResource("安全管理专业", "/spmi/quality/safety", quality, "", 7);
		resourceEntityServiceImpl.saveMenuResource("职业健康专业", "/spmi/quality/health", quality, "", 8);
		resourceEntityServiceImpl.saveMenuResource("应急救援专业", "/spmi/quality/rescue", quality, "", 9);
		resourceEntityServiceImpl.saveMenuResource("信息调度专业", "/spmi/quality/dispatch", quality, "", 10);
		resourceEntityServiceImpl.saveMenuResource("地面设施专业", "/spmi/quality/facilities", quality, "", 11);

		// 质量标准化管理
		String document = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/document", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("文档综合查询", "/spmi/document/query", document, "", 1);
		resourceEntityServiceImpl.saveMenuResource("调度室录入", "/spmi/document/schedule", document, "", 2);
		resourceEntityServiceImpl.saveMenuResource("安全科录入", "/spmi/document/safe", document, "", 3);
		resourceEntityServiceImpl.saveMenuResource("机电科录入", "/spmi/document/machine", document, "", 4);
		resourceEntityServiceImpl.saveMenuResource("通风科录入", "/spmi/document/wind", document, "", 5);
		resourceEntityServiceImpl.saveMenuResource("生产技术科录入", "/spmi/document/produce", document, "", 6);
		resourceEntityServiceImpl.saveMenuResource("防治水科录入", "/spmi/document/water", document, "", 7);
		resourceEntityServiceImpl.saveMenuResource("综掘队录入", "/spmi/document/dig", document, "", 8);
		resourceEntityServiceImpl.saveMenuResource("综采队录入", "/spmi/document/exploit", document, "", 9);
		resourceEntityServiceImpl.saveMenuResource("开拓队录入", "/spmi/document/develop", document, "", 10);

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
		resourceEntityServiceImpl.saveMenuResource("胶轮车管理", "/car", rootMenuIdentifier, "icon-medkit", 3);
		resourceEntityServiceImpl.saveMenuResource("系统管理", "/system", rootMenuIdentifier, "icon-cogs", 4);
	}

	@Override
	public void initThirdLevelGroup() {
		initErcsService.initThirdLevelGroup();
	}

	@Override
	public void initThirdLevelMenu() {
		// 安全生产管理
		String spmi = resourceEntityServiceImpl.getDefaultIdentifier("/spmi", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("质量标准化评分", "/spmi/quality", spmi, "", 1);
		resourceEntityServiceImpl.saveMenuResource("质量标准化管理", "/spmi/document", spmi, "", 6);
		// 应急救援管理
		initErcsService.initThirdLevelMenu();
		// 胶轮车管理
		initCarService.initThirdLevelMenu();
		// 系统管理
		String system = resourceEntityServiceImpl.getDefaultIdentifier("/system", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("用户管理", "/system/account", system, "", 1);
		resourceEntityServiceImpl.saveMenuResource("角色管理", "/system/role", system, "", 2);
		resourceEntityServiceImpl.saveMenuResource("机构管理", "/system/group", system, "", 3);
		resourceEntityServiceImpl.saveMenuResource("菜单管理", "/system/resource", system, "", 4);
		resourceEntityServiceImpl.saveMenuResource("字典管理", "/system/dictionary", system, "", 5);
		resourceEntityServiceImpl.saveMenuResource("日志查询", "/system/log", system, "", 6);
	}
}
