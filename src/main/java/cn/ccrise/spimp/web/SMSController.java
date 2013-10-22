/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.web;

import java.util.ArrayList;

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
import cn.ccrise.spimp.entity.SMS;
import cn.ccrise.spimp.service.SMSService;

import com.google.common.collect.Lists;

/**
 * SMS Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class SMSController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SMSService sMSService;

	@RequestMapping(value = "/query/smses/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@RequestBody SMS sMS, @PathVariable long id) {
		return new Response(sMSService.delete(id));
	}

	@RequestMapping(value = "/query/smses/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(sMSService.get(id));
	}

	@RequestMapping(value = "/query/smses", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<SMS> page, String status, String mobileNumber, String receiveName, String sendName,
			String content) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (null != status) {
			criterions.add(Restrictions.eq("status", status));
		}
		if (null != mobileNumber) {
			criterions.add(Restrictions.ilike("mobileNumber", mobileNumber, MatchMode.ANYWHERE));
		}
		if (null != receiveName) {
			criterions.add(Restrictions.ilike("receiveName", receiveName, MatchMode.ANYWHERE));
		}
		if (null != sendName) {
			criterions.add(Restrictions.ilike("sendName", sendName, MatchMode.ANYWHERE));
		}
		if (null != content) {
			criterions.add(Restrictions.ilike("content", content, MatchMode.ANYWHERE));
		}
		return new Response(sMSService.getPage(page, criterions.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/query/smses", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@RequestBody SMS sMS) {
		return new Response(sMSService.save(sMS));
	}

	@RequestMapping(value = "/query/smses/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@RequestBody SMS sMS) {
		return new Response(sMSService.update(sMS));
	}
}