/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.RegularMaintenanceRemind;
import cn.ccrise.spimp.electr.entity.ReminderSetting;
import cn.ccrise.spimp.electr.web.RegularMaintenanceRemindController;
import cn.ccrise.spimp.electr.web.ReminderSettingController;
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.spimp.ercs.web.AlarmController;
import cn.ccrise.spimp.ercs.web.EmergencyPlanInstanceController;
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.entity.Plan.PlanStatus;
import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.spimp.spmi.daily.entity.Reform.ReformStatus;
import cn.ccrise.spimp.spmi.daily.web.PlanController;
import cn.ccrise.spimp.spmi.daily.web.ReformController;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.system.web.entity.Infobox;

import com.google.common.collect.Lists;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class InfoboxController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlanController planController;
	@Autowired
	private ReformController reformController;
	@Autowired
	private AlarmController alarmController;
	@Autowired
	private EmergencyPlanInstanceController emergencyPlanInstanceController;
	@Autowired
	private RegularMaintenanceRemindController regularMaintenanceRemindController;
	@Autowired
	private ReminderSettingController reminderSettingController;
	@Autowired
	private ReminderController reminderController;
	@Autowired
	private AccountService accountService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/infobox/pull", method = RequestMethod.GET)
	@ResponseBody
	public Response pullInfobox(HttpSession httpSession) {
		List<Infobox> result = Lists.newArrayList();

		long roleId = WebUtils.getRole(httpSession).getId();

		// 未指派的工作安排
		Page<Plan> planPage = new Page<>(Integer.MAX_VALUE);
		planPage = (Page<Plan>) planController.page(planPage, httpSession, null, null, null, null, null,
				PlanStatus.未指派, null, null, null).getData();
		result.add(new Infobox("infobox-green", "icon-check", planPage.getTotalCount() + "条", "未指派的工作安排", getRealLink(
				httpSession, "/daily/plan")));

		// 已执行的整改通知单
		Page<Reform> reformPage = new Page<>(Integer.MAX_VALUE);
		reformPage = (Page<Reform>) reformController.page(reformPage, httpSession, null, null, null, ReformStatus.已执行)
				.getData();
		result.add(new Infobox("infobox-blue2", "icon-file-alt", reformPage.getTotalCount() + "条", "已执行的整改通知单",
				getRealLink(httpSession, "/daily/reform")));

		// 应急报警
		if (accountService.hasPermission(roleId, "/ercs/alarm")) {
			Page<Alarm> alarmPage = new Page<Alarm>(Integer.MAX_VALUE);
			alarmPage = (Page<Alarm>) alarmController.page(alarmPage, null, null, Alarm.DEAL_FLAG_UNDEALED, false)
					.getData();
			result.add(new Infobox("infobox-red", "icon-phone", alarmPage.getTotalCount() + "条", "报警信息", getRealLink(
					httpSession, "/ercs/alarm")));
		}

		// 定期检修提醒
		if (accountService.hasPermission(roleId, "/spmi/tfk/maintenance/schedule")) {
			Page<ReminderSetting> reminderSettingPage = new Page<ReminderSetting>(Integer.MAX_VALUE);
			reminderSettingPage = (Page<ReminderSetting>) reminderSettingController.page(reminderSettingPage, null,
					null, null).getData();
			result.add(new Infobox("infobox-blue", "icon-dashboard", reminderSettingPage.getTotalCount() + "条",
					"定期检修到期提醒", getRealLink(httpSession, "/spmi/jdd/equipment/alert")));
		}

		// 任务查看
		if (accountService.hasPermission(roleId, "/ercs/perform-rescue/task-view")) {
			Page<EmergencyPlanInstance> emergencyPlanPage = new Page<EmergencyPlanInstance>(Integer.MAX_VALUE);
			emergencyPlanPage = (Page<EmergencyPlanInstance>) emergencyPlanInstanceController.page(emergencyPlanPage,
					null, httpSession, Boolean.FALSE).getData();
			result.add(new Infobox("infobox-orange", "icon-list", emergencyPlanPage.getTotalCount() + "条", "应急救援任务",
					getRealLink(httpSession, "/ercs/emergency-plan-instances")));
		}

		// 定期保养
		if (accountService.hasPermission(roleId, "/spmi/tfk/maintenance/schedule")) {
			Page<RegularMaintenanceRemind> reminderPage = new Page<RegularMaintenanceRemind>(Integer.MAX_VALUE);
			reminderPage = (Page<RegularMaintenanceRemind>) regularMaintenanceRemindController.page(reminderPage, null)
					.getData();
			result.add(new Infobox("infobox-orange2", "icon-bell", reminderPage.getTotalCount() + "条", "定期保养提醒",
					getRealLink(httpSession, "/electr/maintenance/regular-remind")));
		}

		return new Response(result);
	}

	private String getRealLink(HttpSession httpSession, String link) {
		List<ResourceEntity> roleResourceEntities = WebUtils.getRole(httpSession).getResourceEntities();
		String realLink = "";

		for (ResourceEntity resourceEntity : roleResourceEntities) {
			if (resourceEntity.getUri().contains(link)) {
				realLink = resourceEntity.getUri();
				return realLink;
			}
		}

		return realLink;
	}
}
