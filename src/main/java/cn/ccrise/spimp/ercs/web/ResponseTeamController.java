/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
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
import cn.ccrise.spimp.ercs.entity.ResponseTeam;
import cn.ccrise.spimp.ercs.service.ResponseTeamService;

/**
 * ResponseTeam Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ResponseTeamController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResponseTeamService responseTeamService;

	@RequestMapping(value = "/ercs/response-teams/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(responseTeamService.delete(id));
	}

	@RequestMapping(value = "/ercs/response-teams/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(responseTeamService.get(id));
	}

	@RequestMapping(value = "/ercs/response-teams", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ResponseTeam> page, String teamName) {
		if (!StringUtils.isBlank(teamName)) {
			return new Response(responseTeamService.getPage(page,
					Restrictions.ilike("teamName", teamName, MatchMode.ANYWHERE)));
		}
		return new Response(responseTeamService.getPage(page));
	}

	@RequestMapping(value = "/ercs/response-teams", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ResponseTeam responseTeam) {
		return new Response(responseTeamService.save(responseTeam));
	}

	@RequestMapping(value = "/ercs/response-teams/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ResponseTeam responseTeam, @PathVariable long id) {
		return new Response(responseTeamService.update(responseTeam));
	}
}