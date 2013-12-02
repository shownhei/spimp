/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.spmi.car.entity.Maintenance;
import cn.ccrise.spimp.spmi.car.service.CarService;
import cn.ccrise.spimp.spmi.car.service.MaintenanceDetailService;
import cn.ccrise.spimp.spmi.car.service.MaintenanceService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Maintenance Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
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

	@RequestMapping(value = "/car/maintenance/maintenances/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(maintenanceService.deleteMaintenance(id));
	}

	@RequestMapping(value = "/car/maintenance/maintenances/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(maintenanceService.get(id));
	}

	/**
	 * 日常维修的界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/maintenance/daily", method = RequestMethod.GET)
	public String daily() {
		return "car/maintenance/daily/index";
	}

	/**
	 * 加载日常维修的维修单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/maintenance/getdailymaintenance", method = RequestMethod.GET)
	public ModelAndView getDailyMaintenance(Long car, Long maintenanceId, Date maintenanceDate,
			String maintenancePeople, Integer maintenanceLevel) {
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
			@SuppressWarnings("unchecked")
			List<Maintenance> result = query.list();
			if (result != null && result.size() > 0) {
				Maintenance maintenance = result.get(0);
				root.put("maintenance", maintenance);
				root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
			}
		}
		return new ModelAndView("car/maintenance/daily/maintenance", root);
	}

	/**
	 * 加载定期维修的维修单
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/maintenance/getschedulemaintenance", method = RequestMethod.GET)
	public ModelAndView getScheduleMaintenance(Long car, Long maintenanceId, Date maintenanceDate,
			String maintenancePeople, Integer maintenanceLevel) {
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
			@SuppressWarnings("unchecked")
			List<Maintenance> result = query.list();
			if (result != null && result.size() > 0) {
				Maintenance maintenance = result.get(0);
				root.put("maintenance", maintenance);
				root.put("details", maintenanceDetailService.find(Restrictions.eq("maintenance", maintenance)));
			}
		}
		return new ModelAndView("car/maintenance/schedule/maintenance", root);
	}

	/**
	 * 定期维修的界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/car/maintenance/schedule", method = RequestMethod.GET)
	public String schedule() {
		return "car/maintenance/schedule/index";
	}

	@RequestMapping(value = "/car/maintenance/maintenances", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Maintenance> page, Long car, String search) {
		page = maintenanceService.pageQuery(page, car, search);
		return new Response(page);
	}

	@RequestMapping(value = "/car/maintenance/maintenances", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Maintenance maintenance) {
		maintenanceService.save(maintenance);
		return new Response(maintenance);
	}

	@RequestMapping(value = "/car/maintenance/maintenances/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Maintenance maintenance, @PathVariable long id) {
		return new Response(maintenanceService.update(maintenance));
	}

	@RequestMapping(value = "/car/maintenance/maintenances/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long car, String search) throws Exception {
		Page<Maintenance> page = new Page<Maintenance>();
		page.setPageSize(100000);
		page = maintenanceService.pageQuery(page, car, search);

		String[] headers = { "车牌号", "保养类别", "保养日期", "保养人", "验收人" };

		HSSFWorkbook wb = new ExcelHelper<Maintenance>().genExcel("故障管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("故障管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}