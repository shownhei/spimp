/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.spimp.ercs.entity.ResponseTeam;
import cn.ccrise.spimp.ercs.service.ResponseTeamService;

/**
 * 应急救援系统基础数据初始化服务。
 * 
 */
@Service
public class InitErcsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ResponseTeamService responseTeamService;
	@Autowired
	protected ResourceEntityServiceImpl resourceEntityServiceImpl;

	/**
	 * 应急救援指挥 三级菜单
	 */
	public void initFourthLevelOperate() {
		// 执行救援
		String performRescue = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/perform-rescue", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("任务查看", "/ercs/perform-rescue/task-view", performRescue, "", 1);
		resourceEntityServiceImpl.saveMenuResource("任务管理", "/ercs/perform-rescue/task-manage", performRescue, "", 2);

		// 应急资源管理
		int i = 1;
		String material = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/material-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("应急资源", "/ercs/material-index/material", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("物资使用记录", "/ercs/material-index/use-record", material, "", i++);
		resourceEntityServiceImpl.saveMenuResource("医护器材", "/ercs/material-index/medical-supply", material, "", i++);

		// 应急预案管理
		i = 1;
		String plan = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/plan-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("应急预案", "/ercs/plan-index/plan", plan, "", i++);
		resourceEntityServiceImpl.saveMenuResource("救援措施模板", "/ercs/plan-index/template", plan, "", i++);

		// 应急救援人员
		i = 1;
		String staff = resourceEntityServiceImpl.getDefaultIdentifier("/ercs/staff-index", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("救援人员", "/ercs/staff-index/staff", staff, "", i++);
		resourceEntityServiceImpl.saveMenuResource("救援专家", "/ercs/staff-index/specia-list", staff, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急保障机构", "/ercs/staff-index/safegard-org", staff, "", i++);

	}

	/**
	 * 应急救援指挥 --基础数据
	 */
	public void initThirdLevelGroup() {
		ResponseTeam root = new ResponseTeam();
		root.setParentId(ResponseTeam.ROOT_ID);
		root.setTeamName("王庄煤业");
		responseTeamService.save(root);
		String teamName[] = { "指挥部", "抢险救援", "医疗救护", "技术专家", "通信信息", "物资装备", "交通运输", "后勤服务", "财力保障", "治安保卫", "善后处置" };
		ResponseTeam team = null;
		for (String element : teamName) {
			team = new ResponseTeam();
			team.setParentId(root.getId());
			team.setTeamName(element);
			responseTeamService.save(team);
		}
	}

	/**
	 * 应急救援指挥 二级菜单
	 */
	public void initThirdLevelMenu() {
		String ercs = resourceEntityServiceImpl.getDefaultIdentifier("/ercs", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("应急报警", "/ercs/alarm", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("执行救援", "/ercs/perform-rescue", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急资源", "/ercs/material-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急预案", "/ercs/plan-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急救援人员", "/ercs/staff-index", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("避难场所", "/ercs/place", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("现场处置方案", "/ercs/scheme", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急法规", "/ercs/law", ercs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("应急机构", "/ercs/organization", ercs, "", i++);
	}
}
