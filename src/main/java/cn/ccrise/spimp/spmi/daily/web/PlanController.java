/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
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
import cn.ccrise.spimp.system.service.AccountService;

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
	private MessageSource messageSource;

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		// 判断是否是整改通知单自动创建的工作安排，如果是则不能删除
		if (reformService.findUniqueBy("planId", id) != null) {
			return new Response(false);
		} else {
			return new Response(planService.delete(id));
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
	public Response page(Page<Plan> page, HttpSession httpSession, Date startDate, Date endDate, String category,
			PlanStatus status) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (startDate != null) {
			criterions.add(Restrictions.ge("cutoffDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("cutoffDate", DateUtils.addSeconds(DateUtils.addDays(endDate, 1), -1)));
		}
		if (category != null) {
			criterions.add(Restrictions.eq("category", category));
		}
		if (status != null) {
			criterions.add(Restrictions.eq("status", status));
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
		return new Response(planService.save(plan));
	}

	@RequestMapping(value = "/spmi/daily/plans/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Plan plan, @PathVariable long id) {
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

		return new Response(planService.update(planInDb));
	}
}