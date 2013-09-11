/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
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
import cn.ccrise.spimp.ercs.entity.Rescuers;
import cn.ccrise.spimp.ercs.service.RescuersService;

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

	@RequestMapping(value = "/ercs/rescuers/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(rescuersService.delete(id));
	}

	@RequestMapping(value = "/ercs/rescuers/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(rescuersService.get(id));
	}


	@RequestMapping(value = "/ercs/rescuers", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Rescuers> page,String staffName) {
		ArrayList<SimpleExpression> param=new ArrayList<SimpleExpression>();
		if(StringUtils.isNotBlank(staffName)){
			param.add(Restrictions.like("staffName", "%"+staffName+"%"));
		}
		return new Response(rescuersService.getPage(page,param.toArray(new SimpleExpression[0])));
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