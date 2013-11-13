/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.document.web;

import java.sql.Timestamp;

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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.entity.Account;
import cn.ccrise.spimp.spmi.document.entity.Document;
import cn.ccrise.spimp.spmi.document.service.DocumentService;

/**
 * Document Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class DocumentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DocumentService documentService;

	@RequestMapping(value = "/spmi/document/common", method = RequestMethod.GET)
	public ModelAndView commonPage(String code) {
		return modelAndView(code);
	}

	@RequestMapping(value = "/spmi/document/documents/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(documentService.delete(id));
	}

	/**
	 * 开拓队录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/develop", method = RequestMethod.GET)
	public String developIndex() {
		return "spmi/document/develop/index";
	}

	/**
	 * 综掘队录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/dig", method = RequestMethod.GET)
	public String digIndex() {
		return "spmi/document/dig/index";
	}

	/**
	 * 综采队录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/exploit", method = RequestMethod.GET)
	public String exploitIndex() {
		return "spmi/document/exploit/index";
	}

	@RequestMapping(value = "/spmi/document/documents/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(documentService.get(id));
	}

	/**
	 * 机电科录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/machine", method = RequestMethod.GET)
	public String machineIndex() {
		return "spmi/document/machine/index";
	}

	@RequestMapping(value = "/spmi/document/documents", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Document> page, String office, String search, Long projectType, String documentName,
			String keyword, String startDate, String endDate) {
		page = documentService.pageQuery(page, office, search, projectType, documentName, keyword, startDate, endDate);
		return new Response(page);
	}

	/**
	 * 生产技术科录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/produce", method = RequestMethod.GET)
	public String produceIndex() {
		return "spmi/document/produce/index";
	}

	/**
	 * 文档综合查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/query", method = RequestMethod.GET)
	public String queryIndex() {
		return "spmi/document/query/index";
	}

	/**
	 * 安全科录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/safe", method = RequestMethod.GET)
	public String safeIndex() {
		return "spmi/document/safe/index";
	}

	@RequestMapping(value = "/spmi/document/documents", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Document document, HttpSession httpSession) {
		Timestamp current = new Timestamp(System.currentTimeMillis());
		document.setCreateTime(current);
		document.setLastModifyTime(current);

		String curUser = getCurUser(httpSession);
		document.setCreateBy(curUser);
		document.setUpdateBy(curUser);
		return new Response(documentService.save(document));
	}

	/**
	 * 调度室录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/schedule", method = RequestMethod.GET)
	public String scheduleIndex() {
		return "spmi/document/schedule/index";
	}

	@RequestMapping(value = "/spmi/document/documents/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Document document, @PathVariable long id, HttpSession httpSession) {
		document.setLastModifyTime(new Timestamp(System.currentTimeMillis()));
		document.setUpdateBy(getCurUser(httpSession));
		return new Response(documentService.update(document));
	}

	/**
	 * 防治水科录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/water", method = RequestMethod.GET)
	public String waterIndex() {
		return "spmi/document/water/index";
	}

	/**
	 * 通风科录入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/spmi/document/wind", method = RequestMethod.GET)
	public String windIndex() {
		return "spmi/document/wind/index";
	}

	private String getCurUser(HttpSession httpSession) {
		Account account = (Account) WebUtils.getAccount(httpSession);
		String curUser = "系统";
		if (account != null) {
			curUser = account.getRealName();
		}

		return curUser;
	}

	private ModelAndView modelAndView(String tag) {
		ModelAndView modelAndView = new ModelAndView("spmi/document/document/index");
		modelAndView.addObject("office", tag);
		return modelAndView;
	}
}