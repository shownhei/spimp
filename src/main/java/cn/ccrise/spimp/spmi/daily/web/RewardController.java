/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import cn.ccrise.spimp.spmi.daily.entity.Reward;
import cn.ccrise.spimp.spmi.daily.service.RewardService;

/**
 * Reward Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class RewardController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RewardService rewardService;

	@RequestMapping(value = "/spmi/daily/rewards/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(rewardService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/rewards/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(rewardService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/rewards", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Reward> page,String category, String search,Date startDate,Date endDate) {
		List<Criterion> criterions = new ArrayList<Criterion>();

		if (StringUtils.isNotBlank(category)) {
			criterions.add(Restrictions.eq("category", category));
		}
		if (StringUtils.isNotBlank(search)) {
			criterions.add(Restrictions.ilike("name", search, MatchMode.ANYWHERE));
		}
		if(startDate!=null){
			criterions.add(Restrictions.ge("rewardDate",startDate));
		}
		if(endDate!=null){
			criterions.add(Restrictions.le("rewardDate", endDate));
		}
		return new Response(rewardService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/spmi/daily/rewards", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Reward reward) {
		return new Response(rewardService.save(reward));
	}

	@RequestMapping(value = "/spmi/daily/rewards/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Reward reward, @PathVariable long id) {
		return new Response(rewardService.update(reward));
	}
}