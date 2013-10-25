/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.util.List;

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
import cn.ccrise.spimp.ercs.entity.EmergencyResource;
import cn.ccrise.spimp.ercs.entity.ResourceUseRecord;
import cn.ccrise.spimp.ercs.service.EmergencyResourceService;
import cn.ccrise.spimp.ercs.service.ResourceUseRecordService;

/**
 * ResourceUseRecord Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ResourceUseRecordController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceUseRecordService resourceUseRecordService;
	@Autowired
	private EmergencyResourceService emergencyResourceService;

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(resourceUseRecordService.delete(id));
	}

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(resourceUseRecordService.get(id));
	}

	@RequestMapping(value = "/ercs/resource-use-records", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<ResourceUseRecord> page, String resourceName) {
		if (!StringUtils.isBlank(resourceName)) {
			List<EmergencyResource> list = emergencyResourceService.find(Restrictions.ilike("resourceName",
					resourceName, MatchMode.ANYWHERE));
			if (list != null && list.size() > 0) {
				return new Response(resourceUseRecordService.getPage(page, Restrictions.in("resource", list)));
			}
		}
		return new Response(resourceUseRecordService.getPage(page));
	}

	@RequestMapping(value = "/ercs/resource-use-records", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody ResourceUseRecord resourceUseRecord) {
		return new Response(resourceUseRecordService.save(resourceUseRecord));
	}

	@RequestMapping(value = "/ercs/resource-use-records/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody ResourceUseRecord resourceUseRecord, @PathVariable long id) {
		return new Response(resourceUseRecordService.update(resourceUseRecord));
	}
}