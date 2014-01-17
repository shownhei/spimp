/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.sql.Date;
import java.sql.Timestamp;
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
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.system.entity.Alteration;
import cn.ccrise.spimp.system.entity.Staff;
import cn.ccrise.spimp.system.service.AlterationService;
import cn.ccrise.spimp.system.service.GroupService;
import cn.ccrise.spimp.system.service.StaffService;
import cn.ccrise.spimp.util.ResponseDataFilter;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * Staff Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class StaffController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StaffService staffService;
	@Autowired
	private AlterationService alterationService;
	@Autowired
	private GroupService groupService;
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/system/staffs/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(staffService.delete(id));
	}

	@RequestMapping(value = "/system/staffs/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(staffService.get(id));
	}

	@ExceptionHandler
	@ResponseBody
	public Response handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		return new Response(ValidationUtils.renderResultMap(error.getBindingResult(), messageSource));
	}

	@RequestMapping(value = "/system/staffs", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Staff> page, String search, String q) {
		ArrayList<Criterion> criterions = Lists.newArrayList();
		if (search != null) {
			criterions.add(Restrictions.or(Restrictions.ilike("name", search, MatchMode.ANYWHERE),
					Restrictions.ilike("identityCard", search, MatchMode.ANYWHERE)));
		}
		if (q != null) {
			List<Staff> results = staffService.find(Restrictions.or(Restrictions.ilike("name", q, MatchMode.ANYWHERE),
					Restrictions.ilike("identityCard", q, MatchMode.ANYWHERE)));

			// 过滤不必有的数据
			for (Staff staff : results) {
				ResponseDataFilter.filter(staff.getGroupEntity());
			}

			return new Response(results);
		}

		staffService.getPage(page, criterions.toArray(new Criterion[0]));

		// 过滤不必有的数据
		for (Staff staff : page.getResult()) {
			ResponseDataFilter.filter(staff.getGroupEntity());
		}

		return new Response(page);
	}

	@RequestMapping(value = "/system/staffs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Staff staff) {
		return new Response(staffService.save(staff));
	}

	@RequestMapping(value = "/system/staffs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Staff staff, @PathVariable long id) {
		Staff staffInDb = staffService.get(id);

		staff.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		/*
		 * 变更记录：当用工类别/职务职称/岗位/兼职岗位/所属机构发生变化时，保存一条变更记录。
		 * 内容为：（日期）（姓名）的个人信息发生如下变更：（变化属性）由（变化前内容）变更为（变化后内容）。
		 */
		Date today = new Date(System.currentTimeMillis());

		List<String> contents = Lists.newArrayList();
		if (!staffInDb.getCategory().equals(staff.getCategory())) { // 用工类别
			if (StringUtils.isBlank(staffInDb.getCategory())) {
				contents.add("用工类别变更为[" + staff.getCategory() + "]");
			} else {
				contents.add("用工类别由[" + staffInDb.getCategory() + "]变更为[" + staff.getCategory() + "]");
			}
		}
		if (!staffInDb.getDuty().equals(staff.getDuty())) { // 职务职称
			if (StringUtils.isBlank(staffInDb.getDuty())) {
				contents.add("职务职称变更为[" + staff.getDuty() + "]");
			} else {
				contents.add("职务职称由[" + staffInDb.getDuty() + "]变更为[" + staff.getDuty() + "]");
			}
		}
		if (!staffInDb.getPost().equals(staff.getPost())) { // 岗位
			if (StringUtils.isBlank(staffInDb.getPost())) {
				contents.add("岗位变更为[" + staff.getPost() + "]");
			} else {
				contents.add("岗位由[" + staffInDb.getPost() + "]变更为[" + staff.getPost() + "]");
			}
		}
		if (!staffInDb.getPartTime().equals(staff.getPartTime())) { // 兼职岗位
			if (StringUtils.isBlank(staffInDb.getPartTime())) {
				contents.add("兼职岗位变更为[" + staff.getPartTime() + "]");
			} else {
				contents.add("兼职岗位由[" + staffInDb.getPartTime() + "]变更为[" + staff.getPartTime() + "]");
			}
		}
		if (!staffInDb.getGroupEntity().getId().equals(staff.getGroupEntity().getId())) { // 所属机构
			if (staffInDb.getGroupEntity() == null) {
				contents.add("所属机构变更为[" + groupService.get(staff.getGroupEntity().getId()).getName() + "]");
			} else {
				contents.add("所属机构由[" + staffInDb.getGroupEntity().getName() + "]变更为["
						+ groupService.get(staff.getGroupEntity().getId()).getName() + "]");
			}
		}

		staff.setAlterations(staffInDb.getAlterations()); // 前端没有将变更记录提交过来，需要重新设置
		if (!contents.isEmpty()) {
			Alteration alteration = new Alteration();
			alteration.setContent(Joiner.on("，").join(contents) + "。");
			alteration.setChangeDate(today);
			alterationService.save(alteration);

			staff.getAlterations().add(alteration);
		}

		return new Response(staffService.merge(staff));
	}
}