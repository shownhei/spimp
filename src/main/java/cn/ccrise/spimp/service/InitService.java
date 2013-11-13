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
	@Autowired
	private InitErcsService initErcsService;

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
		initErcsService.initFourthLevelOperate();
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
		resourceEntityServiceImpl.saveMenuResource("班出勤情况", "/spmi/schedule/attendance", schedule, "", 4);
		resourceEntityServiceImpl.saveMenuResource("矿值班情况", "/spmi/schedule/work", schedule, "", 5);
		resourceEntityServiceImpl.saveMenuResource("基层单位干部跟班情况", "/spmi/schedule/circumstance", schedule, "", 6);
		resourceEntityServiceImpl.saveMenuResource("调度记录", "/spmi/schedule/record", schedule, "", 7);
		resourceEntityServiceImpl.saveMenuResource("安全生产三汇报", "/spmi/schedule/report", schedule, "", 8);
		resourceEntityServiceImpl.saveMenuResource("煤炭外运情况", "/spmi/schedule/transport", schedule, "", 9);
		resourceEntityServiceImpl.saveMenuResource("队组管理", "/spmi/schedule/team", schedule, "", 10);

		// 质量标准化
		String quality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/quality", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("通风专业", "/spmi/quality/wind", quality, "", 1);
		resourceEntityServiceImpl.saveMenuResource("地测防治水专业", "/spmi/quality/water", quality, "", 2);
		resourceEntityServiceImpl.saveMenuResource("采煤专业", "/spmi/quality/mining", quality, "", 3);
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/quality/tunnelling", quality, "", 4);
		resourceEntityServiceImpl.saveMenuResource("机电专业", "/spmi/quality/electro", quality, "", 5);
		resourceEntityServiceImpl.saveMenuResource("运输专业", "/spmi/quality/transportation", quality, "", 6);
		resourceEntityServiceImpl.saveMenuResource("安全管理专业", "/spmi/quality/safety", quality, "", 7);
		resourceEntityServiceImpl.saveMenuResource("职业卫生专业", "/spmi/quality/health", quality, "", 8);
		resourceEntityServiceImpl.saveMenuResource("应急救援专业", "/spmi/quality/rescue", quality, "", 9);
		resourceEntityServiceImpl.saveMenuResource("调度专业", "/spmi/quality/dispatch", quality, "", 10);
		resourceEntityServiceImpl.saveMenuResource("地面设施专业", "/spmi/quality/facilities", quality, "", 11);

		// 文档管理
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
		resourceEntityServiceImpl.saveMenuResource("系统管理", "/system", rootMenuIdentifier, "icon-cogs", 3);
	}

	@Override
	public void initThirdLevelGroup() {
		initErcsService.initThirdLevelGroup();
	}

	@Override
	public void initThirdLevelMenu() {
		// 安全生产管理
		String spmi = resourceEntityServiceImpl.getDefaultIdentifier("/spmi", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/quality", spmi, "", 1);
		resourceEntityServiceImpl.saveMenuResource("机电设备", "/spmi/electro", spmi, "", 2);
		resourceEntityServiceImpl.saveMenuResource("调度管理", "/spmi/schedule", spmi, "", 3);
		resourceEntityServiceImpl.saveMenuResource("重点工作和领导指示", "/spmi/instruction", spmi, "", 4);
		resourceEntityServiceImpl.saveMenuResource("文档管理", "/spmi/document", spmi, "", 5);

		initErcsService.initThirdLevelMenu();
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
