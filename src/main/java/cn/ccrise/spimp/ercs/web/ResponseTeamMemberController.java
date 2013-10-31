/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.util.ArrayList;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
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
import cn.ccrise.spimp.ercs.entity.ResponseTeamMember;
import cn.ccrise.spimp.ercs.service.ResponseTeamMemberService;
import cn.ccrise.spimp.ercs.service.ResponseTeamService;

/**
 * ResponseTeamMember Controller。
 * 
 */
@Controller
public class ResponseTeamMemberController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ResponseTeamService responseTeamService;
	@Autowired
	private ResponseTeamMemberService responseTeamMemberService;

	@RequestMapping(value = "/ercs/response-team-members/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(responseTeamMemberService.delete(id));
	}

	@RequestMapping(value = "/ercs/response-team-members/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(responseTeamMemberService.get(id));
	}

	@RequestMapping(value = "/ercs/response-team-members", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ResponseTeamMember> page, String memberType, Long teamId) {
		ArrayList<SimpleExpression> param = new ArrayList<SimpleExpression>();
		if (StringUtils.isNotBlank(memberType)) {
			param.add(Restrictions.eq("memberType", memberType));
		}
		if (teamId != null) {
			param.add(Restrictions.eq("team", responseTeamService.findUniqueBy("id", teamId)));
		}
		page.setOrder("asc");
		page.setOrderBy("memberLevel");
		return new Response(responseTeamMemberService.getPage(page, param.toArray(new SimpleExpression[0])));
	}

	@RequestMapping(value = "/ercs/response-team-members", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ResponseTeamMember responseTeamMember) {
		return new Response(responseTeamMemberService.save(responseTeamMember));
	}

	@RequestMapping(value = "/ercs/response-team-members/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ResponseTeamMember responseTeamMember, @PathVariable long id) {
		return new Response(responseTeamMemberService.update(responseTeamMember));
	}
}