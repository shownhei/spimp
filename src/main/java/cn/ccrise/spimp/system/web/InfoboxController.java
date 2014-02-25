/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.entity.ResourceEntity;
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.daily.entity.Plan;
import cn.ccrise.spimp.spmi.daily.entity.Plan.PlanStatus;
import cn.ccrise.spimp.spmi.daily.entity.Reform;
import cn.ccrise.spimp.spmi.daily.entity.Reform.ReformStatus;
import cn.ccrise.spimp.spmi.daily.web.PlanController;
import cn.ccrise.spimp.spmi.daily.web.ReformController;
import cn.ccrise.spimp.system.web.entity.Infobox;

import com.google.common.collect.Lists;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class InfoboxController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PlanController planController;
	@Autowired
	private ReformController reformController;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/infobox/pull", method = RequestMethod.GET)
	@ResponseBody
	public Response pullInfobox(HttpSession httpSession) {
		List<Infobox> result = Lists.newArrayList();

		// 未指派的工作安排
		Page<Plan> planPage = new Page<>(Integer.MAX_VALUE);
		planPage = (Page<Plan>) planController.page(planPage, httpSession, null, null, null, null, null,
				PlanStatus.未指派, null, null, null).getData();
		result.add(new Infobox("infobox-green", "icon-check", planPage.getTotalCount() + "条", "未指派的工作安排", getRealLink(
				httpSession, "/daily/plan")));

		// 已执行的整改通知单
		Page<Reform> reformPage = new Page<>(Integer.MAX_VALUE);
		reformPage = (Page<Reform>) reformController.page(reformPage, httpSession, null, null, null, ReformStatus.已执行)
				.getData();
		result.add(new Infobox("infobox-blue2", "icon-file-alt", reformPage.getTotalCount() + "条", "已执行的整改通知单",
				getRealLink(httpSession, "/daily/reform")));

		return new Response(result);
	}

	private String getRealLink(HttpSession httpSession, String link) {
		List<ResourceEntity> roleResourceEntities = WebUtils.getRole(httpSession).getResourceEntities();
		String realLink = "";

		for (ResourceEntity resourceEntity : roleResourceEntities) {
			if (resourceEntity.getUri().contains(link)) {
				realLink = resourceEntity.getUri();
				return realLink;
			}
		}

		return realLink;
	}
}
