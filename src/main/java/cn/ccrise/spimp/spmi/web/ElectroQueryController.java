/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.Criterion;
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
import cn.ccrise.spimp.spmi.entity.ElectroQuery;
import cn.ccrise.spimp.spmi.service.ElectroQueryService;

/**
 * ElectroQuery Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ElectroQueryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ElectroQueryService electroQueryService;

	@RequestMapping(value = "/spmi/electro/electro-queries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(electroQueryService.delete(id));
	}

	@RequestMapping(value = "/spmi/electro/electro-queries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(electroQueryService.get(id));
	}

	@RequestMapping(value = "/spmi/electro/electro-queries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ElectroQuery> page, Date startDate, Date endDate, String deviceVersion,
			String electroVersion) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (startDate != null) {
			criterions.add(Restrictions.ge("rolloutDate", startDate));
		}
		if (endDate != null) {
			criterions.add(Restrictions.le("rolloutDate", DateUtils.addSeconds(DateUtils.addDays(endDate, 1), -1)));
		}
		if (deviceVersion != null) {
			criterions.add(Restrictions.like("deviceVersion", deviceVersion, MatchMode.ANYWHERE));
		}
		if (electroVersion != null) {
			criterions.add(Restrictions.like("electroVersion", electroVersion, MatchMode.ANYWHERE));
		}

		return new Response(electroQueryService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/electro/electro-queries", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ElectroQuery electroQuery) {
		return new Response(electroQueryService.save(electroQuery));
	}

	@RequestMapping(value = "/spmi/electro/electro-queries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ElectroQuery electroQuery, @PathVariable long id) {
		return new Response(electroQueryService.update(electroQuery));
	}
}