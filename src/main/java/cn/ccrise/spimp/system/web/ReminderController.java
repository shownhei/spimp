/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.List;
import java.util.Queue;

import javax.servlet.http.HttpSession;

import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.ercs.entity.Alarm;
import cn.ccrise.spimp.ercs.service.AlarmService;
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.entity.Plan.PlanStatus;
import cn.ccrise.spimp.spmi.daily.web.PlanController;
import cn.ccrise.spimp.system.web.entity.ReminderDeferredResult;
import cn.ccrise.spimp.system.web.entity.ReminderMessage;
import cn.ccrise.spimp.system.web.entity.ReminderResponse;

import com.google.common.collect.Queues;

/**
 * 提醒控制器。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ReminderController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public final Queue<ReminderDeferredResult<ReminderResponse>> messageQueue = Queues.newConcurrentLinkedQueue();

	@Autowired
	private PlanController planController;
	@Autowired
	private AlarmService alarmService;

	@RequestMapping(value = "/reminder/notification/pull", method = RequestMethod.GET)
	@ResponseBody
	public ReminderResponse pullNotification(HttpSession httpSession) {
		logger.debug("Current queue size:{}", messageQueue.size());
		return getReminderResponse(httpSession);
	}

	/**
	 * 推送
	 */
	public void push() {
		logger.debug("Start to push,queue size:{}", messageQueue.size());

		for (ReminderDeferredResult<ReminderResponse> reminderDeferredResult : messageQueue) {
			boolean isSetOrExpired = reminderDeferredResult.isSetOrExpired();
			boolean setResult = false;
			try {
				setResult = reminderDeferredResult.setResult(getReminderResponse(reminderDeferredResult
						.getHttpSession()));
			} catch (IllegalArgumentException exception) {
				logger.warn("IllegalArgumentException,isSetOrExpired:{},setResult:{}", isSetOrExpired, setResult);
			}

			messageQueue.remove(reminderDeferredResult);
		}
	}

	@RequestMapping(value = "/reminder/notification/push", method = RequestMethod.GET)
	@ResponseBody
	public ReminderDeferredResult<ReminderResponse> pushNotification(HttpSession httpSession) {
		ReminderDeferredResult<ReminderResponse> deferredResult = new ReminderDeferredResult<>(300000, httpSession);
		messageQueue.add(deferredResult);
		return deferredResult;
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

	@SuppressWarnings("unchecked")
	private ReminderResponse getReminderResponse(HttpSession httpSession) {
		ReminderResponse reminderResponse = new ReminderResponse();

		// 未指派的工作安排
		Page<Plan> planPage = new Page<>(Integer.MAX_VALUE);
		planPage = (Page<Plan>) planController.page(planPage, httpSession, null, null, null, null, null,
				PlanStatus.未指派, null, null, null).getData();
		reminderResponse.addReminderMessage(new ReminderMessage("你有" + planPage.getTotalCount() + "条未指派的工作安排",
				getRealLink(httpSession, "/daily/plan"), planPage.getTotalCount(), "工作安排"));

		// 未处理的报警
		Long alarmCount = alarmService.count(Restrictions.eq("dealFlag", Alarm.DEAL_FLAG_UNDEALED));
		reminderResponse.addReminderMessage(new ReminderMessage("你有" + alarmCount + "条未处理的报警", "/ercs/alarm",
				alarmCount, "报警"));

		return reminderResponse;
	}
}
