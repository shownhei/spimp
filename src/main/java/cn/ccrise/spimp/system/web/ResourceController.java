/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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

import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.ikjp.core.security.service.impl.ResourceEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Response;

/**
 * 资源控制器.
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class ResourceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ResourceEntityServiceImpl resourceEntityServiceImpl;

	@RequestMapping(value = "/system/resources/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id, Boolean order) {
		if (BooleanUtils.isTrue(order)) {
			return new Response(resourceEntityServiceImpl.get(id, true));
		} else {
			return new Response(resourceEntityServiceImpl.get(id));
		}
	}

	@RequestMapping(value = "/system/resources/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@RequestBody(required = false) ResourceEntity resource, @PathVariable long id, String action) {
		ResourceEntity resourceEntity = resourceEntityServiceImpl.get(id);
		if (StringUtils.equals(action, "up")) {
			ResourceEntity upResourceEntity = resourceEntityServiceImpl.findUnique(
					Restrictions.eq("parentId", resourceEntity.getParentId()),
					Restrictions.eq("sequence", resourceEntity.getSequence() - 1));
			upResourceEntity.setSequence(resourceEntity.getSequence());
			resourceEntity.setSequence(resourceEntity.getSequence() - 1);

			resourceEntityServiceImpl.update(upResourceEntity);
			resourceEntityServiceImpl.update(resourceEntity);
			return new Response(true);
		} else if (StringUtils.equals(action, "down")) {
			ResourceEntity downResourceEntity = resourceEntityServiceImpl.findUnique(
					Restrictions.eq("parentId", resourceEntity.getParentId()),
					Restrictions.eq("sequence", resourceEntity.getSequence() + 1));
			downResourceEntity.setSequence(resourceEntity.getSequence());
			resourceEntity.setSequence(resourceEntity.getSequence() + 1);

			resourceEntityServiceImpl.update(downResourceEntity);
			resourceEntityServiceImpl.update(resourceEntity);
			return new Response(true);
		} else {
			ResourceEntity resourceEntityInDB = resourceEntityServiceImpl.get(resource.getId());
			resourceEntityInDB.setName(resource.getName());
			return new Response(resourceEntityServiceImpl.update(resourceEntityInDB));
		}
	}
}
