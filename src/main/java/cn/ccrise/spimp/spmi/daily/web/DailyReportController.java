/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import javax.validation.Valid;

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
import cn.ccrise.spimp.spmi.daily.entity.DailyReport;
import cn.ccrise.spimp.spmi.daily.service.DailyReportService;

import com.google.common.base.Strings;

/**
 * DailyReport Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class DailyReportController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DailyReportService dailyReportService;

	@RequestMapping(value = "/spmi/daily/daily-reports/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(dailyReportService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/daily-reports/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(dailyReportService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/daily-report", method = RequestMethod.GET)
	public String index() {
		return "spmi/daily/daily-report/index";
	}

	@RequestMapping(value = "/spmi/daily/daily-reports", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<DailyReport> page, String search) {
		if (!Strings.isNullOrEmpty(search)) {
			return new Response(dailyReportService.getPage(page,
					Restrictions.ilike("issue", search, MatchMode.ANYWHERE)));
		} else {
			return new Response(dailyReportService.getPage(page));
		}

	}

	@RequestMapping(value = "/spmi/daily/daily-reports", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody DailyReport dailyReport) {
		return new Response(dailyReportService.save(dailyReport));
	}

	@RequestMapping(value = "/spmi/daily/daily-reports/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody DailyReport dailyReport, @PathVariable long id) {
		return new Response(dailyReportService.update(dailyReport));
	}
}