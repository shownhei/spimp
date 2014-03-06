/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.ercs.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.criterion.Criterion;
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

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.ercs.entity.EmergencyPlanInstance;
import cn.ccrise.spimp.ercs.entity.Rescuers;
import cn.ccrise.spimp.ercs.entity.ResponseTeam;
import cn.ccrise.spimp.ercs.entity.ResponseTeamMember;
import cn.ccrise.spimp.ercs.entity.SpeciaList;
import cn.ccrise.spimp.ercs.service.EmergencyPlanInstanceService;
import cn.ccrise.spimp.ercs.service.RescuersService;
import cn.ccrise.spimp.ercs.service.ResponseTeamMemberService;
import cn.ccrise.spimp.ercs.service.SpeciaListService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.service.DictionaryService;
import cn.ccrise.spimp.util.AlarmMessage;
import cn.ccrise.spimp.util.ErcsDeferredResult;

/**
 * EmergencyPlanInstance Controller。 应急预案的实例
 */
@Controller
public class EmergencyPlanInstanceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private EmergencyPlanInstanceService emergencyPlanInstanceService;
	@Autowired
	private RescuersService rescuersService;
	@Autowired
	private SpeciaListService speciaListService;
	@Autowired
	private ResponseTeamMemberService responseTeamMemberService;

	@RequestMapping(value = "/ercs/emergency-plan-instances/waitrefresh", method = RequestMethod.GET)
	@ResponseBody
	public ErcsDeferredResult<Object> asynGet(HttpServletRequest request) {
		ErcsDeferredResult<Object> deferredResult = new ErcsDeferredResult<Object>();
		AlarmMessage message = new AlarmMessage();
		message.setSessionId(request.getSession().getId());
		deferredResult.setRecordTime(new Timestamp(System.currentTimeMillis()));
		emergencyPlanInstanceService.wait(deferredResult);
		return deferredResult;
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(emergencyPlanInstanceService.delete(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(emergencyPlanInstanceService.get(id));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<EmergencyPlanInstance> page, Long accidentType, HttpSession httpSession,
			Boolean isManage) {
		Object obj = httpSession.getAttribute(PropertiesUtils.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		Account loginAccount = null;
		HashMap<Long, ResponseTeam> map = new HashMap<Long, ResponseTeam>();
		if ((isManage == null || !isManage.booleanValue()) && obj != null) {

			loginAccount = (Account) obj;
			List<Rescuers> rescuersList = rescuersService.find(Restrictions.eq("account", loginAccount));
			if (rescuersList != null && rescuersList.size() > 0) {
				ArrayList<Rescuers> rescuerArray = new ArrayList<Rescuers>();
				Iterator<Rescuers> it = rescuersList.iterator();
				Rescuers rescuer = null;
				while (it.hasNext()) {
					rescuer = it.next();
					rescuerArray.add(rescuer);
				}
				List<ResponseTeamMember> teamMemberList = responseTeamMemberService.find(Restrictions.in(
						"normalMember", rescuerArray));
				if (teamMemberList != null && teamMemberList.size() > 0) {
					for (ResponseTeamMember member : teamMemberList) {
						map.put(member.getTeam().getId(), member.getTeam());
					}
				}
			}
			List<SpeciaList> speciaListList = speciaListService.find(Restrictions.eq("account", loginAccount));
			if (speciaListList != null && speciaListList.size() > 0) {
				Iterator<SpeciaList> it = speciaListList.iterator();//
				SpeciaList speciaList = null;
				ArrayList<SpeciaList> speciaListArray = new ArrayList<SpeciaList>();
				while (it.hasNext()) {
					speciaList = it.next();
					speciaListArray.add(speciaList);
				}
				List<ResponseTeamMember> teamMemberList = responseTeamMemberService.find(Restrictions.in(
						"expertMember", speciaListArray));
				if (teamMemberList != null && teamMemberList.size() > 0) {
					for (ResponseTeamMember member : teamMemberList) {
						map.put(member.getTeam().getId(), member.getTeam());
					}
				}
			}//
				// 当前登录账户所属的队组查询完毕

		}

		ArrayList<Criterion> queryCondition = new ArrayList<Criterion>();
		if (accidentType != null) {
			List<Dictionary> result = dictionaryService.find(Restrictions.eq("id", accidentType));
			if (result != null && result.size() > 0) {
				queryCondition.add(Restrictions.eq("emergencyCategory", result.iterator().next()));
			}
		}
		// 添加针对队组属性的查询
		if (map.size() > 0) {
			queryCondition.add(Restrictions.in("team", map.values().toArray(new ResponseTeam[0])));
		}
		return new Response(emergencyPlanInstanceService.getPage(page, queryCondition.toArray(new Criterion[0])));
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody EmergencyPlanInstance emergencyPlanInstance) {
		boolean result = emergencyPlanInstanceService.save(emergencyPlanInstance);
		emergencyPlanInstanceService.notifyRefresh();
		return new Response(result);
	}

	@RequestMapping(value = "/ercs/emergency-plan-instances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody EmergencyPlanInstance emergencyPlanInstance, @PathVariable long id) {
		return new Response(emergencyPlanInstanceService.update(emergencyPlanInstance));
	}
}