/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
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
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.PropertiesUtils;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Maintenance;
import cn.ccrise.spimp.electr.entity.MaintenanceDetail;
import cn.ccrise.spimp.electr.entity.MaterialsPlan;
import cn.ccrise.spimp.electr.service.CarService;
import cn.ccrise.spimp.electr.service.MaintenanceDetailService;
import cn.ccrise.spimp.electr.service.MaintenanceService;
import cn.ccrise.spimp.system.entity.Account;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * 定期维修、日常维修保养。
 * 
 */
@Controller
public class MaintenanceController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CarService carService;
	@Autowired
	private MaintenanceService maintenanceService;
	@Autowired
	private MaintenanceDetailService maintenanceDetailService;

	@RequestMapping(value = "/electr/maintenance/maintenances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceService.deleteMaintenance(id));
	}

	/**
	 * 导出维修单
	 * 
	 * @param httpSession
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping(value = "/electr/maintenance/maintenances/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpSession httpSession, HttpServletResponse response, Long id) throws Exception {
		HashMap<String, Object> root = new HashMap<String, Object>();
		Maintenance maintenance = maintenanceService.findUniqueBy("id", id);
		root.put("maintenance", maintenance);
		List<MaintenanceDetail> details = maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance));
		SimpleDateFormat formater = new SimpleDateFormat("yyyy年MM月dd日");
		String maintenanceDate = formater.format(maintenance.getMaintenanceDate());
		root.put("maintenanceDate", maintenanceDate);
		root.put("details", details);
		new ExcelHelper<MaterialsPlan>().genExcelWithTel(httpSession, response, "electr/maintenance.xls", root,
				maintenance.getCar().getModels() + "无轨胶轮车定期保养记录", new String[] { "材料计划" });
	}

	@RequestMapping(value = "/electr/maintenance/maintenances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceService.get(id));
	}

	/**
	 * 加载日常维修的维修单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/getdailymaintenance", method = RequestMethod.GET)
	public ModelAndView getDailyMaintenance(Long car, Long maintenanceId, Date maintenanceDate,
			String maintenancePeople, Integer maintenanceLevel, HttpSession httpSession) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		if (maintenanceId != null) {
			Maintenance maintenance = maintenanceService.findUniqueBy("id", maintenanceId);
			root.put("maintenance", maintenance);
			root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
		} else {
			Criteria query = maintenanceService.getDAO().getSession().createCriteria(Maintenance.class);
			if (car != null) {
				query.add(Restrictions.eq("car", carService.findUniqueBy("id", car)));
			}
			if (maintenanceDate != null) {
				query.add(Restrictions.eq("maintenanceDate", maintenanceDate));
			}
			if (StringUtils.isNotBlank(maintenancePeople)) {
				query.add(Restrictions.ilike("maintenancePeople", maintenancePeople, MatchMode.ANYWHERE));
			}
			if (maintenanceLevel != null) {
				query.add(Restrictions.eq("maintenanceLevel", maintenanceLevel));
			}
			query.add(Restrictions.eq("maintenanceGroup.id", loginAccount.getGroupEntity().getId()));
			@SuppressWarnings("unchecked")
			List<Maintenance> result = query.list();
			if (result != null && result.size() > 0) {
				Maintenance maintenance = result.get(0);
				root.put("maintenance", maintenance);
				root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
			}
		}
		return new ModelAndView("electr/maintenance/daily/maintenance", root);
	}

	/**
	 * 加载定期维修的维修单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/electr/maintenance/getschedulemaintenance", method = RequestMethod.GET)
	public ModelAndView getScheduleMaintenance(Long car, Long maintenanceId, Date maintenanceDate,
			String maintenancePeople, Integer maintenanceLevel, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		HashMap<String, Object> root = new HashMap<String, Object>();

		if (maintenanceId != null) {
			Maintenance maintenance = maintenanceService.findUniqueBy("id", maintenanceId);
			root.put("maintenance", maintenance);
			root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
		} else {
			Criteria query = maintenanceService.getDAO().getSession().createCriteria(Maintenance.class);
			if (car != null) {
				query.add(Restrictions.eq("car", carService.findUniqueBy("id", car)));
			}
			if (maintenanceDate != null) {
				query.add(Restrictions.eq("maintenanceDate", maintenanceDate));
			}
			if (StringUtils.isNotBlank(maintenancePeople)) {
				query.add(Restrictions.ilike("maintenancePeople", maintenancePeople, MatchMode.ANYWHERE));
			}
			if (maintenanceLevel != null) {
				query.add(Restrictions.eq("maintenanceLevel", maintenanceLevel));
			}
			query.add(Restrictions.eq("maintenanceGroup.id", loginAccount.getGroupEntity().getId()));
			@SuppressWarnings("unchecked")
			List<Maintenance> result = query.list();
			if (result != null && result.size() > 0) {
				Maintenance maintenance = result.get(0);
				root.put("maintenance", maintenance);
				root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
			}
		}
		return new ModelAndView("electr/maintenance/schedule/maintenance", root);
	}

	@RequestMapping(value = "/electr/maintenance/maintenances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Maintenance> page, Long car, String search) {
		page = maintenanceService.pageQuery(page, car, search);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/maintenance/maintenances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Maintenance maintenance, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		maintenance.setMaintenanceGroup(loginAccount.getGroupEntity());
		maintenanceService.save(maintenance);
		return new Response(maintenance);
	}

	@RequestMapping(value = "/electr/maintenance/maintenances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Maintenance maintenance, @PathVariable long id, HttpSession httpSession) {
		Account loginAccount = (Account) httpSession.getAttribute(PropertiesUtils
				.getString(PropertiesUtils.SESSION_KEY_PROPERTY));
		maintenance.setMaintenanceGroup(loginAccount.getGroupEntity());
		return new Response(maintenanceService.update(maintenance));
	}
}