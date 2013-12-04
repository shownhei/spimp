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
import cn.ccrise.spimp.system.entity.DicitonaryType;
import cn.ccrise.spimp.system.entity.Dictionary;

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
		saveDicitonaryType("应急救援", "plan_type", "应急预案种类");
		saveDicitonaryType("应急救援", "personal_category", "人员类别");
		saveDicitonaryType("应急救援", "expertise_area", "专业领域");
		saveDicitonaryType("应急救援", "accident_category", "事故类别");
		saveDicitonaryType("应急救援", "accident_level", "事故严重程度");
		saveDicitonaryType("应急救援", "response_level", "事故响应级别");
		saveDicitonaryType("应急救援", "refuge_type", "避险场所种类");
		saveDicitonaryType("应急救援", "resource_type", "应急资源种类");
		saveDicitonaryType("应急救援", "education_level", "文化程度");
		saveDicitonaryType("应急救援", "organization_type", "应急保障机构类型");
		// 机电管理
		saveDicitonaryType("机电管理", "summary_type", "每月总结-总结类型");
		saveDicitonaryType("机电管理", "regulationfile_type", "制度文件类型");
		saveDicitonaryType("机电管理", "car_carCategory", "车辆分类");
		// 安全生产管理
		saveDicitonaryType("安全生产管理", "schedule_coal_type", "煤种");
		saveDicitonaryType("安全生产管理", "schedule_coal_series", "煤系");
		saveDicitonaryType("安全生产管理", "schedule_duty", "班次");
		saveDicitonaryType("安全生产管理", "schedule_team_type", "队组类型");
		saveDicitonaryType("安全生产管理", "schedule_injury_type", "受伤类型");
		saveDicitonaryType("安全生产管理", "schedule_working_face", "工作面");
		saveDicitonaryType("安全生产管理", "schedule_exploit_type", "开采方式");
		saveDicitonaryType("安全生产管理", "schedule_tunnel_type", "巷道类型");
		saveDicitonaryType("安全生产管理", "schedule_working_place", "工作地点");
		saveDicitonaryType("安全生产管理", "schedule_meeting_type", "会议类型");
		saveDicitonaryType("安全生产管理", "schedule_gas_emissions_type", "瓦斯排放类型");
		saveDicitonaryType("安全生产管理", "schedule_hidden_type", ">隐患类型");
		saveDicitonaryType("安全生产管理", "schedule_wellheads", "井口");
		saveDicitonaryType("安全生产管理", "schedule_alarm_type", "报警类型");
		saveDicitonaryType("安全生产管理", "document_project_type", "工程分类");
		Dictionary dic = new Dictionary();
		String carCategory[] = { "人车", "客货车", "洒水车", "两驱料车", "四驱料车", "铲运车", "支架搬运车", "其他未录入车型" };
		for (int i = 0; i < carCategory.length; i++) {
			dic = new Dictionary();
			dic.setTypeCode("car_carCategory");
			dic.setItemName(carCategory[i]);
			dictionaryService.save(dic);
		}
		initElectrService.initCustomData();
	}

	private void saveDicitonaryType(String dicGroup, String dicType, String typeTitle) {
		DicitonaryType type = new DicitonaryType();
		type.setDicGroup(dicGroup);
		type.setDicType(dicType);
		type.setTypeTitle(typeTitle);
		dicitonaryTypeService.save(type);
	}

	@Override
	public void initCustomSystemResource() {
	}

	@Override
	public void initFourthLevelOperate() {
		// 安全生产管理
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
		initElectrService.initThirdLevelMenu();

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
