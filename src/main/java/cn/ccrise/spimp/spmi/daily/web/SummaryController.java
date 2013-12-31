/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import javax.servlet.http.HttpSession;

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

import cn.ccrise.ikjp.core.security.entity.AccountEntity;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.daily.entity.Summary;
import cn.ccrise.spimp.spmi.daily.service.SummaryService;
import cn.ccrise.spimp.system.service.AccountService;

import com.google.common.base.Strings;

/**
 * Summary Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class SummaryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SummaryService summaryService;
	@Autowired
	private AccountService accountService;

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(summaryService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(summaryService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/summaries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Summary> page, String search) {
		if (!Strings.isNullOrEmpty(search)) {
			page = summaryService.getPage(page, Restrictions.ilike("name", search, MatchMode.ANYWHERE));
			if (page.getResult().size() > 0) {
				for (Summary summary : page.getResult()) {
					AccountEntity account = accountService.get(summary.getUploaderId());
					summary.setUploader(account.getPrincipal());
				}
			}
			return new Response(page);
		} else {
			page = summaryService.getPage(page);
			if (page.getResult().size() > 0) {
				for (Summary summary : page.getResult()) {
					AccountEntity account = accountService.get(summary.getUploaderId());
					summary.setUploader(account.getPrincipal());
				}
			}
			return new Response(page);
		}
	}

	@RequestMapping(value = "/spmi/daily/summaries", method = RequestMethod.POST)
	@ResponseBody
	public Response save(HttpSession httpSession, @RequestBody Summary summary) {
		AccountEntity account = WebUtils.getAccount(httpSession);

		summary.setUploaderId(account.getId());

		return new Response(summaryService.save(summary));
	}

	@RequestMapping(value = "/spmi/daily/summaries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@RequestBody Summary summary, @PathVariable long id) {
		summary.setId(id);
		summary.setUploaderId(summaryService.get(id).getUploaderId());
		return new Response(summaryService.merge(summary));
	}
}