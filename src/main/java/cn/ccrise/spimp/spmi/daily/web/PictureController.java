/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.daily.web;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import cn.ccrise.ikjp.core.security.service.util.WebUtils;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.service.UploadedFileService;
import cn.ccrise.spimp.spmi.daily.entity.Picture;
import cn.ccrise.spimp.spmi.daily.service.PictureService;
import cn.ccrise.spimp.system.entity.Account;

import com.google.common.base.Strings;

/**
 * Picture Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class PictureController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PictureService pictureService;
	@Autowired
	private UploadedFileService uploadedFileService;

	@RequestMapping(value = "/spmi/daily/pictures/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(pictureService.delete(id));
	}

	@RequestMapping(value = "/spmi/daily/pictures/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(pictureService.get(id));
	}

	@RequestMapping(value = "/spmi/daily/pictures/{id}/count", method = RequestMethod.GET)
	@ResponseBody
	public Response getCount(@PathVariable long id) {
		List<Picture> result = pictureService.findBy("groupId", id);
		return new Response(result.size());
	}

	@RequestMapping(value = "/spmi/daily/pictures", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Picture> page, Long groupId, String search) {
		if (!Strings.isNullOrEmpty(search)) {
			return new Response(pictureService.getPage(page, Restrictions.eq("groupId", groupId),
					Restrictions.ilike("name", search, MatchMode.ANYWHERE)));
		}
		return new Response(pictureService.getPage(page, Restrictions.eq("groupId", groupId)));
	}

	@RequestMapping(value = "/spmi/daily/pictures", method = RequestMethod.POST)
	@ResponseBody
	public Response save(HttpSession httpSession, @Valid @RequestBody Picture picture) {
		Account account = (Account)WebUtils.getAccount(httpSession);
		picture.setUploaderId(account.getId());
		picture.setUploader(account.getRealName());
		logger.debug("{}",account.getRealName());
		return new Response(pictureService.save(picture));
	}

	@RequestMapping(value = "/spmi/daily/pictures/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Picture picture, @PathVariable long id) {
		return new Response(pictureService.update(picture));
	}
}