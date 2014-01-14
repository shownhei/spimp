/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

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
import cn.ccrise.spimp.spmi.daily.entity.Folder;
import cn.ccrise.spimp.spmi.daily.service.FolderService;

/**
 * Folder Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class FolderController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private FolderService folderService;

	@RequestMapping(value = "/spmi/daily/folders/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		Folder folder = folderService.get(id);
		return new Response(folderService.associatedDelete(folder));
	}

	@RequestMapping(value = "/spmi/daily/folders/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(folderService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/folders", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Folder> page) {
		return new Response(folderService.getPage(page));
	}

	@RequestMapping(value = "/spmi/daily/folders", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Folder folder) {
		return new Response(folderService.associatedSave(folder));
	}

	@RequestMapping(value = "/spmi/daily/folders/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Folder folder, @PathVariable long id) {
		return new Response(folderService.associatedUpdate(folder));
	}
}