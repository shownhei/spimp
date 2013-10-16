/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import cn.ccrise.spimp.ercs.entity.UploadedFile;
import cn.ccrise.spimp.ercs.service.UploadedFileService;

/**
 * UploadedFile Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class UploadedFileController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UploadedFileService uploadedFileService;

	@RequestMapping(value = "/ercs/uploaded-files/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(HttpSession httpSession, @PathVariable long id) {
		return new Response(uploadedFileService.deleteFile(httpSession, id));
	}

	@RequestMapping(value = "/ercs/uploaded-files/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(uploadedFileService.get(id));
	}

	@RequestMapping(value = "/ercs/uploaded-files", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<UploadedFile> page) {
		return new Response(uploadedFileService.getPage(page));
	}

	@RequestMapping(value = "/ercs/uploaded-files", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody UploadedFile uploadedFile) {
		return new Response(uploadedFileService.save(uploadedFile));
	}

	@RequestMapping(value = "/ercs/uploaded-files/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody UploadedFile uploadedFile, @PathVariable long id) {
		return new Response(uploadedFileService.update(uploadedFile));
	}
}