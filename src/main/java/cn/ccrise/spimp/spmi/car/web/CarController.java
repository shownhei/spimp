/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.spmi.car.web;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
import cn.ccrise.spimp.spmi.car.entity.Car;
import cn.ccrise.spimp.spmi.car.service.CarService;
import cn.ccrise.spimp.util.ExcelHelper;

/**
 * Car Controller。
 * 
 * @author Panfeng Niu(david.kosoon@gmail.com)
 */
@Controller
public class CarController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CarService carService;

	@RequestMapping(value = "/car/cars/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id) {
		return new Response(carService.delete(id));
	}

	@RequestMapping(value = "/car/cars/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(carService.get(id));
	}

	@RequestMapping(value = "/car/car", method = RequestMethod.GET)
	public String index() {
		return "car/car/index";
	}

	@RequestMapping(value = "/car/cars", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Car> page, String search) {
		page = carService.pageQuery(page, search);
		return new Response(page);
	}

	@RequestMapping(value = "/car/carslist", method = RequestMethod.GET)
	@ResponseBody
	public Response pageList(Page<Car> page, String search) {
		page = carService.pageQuery(page, search);
		return new Response(page.getResult());
	}

	@RequestMapping(value = "/car/cars", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Car car) {
		car.setAddDateTime(new Timestamp(System.currentTimeMillis()));
		return new Response(carService.save(car));
	}

	@RequestMapping(value = "/car/cars/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Car car, @PathVariable long id) {
		return new Response(carService.update(car));
	}

	@RequestMapping(value = "/car/cars/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, String search) throws Exception {
		Page<Car> page = new Page<Car>();
		page.setPageSize(100000);
		page = carService.pageQuery(page, search);

		String[] headers = { "车类", "车型", "车号", "购买日期", "记录时间" };

		HSSFWorkbook wb = new ExcelHelper<Car>().genExcel("防治水信息管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("防治水信息管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}
}