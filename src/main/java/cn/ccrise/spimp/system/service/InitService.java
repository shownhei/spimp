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
		int i = 1;
		String quality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/quality", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("一通三防专业", "/spmi/quality/wind", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("地测防治水专业", "/spmi/quality/water", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("采煤专业", "/spmi/quality/mining", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/quality/tunnelling", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机电专业", "/spmi/quality/electro", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运输专业", "/spmi/quality/transportation", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全管理专业", "/spmi/quality/safety", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("职业健康专业", "/spmi/quality/health", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急救援专业", "/spmi/quality/rescue", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("信息调度专业", "/spmi/quality/dispatch", quality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("地面设施专业", "/spmi/quality/facilities", quality, "", i++);

		// 质量标准化管理
		i = 1;
		String document = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/document", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("文档综合查询", "/spmi/document/query", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("调度室录入", "/spmi/document/schedule", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全科录入", "/spmi/document/safe", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机电科录入", "/spmi/document/machine", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("通风科录入", "/spmi/document/wind", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("生产技术科录入", "/spmi/document/produce", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("防治水科录入", "/spmi/document/water", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("综掘队录入", "/spmi/document/dig", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("综采队录入", "/spmi/document/exploit", document, "", i++);
		resourceEntityServiceImpl.saveMenuResource("开拓队录入", "/spmi/document/develop", document, "", i++);
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
		int i = 1;
		String rootMenuIdentifier = resourceEntityServiceImpl.getRootMenu().getIdentifier();
		resourceEntityServiceImpl.saveMenuResource("安全生产管理", "/spmi", rootMenuIdentifier, "icon-wrench", i++);
		resourceEntityServiceImpl.saveMenuResource("应急救援指挥", "/ercs", rootMenuIdentifier, "icon-medkit", i++);
		resourceEntityServiceImpl.saveMenuResource("机电管理", "/electr", rootMenuIdentifier, "icon-bolt", i++);
		resourceEntityServiceImpl.saveMenuResource("系统管理", "/system", rootMenuIdentifier, "icon-cogs", i++);
	}

	@Override
	public void initThirdLevelGroup() {
		initErcsService.initThirdLevelGroup();
	}

	@Override
	public void initThirdLevelMenu() {
		// 安全生产管理
		int i = 1;
		String spmi = resourceEntityServiceImpl.getDefaultIdentifier("/spmi", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("质量标准化评分", "/spmi/quality", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化管理", "/spmi/document", spmi, "", i++);

		// 应急救援管理
		initErcsService.initThirdLevelMenu();

		// 机电管理
		initCarService.initThirdLevelMenu();

		// 系统管理
		i = 1;
		String system = resourceEntityServiceImpl.getDefaultIdentifier("/system", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("用户管理", "/system/account", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("角色管理", "/system/role", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机构管理", "/system/group", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("人员管理", "/system/staff", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("菜单管理", "/system/resource", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("字典管理", "/system/dictionary", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("日志查询", "/system/log", system, "", i++);
	}
}
