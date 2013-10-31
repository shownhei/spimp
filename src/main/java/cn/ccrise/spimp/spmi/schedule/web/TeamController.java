/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.schedule.web;

import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import cn.ccrise.spimp.spmi.schedule.entity.Team;
import cn.ccrise.spimp.spmi.schedule.service.TeamService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Team Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class TeamController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TeamService teamService;

	@RequestMapping(value = "/spmi/schedule/teams/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(teamService.delete(id));
	}

	@RequestMapping(value = "/spmi/schedule/teams/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search, Long teamType) throws Exception {
		Page<Team> page = new Page<Team>();
		page.setPageSize(100000);
		page = teamService.pageQuery(page, search, teamType);

		String[] headers = { "队组名称", "队组类型", "人数", "排列顺序" };

		HSSFWorkbook wb = new ExcelHelper<Team>()
				.genExcel("队组管理 - 安全生产综合管理平台", headers, page.getResult(), "yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("队组管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/spmi/schedule/teams/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(teamService.get(id));
	}

	@RequestMapping(value = "/spmi/schedule/team", method = RequestMethod.GET)
	public String index() {
		return "spmi/schedule/team/index";
	}

	@RequestMapping(value = "/spmi/schedule/teams", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Team> page, String search, Long teamType, Boolean list) {
		page = teamService.pageQuery(page, search, teamType);

		if (BooleanUtils.isTrue(list)) {
			return new Response(page.getResult());
		}

		return new Response(page);
	}

	@RequestMapping(value = "/spmi/schedule/teams", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Team team) {
		return new Response(teamService.save(team));
	}

	@RequestMapping(value = "/spmi/schedule/teams/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Team team, @PathVariable long id) {
		return new Response(teamService.update(team));
	}
}