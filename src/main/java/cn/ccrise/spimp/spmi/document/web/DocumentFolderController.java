/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.web;

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

import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.document.entity.DocumentFolder;
import cn.ccrise.spimp.spmi.document.service.DocumentFolderService;
/**
 * DocumentFolder Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class DocumentFolderController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DocumentFolderService documentFolderService;

	@RequestMapping(value = "/spimp/document/document-folders/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(documentFolderService.deleteFolder(id));
	}

	@RequestMapping(value = "/spimp/document/document-folders/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(documentFolderService.get(id));
	}

	@RequestMapping(value = "/spimp/document/document-folder", method = RequestMethod.GET)
	public String index() {
		return "spimp/document/document-folder/index";
	}

	@RequestMapping(value = "/spimp/document/document-folders", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<DocumentFolder> page,Long parentId,HttpSession httpSession) {
		page.setPageSize(900000);
		page = documentFolderService.pageQuery(page,parentId,httpSession);
		return new Response(page);
	}

	@RequestMapping(value = "/spimp/document/document-folders", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody DocumentFolder documentFolder,HttpSession httpSession) {
		documentFolder.setAccount(WebUtils.getAccount(httpSession));
		return new Response(documentFolderService.save(documentFolder));
	}

	@RequestMapping(value = "/spimp/document/document-folders/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody DocumentFolder documentFolder, @PathVariable long id) {
		return new Response(documentFolderService.update(documentFolder));
	}
}