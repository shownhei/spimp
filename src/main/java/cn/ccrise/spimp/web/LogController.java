/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.web;

import java.sql.Date;
import java.util.ArrayList;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.entity.LogEntity;
import cn.ccrise.ikjp.core.security.entity.LogEntity.Level;
import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;

import com.google.common.collect.Lists;

/**
 * 日志控制器。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class LogController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;

	@RequestMapping(value = "/system/logs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(logEntityServiceImpl.get(id));
	}

	@RequestMapping(value = "/system/logs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<LogEntity> page, Date startDate, Date endDate, Level level) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (startDate != null) {
			criterions.add(Restrictions.ge("recordTime", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("recordTime", DateUtils.addSeconds(DateUtils.addDays(endDate, 1), -1)));
		}
		if (level != null) {
			criterions.add(Restrictions.eq("level", level));
		}
		return new Response(logEntityServiceImpl.getPage(page, criterions.toArray(new Criterion[0])));
	}
}