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
import cn.ccrise.spimp.system.service.GroupService;

import com.google.common.collect.Lists;

/**
 * Reform Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ReformController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ReformService reformService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private PlanService planService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(reformService.delete(id, true));
	}

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		Reform reform = reformService.get(id);

		reform.setPrincipal(accountService.get(reform.getPrincipalId()).getRealName());
		reform.setCreater(accountService.get(reform.getCreaterId()).getRealName());
		reform.setSendGroup(groupService.get(reform.getSendGroupId()).getName());
		reform.setTestGroup(groupService.get(reform.getTestGroupId()).getName());

		return new Response(reform);
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/spmi/daily/reforms", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Reform> page, HttpSession httpSession, Date startDate, Date endDate, Long testGroupId,
			ReformStatus status) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (startDate != null) {
			criterions.add(Restrictions.ge("checkDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("checkDate", DateUtils.addSeconds(DateUtils.addDays(endDate, 1), -1)));
		}
		if (testGroupId != null) {
			criterions.add(Restrictions.eq("testGroupId", testGroupId));
		}
		if (status != null) {
			criterions.add(Restrictions.eq("status", status));
		}

		addFilter(httpSession, criterions);

		page = reformService.getPage(page, criterions.toArray(new Criterion[0]));

		for (Reform reform : page.getResult()) {
			reform.setPrincipal(accountService.get(reform.getPrincipalId()).getRealName());
			reform.setCreater(accountService.get(reform.getCreaterId()).getRealName());
			reform.setSendGroup(groupService.get(reform.getSendGroupId()).getName());
			reform.setTestGroup(groupService.get(reform.getTestGroupId()).getName());
		}

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/daily/reforms", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Reform reform, HttpSession httpSession) {
		reform.setCreaterId(WebUtils.getAccount(httpSession).getId());
		return new Response(reformService.save(reform, true));
	}

	@RequestMapping(value = "/spmi/daily/reforms/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Reform reform, @PathVariable long id) {
		Reform reformInDb = reformService.get(id);

		if (reformInDb.getStatus().equals(ReformStatus.已下发)) { // 编辑
			return new Response(reformService.update(reform, true));
		} else if (reformInDb.getStatus().equals(ReformStatus.已执行)) { // 已执行->已审核
			reformInDb.setFeedback(reform.getFeedback());
			reformInDb.setFeedbackTime(new Timestamp(System.currentTimeMillis()));
			reformInDb.setStatus(ReformStatus.已审核);
			reformInDb.setDoneDate(new Date(System.currentTimeMillis()));

			/* 同步更新工作安排状态为：已完成 */
			Plan plan = planService.get(reformInDb.getPlanId());
			plan.setStatus(PlanStatus.已完成);
			planService.update(plan);
			return new Response(reformService.update(reformInDb));
		} else { // 不做任何改动
			return new Response(true);
		}
	}

	/**
	 * 增加根据用户所在机构查询数据的过滤条件。
	 * 
	 * @param httpSession
	 * @param criterions
	 */
	private void addFilter(HttpSession httpSession, ArrayList<Criterion> criterions) {
		List<Long> childrenGroupIds = groupService.getChildrenByQueryLabel(WebUtils.getGroup(httpSession).getId(),
				new String[] { "mine", "office", "team", "other" });

		childrenGroupIds.add(WebUtils.getGroup(httpSession).getId());

		if (childrenGroupIds.size() != 0) {
			criterions.add(Restrictions.in("sendGroupId", childrenGroupIds));
		} else {
			// 如果没有下属机构，则什么都不显示
			criterions.add(Restrictions.eq("sendGroupId", 0L));
		}
	}
}