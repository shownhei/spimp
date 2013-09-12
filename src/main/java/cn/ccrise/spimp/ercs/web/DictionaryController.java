/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.ikjp.core.util.ValidationUtils;
import cn.ccrise.spimp.entity.Account;
import cn.ccrise.spimp.ercs.entity.Dictionary;
import cn.ccrise.spimp.ercs.service.DictionaryService;
import cn.ccrise.spimp.util.ResponseDataFilter;

/**
 * Dictionary Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class DictionaryController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(dictionaryService.delete(id));
	}

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(dictionaryService.get(id));
	}


	@RequestMapping(value = "/ercs/dictionaries", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Dictionary> page, String typeCode,Boolean list,String itemName) {
		if(StringUtils.isBlank(itemName)){
			page = dictionaryService.getPage(page,Restrictions.eq("typeCode", typeCode));
		}else{
			page = dictionaryService.getPage(page,Restrictions.eq("typeCode", typeCode),Restrictions.like("itemName", "%"+itemName+"%"));
		}
		
		if(list!=null && list.booleanValue()){
			return new Response(page.getResult());
		}
		return new Response(page);
	}

	@RequestMapping(value = "/ercs/dictionaries", method = RequestMethod.POST)
	@ResponseBody
	public Response save( @RequestBody Dictionary dictionary) {
//		List<Dictionary> existList=dictionaryService.find(Restrictions.eq("typeCode", dictionary.getTypeCode()),Restrictions.eq("itemName", dictionary.getItemName()));
//		if(existList.size()>0){
//			return response(existList.get(0), false, "update");
//		}
		// 业务验证
//		Account accountInDB = dictionaryService.get(id);
//		// 更新时如果用户名更改，则需要验证用户名唯一性
//		if (!accountInDB.getPrincipal().equals(account.getPrincipal())) {
//			if (accountService.isExist(account.getPrincipal())) {
//				return new Response(ValidationUtils.renderResultMap(
//						"Unique.account.principal", messageSource));
//			}
//		}

//		boolean result = accountService.update(accountInDB, account, account
//				.getGroupEntity().getId(), account.getRoleEntity().getId());
//		return response(accountInDB, result, "update");
		return new Response(dictionaryService.save(dictionary));
	}

	@RequestMapping(value = "/ercs/dictionaries/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Dictionary dictionary,
			@PathVariable long id) {
		return new Response(dictionaryService.update(dictionary));
	}

	private Response response(Dictionary dictionary, boolean result, String messageKey) {
		String[] args = { dictionary.getItemName() };
		if (result) {
			logEntityServiceImpl.info(messageSource.getMessage("dictionary."
					+ messageKey + ".success", args, Locale.getDefault()));
			return new Response(dictionary);
		} else {
			logEntityServiceImpl.warn(messageSource.getMessage("dictionary."
					+ messageKey + ".failure", args, Locale.getDefault()));
			return new Response(false);
		}
	}

	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;
	@Autowired
	private MessageSource messageSource;
}