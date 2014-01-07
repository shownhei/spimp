/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.entity.Plan.PlanStatus;
import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.spimp.spmi.daily.entity.Reform.ReformStatus;
import cn.ccrise.spimp.spmi.daily.service.PlanService;
import cn.ccrise.spimp.spmi.daily.service.ReformService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;
import cn.ccrise.spimp.system.service.GroupService;
import cn.ccrise.spimp.system.web.ReminderController;

import com.google.common.collect.Lists;

/**
 * Plan Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class PlanController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlanService planService;
	@Autowired
	private ReformService reformService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private ReminderController reminderController;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id, HttpSession httpSession) {
		// 判断是否是整改通知单自动创建的工作安排，如果是则不能删除
		if (reformService.findUniqueBy("planId", id) != null) {
			return new Response(false);
		} else {
			boolean result = planService.delete(id);

			// 推送
			reminderController.push(httpSession);

			return new Response(result);
		}
	}

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(planService.get(id));
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/spmi/daily/plans", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Plan> page, HttpSession httpSession, Date startDate, Date endDate,
			Date feedbackStartDate, Date feedbackEndDate, String category, PlanStatus status, String content,
			String feedback, Boolean filter) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (startDate != null) {
			criterions.add(Restrictions.ge("cutoffDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("cutoffDate", DateUtils.addSeconds(DateUtils.addDays(endDate, 1), -1)));
		}
		if (feedbackStartDate != null) {
			criterions.add(Restrictions.ge("feedbackTime", feedbackStartDate));
		}
		if (feedbackEndDate != null) {
			criterions.add(Restrictions.le("feedbackTime",
					DateUtils.addSeconds(DateUtils.addDays(feedbackEndDate, 1), -1)));
		}
		if (category != null) {
			criterions.add(Restrictions.eq("category", category));
		}
		if (status != null) {
			criterions.add(Restrictions.eq("status", status));
		}
		if (content != null) {
			criterions.add(Restrictions.like("content", content, MatchMode.ANYWHERE));
		}
		if (feedback != null) {
			criterions.add(Restrictions.like("feedback", feedback, MatchMode.ANYWHERE));
		}

		if (BooleanUtils.isNotFalse(filter)) {
			addFilter(httpSession, criterions);
		}

		page = planService.getPage(page, criterions.toArray(new Criterion[0]));

		for (Plan plan : page.getResult()) {
			plan.setCreater(accountService.get(plan.getCreaterId()).getRealName());
		}

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/daily/plans", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Plan plan, HttpSession httpSession) {
		plan.setCreaterId(WebUtils.getAccount(httpSession).getId());
		if (StringUtils.isBlank(plan.getExecutor())) {
			plan.setStatus(PlanStatus.未指派);
		} else {
			plan.setStatus(PlanStatus.已指派);
		}

		boolean result = planService.save(plan);

		// 推送
		reminderController.push(httpSession);

		return new Response(result);
	}

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Plan plan, @PathVariable long id, HttpSession httpSession) {
		Plan planInDb = planService.get(id);

		if (planInDb.getStatus().equals(PlanStatus.未指派)) { // 未指派->已指派，只能是整改安排
			planInDb.setContent(plan.getContent());
			planInDb.setExecutor(plan.getExecutor());
			planInDb.setStatus(PlanStatus.已指派);

			/* 同步更新整改通知单状态为：已指派 */
			Reform reform = reformService.findUniqueBy("planId", planInDb.getId());
			if (reform != null) {
				reform.setStatus(ReformStatus.已指派);
				reformService.update(reform);
			}
		} else if (planInDb.getStatus().equals(PlanStatus.已指派)) {
			if (plan.getFeedback() != null) { // 已指派->已执行
				planInDb.setFeedback(plan.getFeedback());
				planInDb.setFeedbackTime(new Timestamp(System.currentTimeMillis()));
				planInDb.setStatus(PlanStatus.已执行);

				/* 同步更新整改通知单状态为：已执行 */
				Reform reform = reformService.findUniqueBy("planId", planInDb.getId());
				if (reform != null) {
					reform.setStatus(ReformStatus.已执行);
					reformService.update(reform);
				}
			} else { // 已指派->已指派
				planInDb.setTitle(plan.getTitle());
				planInDb.setCategory(plan.getCategory());
				planInDb.setCutoffDate(plan.getCutoffDate());
				planInDb.setContent(plan.getContent());
				planInDb.setExecutor(plan.getExecutor());
				planInDb.setStatus(PlanStatus.已指派);
			}
		}

		boolean result = planService.update(planInDb);

		// 推送
		reminderController.push(httpSession);

		return new Response(result);
	}

	/**
	 * 根据用户所在机构查询同一机构所有用户的工作安排，包括下属机构。
	 * 
	 * @param httpSession
	 * @param criterions
	 */
	private void addFilter(HttpSession httpSession, ArrayList<Criterion> criterions) {
		GroupEntity groupEntityInDb = groupService.get(WebUtils.getGroup(httpSession).getId()); // 重新从数据库获取机构数据

		List<GroupEntity> childrenGroups = groupService.getChildrenByQueryLabel(groupEntityInDb, new String[] { "mine",
				"office", "team", "other" });

		childrenGroups.add(WebUtils.getGroup(httpSession));

		List<Account> accounts = accountService.find(Restrictions.in("groupEntity", childrenGroups));
		List<Long> accountIds = Lists.newArrayList();
		for (Account account : accounts) {
			accountIds.add(account.getId());
		}

		if (accounts.size() != 0) {
			criterions.add(Restrictions.in("createrId", accountIds));
		} else {
			// 如果没有下属机构，则什么都不显示
			criterions.add(Restrictions.eq("createrId", 0L));
		}
	}
}