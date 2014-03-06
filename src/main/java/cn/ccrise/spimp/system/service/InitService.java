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
import cn.ccrise.spimp.spmi.daily.entity.Folder;
import cn.ccrise.spimp.spmi.daily.service.FolderService;
import cn.ccrise.spimp.system.entity.Account;
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
	private GroupService groupService;
	@Autowired
	private InitErcsService initErcsService;
	@Autowired
	private InitElectrService initElectrService;
	@Autowired
	private DicitonaryTypeService dicitonaryTypeService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private FolderService folderService;

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
		// 人员管理
		dicitonaryTypeService.save("人员管理", "system_duty", "职务职称");
		dicitonaryTypeService.save("人员管理", "system_post", "岗位");

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

		dicitonaryTypeService.save("机电管理", "equipment_device_class", "设备分类");
		dicitonaryTypeService.save("机电管理", "equipment_deviceCategory", "设备种类");
		dicitonaryTypeService.save("机电管理", "equipment_deviceType", "设备类型");
		dicitonaryTypeService.save("机电管理", "equipment_serviceEnvironment", "使用环境");
		dicitonaryTypeService.save("机电管理", "equipment_deviceArea", "所属区域");
		dicitonaryTypeService.save("机电管理", "equipment_stowedPosition", "存放地点");

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
		// 事故类型
		String accidentCategories[] = { "顶板事故", "瓦斯事故", "机电事故", "放炮事故", "火灾事故", "水害事故" };
		for (String element : accidentCategories) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("accident_category");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 事故严重程度 accident_level
		String accidentLevels[] = { "特别重大事故", "重大事故", "较大事故", "一般事故" };
		for (String element : accidentLevels) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("accident_level");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 应急预案种类plan_type
		String planTypes[] = { "瓦斯突出事故应急预案", "火灾事故应急预案", "水害事故应急预案", "综合预案" };
		for (String element : planTypes) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("plan_type");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 专业领域expertise_area
		String expertiseArea[] = { "采矿专业", "机电专业", "通风安全", "地质专业" };
		for (String element : expertiseArea) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("expertise_area");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 事故响应级别 response_level
		String responseLevels[] = { "Ⅰ级响应", "Ⅱ级响应", "Ⅲ级响应", "Ⅳ级响应", "Ⅴ级响应" };
		for (String element : responseLevels) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("response_level");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 避险场所种类 refuge_type
		String refugeTypes[] = { "移动式救生舱", "避难硐室" };
		for (String element : refugeTypes) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("refuge_type");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 应急保障机构类型 organization_type
		String organizationType[] = { "医疗", "消防" };
		for (String element : organizationType) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("organization_type");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---设备分类
		String deviceClass[] = { "电器设备", "运输设备", "脚轮运输", "压风供水自救装置", "消防器材装置" };
		for (String element : deviceClass) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_device_class");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---设备种类
		String deviceCategory[] = { "安全交通灯", "变频器", "低压开关", "电动机", "高压开关", "集控设备", "流量计", "滤波器", "排水设施", "配电柜", "通风设施",
				"压风设施" };
		for (String element : deviceCategory) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_deviceCategory");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---设备类型
		String deviceType[] = { "35KV柜", "380V配电柜", "660V配电柜", "6KV柜", "LED屏", "按钮", "变频电动机", "传感器", "灯箱", "低压变频器",
				"电源箱", "干式变压器", "高爆", "高压变频器", "功率补偿柜", "管道泵", "检修设备", "控制柜", "控制箱", "馈电开关", "离心式水泵", "流量记传感器",
				"流量计主机", "启动器", "潜水式水泵", "软起动器", "输出滤波器", "输入滤波器", "双电源开关", "显示控制箱", "消弧柜", "压风机", "一般电动机", "移变",
				"浸油式变压器", "在线检测柜", "渣浆泵", "照明综保", "整流柜", "直流屏", "制动器", "制冷机", "主机", "主通风机", "自动排水系统", "自动长进开关" };
		for (String element : deviceType) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_deviceType");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---使用环境
		String[] serviceEnvironment = { "地面", "井下" };
		for (String element : serviceEnvironment) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_serviceEnvironment");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---所属区域
		String deviceArea[] = { "中央变电所", "压风机房", "1＃皮带", "避难硐室", "7＃皮带", "污水站", "主井", "", "中央水泵房", "35KV变电站", "副井",
				"630溜槽", "2＃配电点", "开拓变电所", "5＃皮带", "锅炉房", "胶轮运输巷", "主提升皮带", "主扇", "南翼配电点", "6＃皮带" };
		for (String element : deviceArea) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_deviceArea");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 设备台账---存放地点
		String stowedPosition[] = { "1＃驱动处", "1#皮带机头", "主井变压器室", "主提升变频室", "6＃皮带机尾处234#H架", "6KV配电室", "材料巷口", "1＃机尾",
				"平硐口", "630溜槽机头", "消防材料库", "旧消防材料库", "2#排水点", "南翼水仓", "1＃避难硐室", "7＃皮带机头", "锅炉房配电点", "1＃配电点", "3＃调车硐室",
				"自动补偿柜室", "压风机房", "值班室内", "新消防材料库", "5＃皮带机头", "开拓变电所", "风水管路539＃", "1#皮带机电硐室", "1#排水点", "7＃皮带56＃H架",
				"3＃避难硐室", "主扇配电室", "副井配电点", "1＃皮带机头", "主提升皮带机头", "绞车房", "35KV配电室", "材料巷口处", "消弧柜室", "主井配电室", "中央水泵房",
				"35KV变电站", "2＃配电点", "平硐3＃躲避硐", "6＃皮带机头", "主扇", "增压泵处", "中央变电所", "2＃避难硐室", "控制室", "3＃排水点", "主提升原值班室",
				"主提升皮带机尾", "2＃驱动处", "南翼配电点" };
		for (String element : stowedPosition) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("equipment_stowedPosition");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		String carCategory[] = { "人车", "客货车", "洒水车", "两驱料车", "四驱料车", "铲运车", "支架搬运车", "其他未录入车型" };
		for (String element : carCategory) {
			Dictionary dic = new Dictionary();
			dic.setTypeCode("car_carCategory");
			dic.setItemName(element);
			dictionaryService.save(dic);
		}
		// 文化程度
		String educations[] = { "高中", "中专", "大专", "本科", "硕士", "博士" };
		for (String education : educations) {
			Dictionary dictionary = new Dictionary();
			dictionary.setItemName(education);
			dictionary.setTypeCode("education_level");
			dictionaryService.save(dictionary);
		}
		// 职务职称
		String duties[] = { "队长", "副队长", "班长", "组长" };
		for (String duty : duties) {
			Dictionary dictionary = new Dictionary();
			dictionary.setItemName(duty);
			dictionary.setTypeCode("system_duty");
			dictionaryService.save(dictionary);
		}
		// 岗位
		String posts[] = { "把钩工", "充电工", "见习维修工", "井底把钩工", "皮带维修工", "电钳工", "配电工", "送饭工", "清煤工", "维修工", "卫生清理工", "修道工",
				"35KV变电站配电工", "队勤（材料员）", "队勤（核算员）", "副绞司机", "机车司机", "空压司机", "皮带司机", "拖拉机司机", "主井皮带司机", "主提升司机", "主扇司机",
				"轨道运输", "锅炉化验", "地面供电系统", "井下运输系统", "主提升系统", "全面负责", "主井卫生", "主提升机头", "主提升机尾" };
		for (String post : posts) {
			Dictionary dictionary = new Dictionary();
			dictionary.setItemName(post);
			dictionary.setTypeCode("system_post");
			dictionaryService.save(dictionary);
		}
		Folder folder = new Folder();
		folder.setName("文件名");
		folderService.save(folder);
		initElectrService.initCustomData();
	}

	@Override
	public void initCustomSystemResource() {
		// 调度室
		String ddsQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dds/quality", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("应急救援专业", "/spmi/dds/quality/rescue", ddsQuality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("信息调度专业", "/spmi/dds/quality/dispatch", ddsQuality, "", i++);
		String ddsDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dds/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/dds/document/query", ddsDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/dds/document/input", ddsDocument, "", i++);
		String ddsDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dds/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/dds/daily/reform", ddsDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/dds/daily/plan", ddsDaily, "", i++);

		// 安全科
		String aqkQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/aqk/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("安全管理专业", "/spmi/aqk/quality/safety", aqkQuality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("地面设施专业", "/spmi/aqk/quality/facilities", aqkQuality, "", i++);
		String aqkDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/aqk/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/aqk/document/query", aqkDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/aqk/document/input", aqkDocument, "", i++);
		String aqkDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/aqk/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/aqk/daily/reform", aqkDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/aqk/daily/plan", aqkDaily, "", i++);

		// 机电科
		String jdkQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdk/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("机电专业", "/spmi/jdk/quality/electro", jdkQuality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运输专业", "/spmi/jdk/quality/transportation", jdkQuality, "", i++);
		String jdkDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdk/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/jdk/document/query", jdkDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/jdk/document/input", jdkDocument, "", i++);
		String jdkDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdk/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/jdk/daily/reform", jdkDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/jdk/daily/plan", jdkDaily, "", i++);

		// 通风科
		String tfkQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("一通三防专业", "/spmi/tfk/quality/wind", tfkQuality, "", i++);
		resourceEntityServiceImpl.saveMenuResource("职业健康专业", "/spmi/tfk/quality/health", tfkQuality, "", i++);
		String tfkDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/tfk/document/query", tfkDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/tfk/document/input", tfkDocument, "", i++);
		String tfkDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/tfk/daily/reform", tfkDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/tfk/daily/plan", tfkDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("培训计划", "/spmi/tfk/daily/training", tfkDaily, "", i++);
		String tfkMaterial = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/material", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("采购计划", "/spmi/tfk/material/plan", tfkMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("库存管理", "/spmi/tfk/material/stock", tfkMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("入库管理", "/spmi/tfk/material/stock-putin", tfkMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("出库管理", "/spmi/tfk/material/stock-sendout", tfkMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件统计", "/spmi/tfk/material/statistics", tfkMaterial, "", i++);
		String tfkEquipment = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/equipment", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("设备台账", "/spmi/tfk/equipment/detail", tfkEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("检修计划", "/spmi/tfk/equipment/plan", tfkEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修记录", "/spmi/tfk/equipment/overhaul", tfkEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修提醒", "/spmi/tfk/equipment/alert", tfkEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修设置", "/spmi/tfk/equipment/settings", tfkEquipment, "", i++);
		String tfkMaintenance = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk/maintenance", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("日常保养", "/spmi/tfk/maintenance/daily", tfkMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养", "/spmi/tfk/maintenance/schedule", tfkMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修检测", "/spmi/tfk/maintenance/maintenance-testing", tfkMaintenance,
				"", i++);
		resourceEntityServiceImpl.saveMenuResource("故障记录", "/spmi/tfk/maintenance/problem", tfkMaintenance, "", i++);

		// 生产技术科
		String sjkQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/sjk/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("采煤专业", "/spmi/sjk/quality/mining", sjkQuality, "", i++);
		String sjkDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/sjk/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/sjk/document/query", sjkDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/sjk/document/input", sjkDocument, "", i++);
		String sjkDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/sjk/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/sjk/daily/reform", sjkDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/sjk/daily/plan", sjkDaily, "", i++);

		// 地测科
		String dckQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dck/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("地测防治水专业", "/spmi/dck/quality/water", dckQuality, "", i++);
		String dckDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dck/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/dck/document/query", dckDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/dck/document/input", dckDocument, "", i++);
		String dckDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dck/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/dck/daily/reform", dckDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/dck/daily/plan", dckDaily, "", i++);

		// 防治水科
		String fzsQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/fzs/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("地测防治水专业", "/spmi/fzs/quality/water", fzsQuality, "", i++);
		String fzsDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/fzs/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/fzs/document/query", fzsDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/fzs/document/input", fzsDocument, "", i++);
		String fzsDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/fzs/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("整改通知单", "/spmi/fzs/daily/reform", fzsDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/fzs/daily/plan", fzsDaily, "", i++);

		// 探放水队
		String tfsDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfs/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/tfs/document/query", tfsDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/tfs/document/input", tfsDocument, "", i++);
		String tfsDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfs/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/tfs/daily/plan", tfsDaily, "", i++);

		// 机电机运队
		String jddDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/jdd/document/query", jddDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/jdd/document/input", jddDocument, "", i++);
		String jddDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/jdd/daily/plan", jddDaily, "", i++);
		i = 1;
		String routine = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/routine", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("奖惩记录", "/spmi/jdd/routine/reward", routine, "", i++);
		resourceEntityServiceImpl.saveMenuResource("图片管理", "/spmi/jdd/routine/picture", routine, "", i++);
		resourceEntityServiceImpl.saveMenuResource("每月总结", "/spmi/jdd/routine/summary", routine, "", i++);
		String jddMaterial = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/material", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("采购计划", "/spmi/jdd/material/plan", jddMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("库存管理", "/spmi/jdd/material/stock", jddMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("入库管理", "/spmi/jdd/material/stock-putin", jddMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("出库管理", "/spmi/jdd/material/stock-sendout", jddMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件统计", "/spmi/jdd/material/statistics", jddMaterial, "", i++);
		String jddEquipment = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/equipment", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("检修计划", "/spmi/jdd/equipment/plan", jddEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修记录", "/spmi/jdd/equipment/overhaul", jddEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修提醒", "/spmi/jdd/equipment/alert", jddEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修设置", "/spmi/jdd/equipment/settings", jddEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("专利发明", "/spmi/jdd/innovation/innovation", jddEquipment, "", i++);
		// equipment-ledger
		String jddEquipmentLedger = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/equipment-ledger",
				HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("电器设备", "/spmi/jdd/equipment-ledger/detail", jddEquipmentLedger, "",
				i++);
		resourceEntityServiceImpl.saveMenuResource("运输设备", "/spmi/jdd/equipment-ledger/transform", jddEquipmentLedger,
				"", i++);
		resourceEntityServiceImpl.saveMenuResource("压风供水自救设备", "/spmi/jdd/equipment-ledger/wind-water",
				jddEquipmentLedger, "", i++);
		resourceEntityServiceImpl.saveMenuResource("井下消防设备", "/spmi/jdd/equipment-ledger/fire-fighting",
				jddEquipmentLedger, "", i++);

		String jddMaintenance = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/maintenance", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("日常保养", "/spmi/jdd/maintenance/daily", jddMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养", "/spmi/jdd/maintenance/schedule", jddMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修检测", "/spmi/jdd/maintenance/maintenance-testing", jddMaintenance,
				"", i++);
		resourceEntityServiceImpl.saveMenuResource("故障记录", "/spmi/jdd/maintenance/problem", jddMaintenance, "", i++);
		String jddCar = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd/car", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/spmi/jdd/car/car", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运行日志", "/spmi/jdd/car/runlog", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度油耗统计", "/spmi/jdd/car/annual-oil", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度公里统计", "/spmi/jdd/car/annual-kilometer", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度油耗统计", "/spmi/jdd/car/monthly-oil", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度运行统计", "/spmi/jdd/car/monthly-run", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养周期配置", "/electr/maintenance/regular-config", jddCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养到期提醒", "/electr/maintenance/regular-remind", jddCar, "", i++);

		// 综采队
		String zcdQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("采煤专业", "/spmi/zcd/quality/mining", zcdQuality, "", i++);
		String zcdDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/zcd/document/query", zcdDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/zcd/document/input", zcdDocument, "", i++);
		String zcdDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("生产日报表", "/spmi/zcd/daily/report", zcdDaily, "", i++);
		resourceEntityServiceImpl.saveMenuResource("产量统计", "/spmi/zcd/daily/output", zcdDaily, "", i++);
		i = 1;
		String safe = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/safe", HttpMethod.GET);
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/zcd/safe/plan", safe, "", i++);

		String zcdMaterial = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/material", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("采购计划", "/spmi/zcd/material/plan", zcdMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("库存管理", "/spmi/zcd/material/stock", zcdMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("入库管理", "/spmi/zcd/material/stock-putin", zcdMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("出库管理", "/spmi/zcd/material/stock-sendout", zcdMaterial, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件统计", "/spmi/zcd/material/statistics", zcdMaterial, "", i++);

		String zcdEquipmentLedger = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/equipment-ledger",
				HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("电器设备", "/spmi/zcd/equipment-ledger/detail", zcdEquipmentLedger, "",
				i++);
		resourceEntityServiceImpl.saveMenuResource("运输设备", "/spmi/zcd/equipment-ledger/transform", zcdEquipmentLedger,
				"", i++);
		resourceEntityServiceImpl.saveMenuResource("压风供水自救设备", "/spmi/zcd/equipment-ledger/wind-water",
				zcdEquipmentLedger, "", i++);
		resourceEntityServiceImpl.saveMenuResource("井下消防设备", "/spmi/zcd/equipment-ledger/fire-fighting",
				zcdEquipmentLedger, "", i++);

		String zcdEquipment = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/equipment", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("检修计划", "/spmi/zcd/equipment/plan", zcdEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修记录", "/spmi/zcd/equipment/overhaul", zcdEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修提醒", "/spmi/zcd/equipment/alert", zcdEquipment, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期检修设置", "/spmi/zcd/equipment/settings", zcdEquipment, "", i++);
		String zcdMaintenance = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/maintenance", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("日常保养", "/spmi/zcd/maintenance/daily", zcdMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("定期保养", "/spmi/zcd/maintenance/schedule", zcdMaintenance, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修检测", "/spmi/zcd/maintenance/maintenance-testing", zcdMaintenance,
				"", i++);
		resourceEntityServiceImpl.saveMenuResource("故障记录", "/spmi/zcd/maintenance/problem", zcdMaintenance, "", i++);
		String zcdCar = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd/car", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/spmi/zcd/car/car", zcdCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("运行日志", "/spmi/zcd/car/runlog", zcdCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度油耗统计", "/spmi/zcd/car/annual-oil", zcdCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("年度公里统计", "/spmi/zcd/car/annual-kilometer", zcdCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度油耗统计", "/spmi/zcd/car/monthly-oil", zcdCar, "", i++);
		resourceEntityServiceImpl.saveMenuResource("月度运行统计", "/spmi/zcd/car/monthly-run", zcdCar, "", i++);

		// 掘进一队
		String jjyQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jjy/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/jjy/quality/tunnelling", jjyQuality, "", i++);
		String jjyDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jjy/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/jjy/document/query", jjyDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/jjy/document/input", jjyDocument, "", i++);
		String jjyDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jjy/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/jjy/daily/plan", jjyDaily, "", i++);

		// 掘进二队
		String jjeQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jje/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/jje/quality/tunnelling", jjeQuality, "", i++);
		String jjeDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jje/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/jje/document/query", jjeDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/jje/document/input", jjeDocument, "", i++);
		String jjeDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jje/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/jje/daily/plan", jjeDaily, "", i++);

		// 开拓一队
		String ktyQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kty/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/kty/quality/tunnelling", ktyQuality, "", i++);
		String ktyDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kty/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/kty/document/query", ktyDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/kty/document/input", ktyDocument, "", i++);
		String ktyDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kty/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/kty/daily/plan", ktyDaily, "", i++);

		// 开拓二队
		String kteQuality = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kte/quality", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("掘进专业", "/spmi/kte/quality/tunnelling", kteQuality, "", i++);
		String kteDocument = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kte/document", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档综合查询", "/spmi/kte/document/query", kteDocument, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档录入", "/spmi/kte/document/input", kteDocument, "", i++);
		String kteDaily = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kte/daily", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("派工单管理", "/spmi/kte/daily/plan", kteDaily, "", i++);
	}

	@Override
	public void initFourthLevelOperate() {
		// 安全生产管理
		// 调度室
		String dds = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dds", HttpMethod.GET);
		int i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/dds/quality", dds, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/dds/document", dds, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/dds/info", dds, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/dds/daily", dds, "", i++);

		// 安全科
		String aqk = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/aqk", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/aqk/quality", aqk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/aqk/document", aqk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/aqk/info", aqk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/aqk/daily", aqk, "", i++);

		// 机电科
		String jdk = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdk", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/jdk/quality", jdk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/jdk/document", jdk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/jdk/info", jdk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/jdk/daily", jdk, "", i++);

		// 通风科
		String tfk = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfk", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/tfk/quality", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/tfk/document", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/tfk/info", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/tfk/daily", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件管理", "/spmi/tfk/material", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("人员管理", "/spmi/tfk/staff", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备管理", "/spmi/tfk/equipment", tfk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修保养", "/spmi/tfk/maintenance", tfk, "", i++);

		// 生产技术科
		String sjk = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/sjk", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/sjk/quality", sjk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/sjk/document", sjk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/sjk/info", sjk, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/sjk/daily", sjk, "", i++);

		// 地测科
		String dck = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/dck", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/dck/quality", dck, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/dck/document", dck, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/dck/info", dck, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/dck/daily", dck, "", i++);

		// 防治水科
		String fzs = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/fzs", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/fzs/quality", fzs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/fzs/document", fzs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/fzs/info", fzs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/fzs/daily", fzs, "", i++);

		// 探放水队
		String tfs = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/tfs", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/tfs/document", tfs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/tfs/info", tfs, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/tfs/daily", tfs, "", i++);

		// 机电机运队
		String jdd = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jdd", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/jdd/document", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/jdd/info", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/jdd/daily", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("日常工作", "/spmi/jdd/routine", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件管理", "/spmi/jdd/material", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机构管理", "/spmi/jdd/group", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("人员管理", "/spmi/jdd/staff", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备管理", "/spmi/jdd/equipment", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备台账", "/spmi/jdd/equipment-ledger", jdd, "", i++);
		// resourceEntityServiceImpl.saveMenuResource("岗位管理", "/spmi/jdd/post",
		// jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修保养", "/spmi/jdd/maintenance", jdd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/spmi/jdd/car", jdd, "", i++);

		// 综采队
		String zcd = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/zcd", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/zcd/quality", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/zcd/document", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/zcd/info", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("产量管理", "/spmi/zcd/daily", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/zcd/safe", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("配件管理", "/spmi/zcd/material", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("人员管理", "/spmi/zcd/staff", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备管理", "/spmi/zcd/equipment", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("设备台账", "/spmi/zcd/equipment-ledger", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("维修保养", "/spmi/zcd/maintenance", zcd, "", i++);
		resourceEntityServiceImpl.saveMenuResource("车辆管理", "/spmi/zcd/car", zcd, "", i++);

		// 掘进一队
		String jjy = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jjy", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/jjy/quality", jjy, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/jjy/document", jjy, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/jjy/info", jjy, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/jjy/daily", jjy, "", i++);

		// 掘进二队
		String jje = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/jje", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/jje/quality", jje, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/jje/document", jje, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/jje/info", jje, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/jje/daily", jje, "", i++);

		// 开拓一队
		String kty = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kty", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/kty/quality", kty, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/kty/document", kty, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/kty/info", kty, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/kty/daily", kty, "", i++);

		// 开拓二队
		String kte = resourceEntityServiceImpl.getDefaultIdentifier("/spmi/kte", HttpMethod.GET);
		i = 1;
		resourceEntityServiceImpl.saveMenuResource("质量标准化", "/spmi/kte/quality", kte, "", i++);
		resourceEntityServiceImpl.saveMenuResource("质量标准化文档管理", "/spmi/kte/document", kte, "", i++);
		resourceEntityServiceImpl.saveMenuResource("经验信息库", "/spmi/kte/info", kte, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全整改", "/spmi/kte/daily", kte, "", i++);
		// 应急救援管理系统
		initErcsService.initFourthLevelOperate();
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
		groupService.save("地测科", "DCK", "office", "科室", g1);
		groupService.save("防治水科", "FZS", "office", "科室", g1);

		groupService.save("探放水队", "TFS", "team", "队组", g1);
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
		resourceEntityServiceImpl.saveMenuResource("调度室", "/spmi/dds", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("安全科", "/spmi/aqk", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机电科", "/spmi/jdk", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("通风科", "/spmi/tfk", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("生产技术科", "/spmi/sjk", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("地测科", "/spmi/dck", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("防治水科", "/spmi/fzs", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("探放水队", "/spmi/tfs", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("综采队", "/spmi/zcd", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("机电机运队", "/spmi/jdd", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("掘进一队", "/spmi/jjy", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("掘进二队", "/spmi/jje", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("开拓一队", "/spmi/kty", spmi, "", i++);
		resourceEntityServiceImpl.saveMenuResource("开拓二队", "/spmi/kte", spmi, "", i++);

		// 应急救援管理
		initErcsService.initThirdLevelMenu();

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
