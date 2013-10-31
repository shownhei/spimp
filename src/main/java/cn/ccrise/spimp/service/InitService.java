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
import cn.ccrise.spimp.ercs.entity.ResponseTeam;
import cn.ccrise.spimp.ercs.service.ResponseTeamService;

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
	private ResponseTeamService responseTeamService;

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
		
		// 执行救援
		String performRescue = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/perform-rescue", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("任务查看", "/ercs/task-view", performRescue, "", 1);
		resourceEntityServiceImpl.saveMenuResource("任务管理", "/ercs/task-manage", performRescue, "", 2);
		
		// 应急资源管理
		int i = 1;
		String material = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/material-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("应急资源", "/ercs/material", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("物资使用记录", "/ercs/use-record", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("医护器材", "/ercs/medical-supply", material, "", i++);
		
		// 应急预案管理
		i = 1;
		String plan = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/plan-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("应急预案", "/ercs/plan", plan, "", i++);
		resourceEntityServiceImpl.saveMenuResource("救援措施模板", "/ercs/template", plan, "", i++);
		
		// 应急救援人员
		String staff = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/staff-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("救援人员", "/ercs/staff", staff, "", i++);
		resourceEntityServiceImpl.saveMenuResource("救援专家", "/ercs/specia-list", staff, "", i++);
		
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
		ResponseTeam root = new ResponseTeam();
		root.setParentId(ResponseTeam.ROOT_ID);
		root.setTeamName("王庄煤业");
		responseTeamService.save(root);
		String teamName[] = { "指挥部", "抢险救援", "医疗救护", "技术专家", "通信信息", "物资装备", "交通运输", "后勤服务", "财力保障", "治安保卫", "善后处置" };
		ResponseTeam team = null;
		for (int i = 0; i < teamName.length; i++) {
			team = new ResponseTeam();
			team.setParentId(root.getId());
			team.setTeamName(teamName[i]);
			responseTeamService.save(team);
		}
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
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("应急报警", "/ercs/alarm", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急指示", "/ercs/indicate-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急处置", "/ercs/deal", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("执行救援", "/ercs/perform-rescue", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急资源", "/ercs/material-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急预案", "/ercs/plan-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急救援人员", "/ercs/staff-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("避难场所", "/ercs/place", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("现场处置方案", "/ercs/scheme", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急法规", "/ercs/law", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急机构", "/ercs/organization", ercs, "", i++);

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
