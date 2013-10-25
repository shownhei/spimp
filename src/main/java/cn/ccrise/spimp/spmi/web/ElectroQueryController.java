/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import cn.ccrise.spimp.util.DateUtil;

import com.google.common.base.Strings;

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
	public Response page(Page<ElectroQuery> page) {
		return new Response(electroQueryService.getPage(page));
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

	@RequestMapping(value = "/spmi/electro/multi-query", method = RequestMethod.GET)
	@ResponseBody
	public Response query(Page<ElectroQuery> page, ElectroQuery electroQuery, String startTime, String endTime) {
		List<Criterion> criterions = new ArrayList<Criterion>();
		if (!Strings.isNullOrEmpty(startTime)) {
			Date start = DateUtil.String2date(startTime, "yyyy-MM-dd");
			criterions.add(Restrictions.ge("rolloutDate", start));
		}
		if (!Strings.isNullOrEmpty(endTime)) {
			Date end = DateUtil.String2date(endTime, "yyyy-MM-dd");
			criterions.add(Restrictions.lt("rolloutDate", end));
		}
		if (electroQuery.getDeviceVersion() != null) {
			criterions.add(Restrictions.like("deviceVersion", electroQuery.getDeviceVersion(), MatchMode.ANYWHERE));

		}
		if (electroQuery.getElectroVersion() != null) {
			criterions.add(Restrictions.like("electroVersion", electroQuery.getElectroVersion(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getElectricity() != null) {
			criterions.add(Restrictions.like("electricity", electroQuery.getElectricity(), MatchMode.ANYWHERE));

		}
		if (electroQuery.getVoltage() != null) {
			criterions.add(Restrictions.like("voltage", electroQuery.getVoltage(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getPower() != null) {
			criterions.add(Restrictions.like("power", electroQuery.getPower(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getFrequency() != null) {
			criterions.add(Restrictions.like("frequency", electroQuery.getFrequency(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getExplosion() != null) {
			criterions.add(Restrictions.like("explosion", electroQuery.getExplosion(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getMineSecurity() != null) {
			criterions.add(Restrictions.like("mineSecurity", electroQuery.getMineSecurity(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getPhase() != null) {
			criterions.add(Restrictions.like("phase", electroQuery.getPhase(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getRolloutNum() != null) {
			criterions.add(Restrictions.like("rolloutNum", electroQuery.getRolloutNum(), MatchMode.ANYWHERE));
		}
		if (electroQuery.getSize() != null) {
			criterions.add(Restrictions.like("size", electroQuery.getSize(), MatchMode.ANYWHERE));
		}

		return new Response(electroQueryService.getPage(page, criterions.toArray(new Criterion[0])));
	}
}