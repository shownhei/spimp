/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.service;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.DataInitAbstractService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.entity.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

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
	private GroupService groupService;
	@Autowired
	private InitErcsService initErcsService;
	@Autowired
	private InitElectrService initElectrService;
	@Autowired
	private DicitonaryTypeService dicitonaryTypeService;
	@Autowired
	private DictionaryService dictionaryService;

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
		// 应急救援
		dicitonaryTypeService.save("应急救援", "plan_type", "应急预案种类");
		dicitonaryTypeService.save("应急救援", "personal_category", "人员类别");
		dicitonaryTypeService.save("应急救援", "expertise_area", "专业领域");
		dicitonaryTypeService.save("应急救援", "accident_category", "事故类别");
		dicitonaryTypeService.save("应急救援", "accident_level", "事故严重程度");
		dicitonaryTypeService.save("应急救援", "response_level", "事故响应级别");
		dicitonaryTypeService.save("应急救援", "refuge_type", "避险场所种类");
		dicitonaryTypeService.save("应急救援", "resource_type", "应急资源种类");
		dicitonaryTypeService.save("应急救援", "education_level", "文化程度");
		dicitonaryTypeService.save("应急救援", "organization_type", "应急保障机构类型");

		// 机电管理
		dicitonaryTypeService.save("机电管理", "summary_type", "每月总结-总结类型");
		dicitonaryTypeService.save("机电管理", "regulationfile_type", "制度文件类型");
		dicitonaryTypeService.save("机电管理", "car_carCategory", "车辆分类");
		dicitonaryTypeService.save("机电管理", "equipment_maintenance_project", "设备检修计划项目");
		dicitonaryTypeService.save("机电管理", "reminder_setting_project", "定期检修设置-项目");

		// 安全生产管理
		dicitonaryTypeService.save("安全生产管理", "schedule_coal_type", "煤种");
		dicitonaryTypeService.save("安全生产管理", "schedule_coal_series", "煤系");
		dicitonaryTypeService.save("安全生产管理", "schedule_duty", "班次");
		dicitonaryTypeService.save("安全生产管理", "schedule_team_type", "队组类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_injury_type", "受伤类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_working_face", "工作面");
		dicitonaryTypeService.save("安全生产管理", "schedule_exploit_type", "开采方式");
		dicitonaryTypeService.save("安全生产管理", "schedule_tunnel_type", "巷道类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_working_place", "工作地点");
		dicitonaryTypeService.save("安全生产管理", "schedule_meeting_type", "会议类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_gas_emissions_type", "瓦斯排放类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_hidden_type", "隐患类型");
		dicitonaryTypeService.save("安全生产管理", "schedule_wellheads", "井口");
		dicitonaryTypeService.save("安全生产管理", "schedule_alarm_type", "报警类型");
		dicitonaryTypeService.save("安全生产管理", "document_project_type", "工程分类");

		String carCategory[] = { "人车", "客货车", "洒水车", "两驱料车", "四驱料车", "铲运车", "支架搬运车", "其他未录入车型" };
		for (String element : carCategory) {
            Dictionary dic = new Dictionary();
            dic.setTypeCode("car_carCategory");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		initElectrService.initCustomData();
	}

	@Override
	public void initCustomSystemResource() {
	}

	@Override
	public void initFourthLevelOperate() {
		// 安全生产管理
		// 质量标准化评分
		String quality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/quality", HttpMethod.GET);
		int i = 1;
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
		String document = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/document", HttpMethod.GET);
		i = 1;
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

		// 日常工作
		String daily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("工作安排", "/spmi/daily/plan", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/daily/reform", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("奖惩记录", "/spmi/daily/reward", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("图片管理", "/spmi/daily/picture", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("每月总结", "/spmi/daily/summary", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("产量统计", "/spmi/daily/output", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("生产日报表", "/spmi/daily/report", daily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("事故记录", "/spmi/daily/accident", daily, "", i++);
        resourceEntityServiceImpl.saveMenuResource("培训计划", "/spmi/daily/training", daily, "", i++);

        // 应急救援指挥
        initErcsService.initFourthLevelOperate();

		// 机电管理
		initElectrService.initFourthLevelOperate();
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
		g1.setNumber("WZMY");
		groupEntityServiceImpl.save(g1);

		rootGroupEntity.getGroupEntities().add(g1);
		groupEntityServiceImpl.save(rootGroupEntity);

		// 科室和队组
		groupService.save("调度室", "DDS", "office", "科室", g1);
		groupService.save("安全科", "AQK", "office", "科室", g1);
		groupService.save("机电科", "JDK", "office", "科室", g1);
		groupService.save("通风科", "TFK", "office", "科室", g1);
		groupService.save("生产技术科", "SJK", "office", "科室", g1);
		groupService.save("防治水科", "FZS", "office", "科室", g1);
		groupService.save("综采队", "ZCD", "team", "队组", g1);
		groupService.save("机电机运队", "JDD", "team", "队组", g1);
		groupService.save("掘进一队", "JJY", "team", "队组", g1);
		groupService.save("掘进二队", "JJE", "team", "队组", g1);
		groupService.save("开拓一队", "KTY", "team", "队组", g1);
		groupService.save("开拓二队", "KYE", "team", "队组", g1);
	}

	@Override
	public void initSecondLevelMenu() {
		String rootMenuIdentifier = resourceEntityServiceImpl.getRootMenu().getIdentifier();
		int i = 1;
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
		String spmi = resourceEntityServiceImpl.getDefaultIdentifier("/spmi", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化评分", "/spmi/quality", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化管理", "/spmi/document", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("日常工作", "/spmi/daily", spmi, "", i++);

		// 应急救援管理
		initErcsService.initThirdLevelMenu();

		// 机电管理
		initElectrService.initThirdLevelMenu();

		// 系统管理
		String system = resourceEntityServiceImpl.getDefaultIdentifier("/system", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("用户管理", "/system/account", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("角色管理", "/system/role", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机构管理", "/system/group", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("人员管理", "/system/staff", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("菜单管理", "/system/resource", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("字典管理", "/system/dictionary", system, "", i++);
		resourceEntityServiceImpl.saveMenuResource("日志查询", "/system/log", system, "", i++);
	}
}
