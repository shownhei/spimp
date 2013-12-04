/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.sql.Timestamp;

import javax.validation.Valid;

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
import cn.ccrise.spimp.system.entity.Staff;
import cn.ccrise.spimp.system.service.StaffService;

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
	public Response page(Page<Staff> page) {
		return new Response(staffService.getPage(page));
	}

	@RequestMapping(value = "/system/staffs", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Staff staff) {
		return new Response(staffService.save(staff));
	}

	@RequestMapping(value = "/system/staffs/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Staff staff, @PathVariable long id) {
		staff.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return new Response(staffService.update(staff));
	}
}