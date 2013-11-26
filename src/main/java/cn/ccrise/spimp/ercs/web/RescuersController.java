/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.entity.Rescuers;
import cn.ccrise.spimp.ercs.entity.ResponseTeamMember;
import cn.ccrise.spimp.ercs.service.RescuersService;
import cn.ccrise.spimp.ercs.service.ResponseTeamMemberService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.service.AccountService;

/**
 * Rescuers Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class RescuersController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RescuersService rescuersService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ResponseTeamMemberService responseTeamMemberService;

	@RequestMapping(value = "/ercs/rescuers/associate", method = RequestMethod.POST)
	@ResponseBody
	public Response associate(Long staffId, Long accountId) {
		Rescuers rescuer = rescuersService.findUniqueBy("id", staffId);
		Account account = accountService.findUniqueBy("id", accountId);
		rescuer.setAccount(account);
		rescuersService.update(rescuer);
		return new Response(true);
	}

	@RequestMapping(value = "/ercs/rescuers/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		List<ResponseTeamMember> list = responseTeamMemberService.find(Restrictions.eq("normalMember",
				rescuersService.get(id)));
		if (list != null && list.size() > 0) {
			return new Response(false);// 已经被关联到应急机构，需先解除关联
		} else {
			return new Response(rescuersService.delete(id));
		}
	}

	@RequestMapping(value = "/ercs/rescuers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(rescuersService.get(id));
	}

	@RequestMapping(value = "/ercs/rescuers", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Rescuers> page, String staffName, String q) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(staffName)) {
			param.add(Restrictions.like("staffName", staffName, MatchMode.ANYWHERE));
		}
		if (StringUtils.isNotBlank(q)) {
			param.add(Restrictions.like("staffName", q, MatchMode.ANYWHERE));
		}
		return new Response(rescuersService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/rescuers", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Rescuers rescuers) {
		rescuers.setAddTime(new Timestamp(System.currentTimeMillis()));
		return new Response(rescuersService.save(rescuers));
	}

	@RequestMapping(value = "/ercs/rescuers/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Rescuers rescuers, @PathVariable long id) {
		return new Response(rescuersService.update(rescuers));
	}
}