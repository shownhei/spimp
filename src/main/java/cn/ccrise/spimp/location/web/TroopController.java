/*
 * Copyright (C) 2012 CCRISE.
 */
package cn.ccrise.spimp.location.web;

import java.util.List;

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
import cn.ccrise.spimp.location.entity.Troop;
import cn.ccrise.spimp.location.entity.TroopName;
import cn.ccrise.spimp.location.service.TroopService;

import com.google.common.collect.Lists;

/**
 * Troop Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class TroopController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TroopService troopService;

	@RequestMapping(value = "/system/troops/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(troopService.delete(id));
	}

	@RequestMapping(value = "/system/troops/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(troopService.get(id));
	}

	@RequestMapping(value = "/system/troop", method = RequestMethod.GET)
	public String index() {
		return "system/troop/index";
	}

	@RequestMapping(value = "/system/troops", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Troop> page) {
		return new Response(troopService.getPage(page));
	}

	@RequestMapping(value = "/system/troops", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Troop troop) {
		return new Response(troopService.save(troop));
	}

	@RequestMapping(value = "/system/troops-select", method = RequestMethod.GET)
	@ResponseBody
	public Response troop() {
		List<TroopName> troops = Lists.newArrayList();
		for (Troop troop : troopService.getAll()) {
			TroopName troopName = new TroopName();
			troopName.setName(troop.getName() + ":" + troop.getStartTime() + "点-" + troop.getEndTime() + "点");
			troops.add(troopName);
		}
		return new Response(troops);
	}

	@RequestMapping(value = "/system/troops/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Troop troop, @PathVariable long id) {
		return new Response(troopService.update(troop));
	}
}