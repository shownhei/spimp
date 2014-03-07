/*
 * Copyright (C) 2010-2020 CCRISE.
 */
package cn.ccrise.spimp.electr.web;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import net.sf.jxls.exception.ParsePropertyException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.ccrise.ikjp.core.util.Page;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.electr.entity.Equipment;
import cn.ccrise.spimp.electr.service.AccessoryService;
import cn.ccrise.spimp.electr.service.EquipmentService;
import cn.ccrise.spimp.system.web.UploadController;
import cn.ccrise.spimp.util.ExcelHelper;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * Equipment Controller。
 * 
 */
@Controller
public class EquipmentController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AccessoryService accessoryService;
	@Autowired
	private EquipmentService equipmentService;

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Response delete(@PathVariable long id, HttpSession httpSession) {
		return new Response(equipmentService.deleteEquipment(id, httpSession));
	}

	@RequestMapping(value = "/electr/equipment/equipments/export-excel", method = RequestMethod.GET)
	public void exportExcel(HttpServletResponse response, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) throws Exception {
		Page<Equipment> page = new Page<Equipment>();
		page.setPageSize(100000);
		page = equipmentService.pageQuery(page, deviceClass, deviceCategory, deviceType, serviceEnvironment,
				deviceArea, stowedPosition);

		String[] headers = { "设备分类", "设备种类", "设备类型", "设备名称", "设备型号", "使用环境", "所属区域", "存放地点", "用途", "生产厂家", "设备编号",
				"出厂编号", "出厂日期", "包机人", "班长/组长", "三开一防锁", "数量", "图片路径", "说明书路径" };

		HSSFWorkbook wb = new ExcelHelper<Equipment>().genExcel("定期检修设置管理 - 安全生产综合管理平台", headers, page.getResult(),
				"yyyy-MM-dd");
		response.setContentType("application/force-download");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment;filename=" + URLEncoder.encode("定期检修设置管理 - 安全生产综合管理平台", "UTF-8") + ".xls");

		OutputStream ouputStream = response.getOutputStream();
		wb.write(ouputStream);
		ouputStream.flush();
		ouputStream.close();
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Response get(@PathVariable long id) {
		return new Response(equipmentService.get(id));
	}

	@RequestMapping(value = "/electr/equipment/info", method = RequestMethod.GET)
	public ModelAndView getPlan(Long equipmentId) {
		HashMap<String, Object> root = new HashMap<String, Object>();
		if (equipmentId != null) {
			Equipment instance = equipmentService.get(equipmentId);
			root.put("equipment", instance);
			root.put("accessories", accessoryService.find(Restrictions.eq("equipmentId", instance.getId())));
		}
		return new ModelAndView("electr/equipment/detail/info", root);
	}

	@RequestMapping(value = "/electr/equipment/detail", method = RequestMethod.GET)
	public String index() {
		return "electr/equipment/detail/index";
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.GET)
	@ResponseBody
	public Response page(Page<Equipment> page, Long deviceClass, Long deviceCategory, Long deviceType,
			Long serviceEnvironment, Long deviceArea, Long stowedPosition) {
		page = equipmentService.pageQuery(page, deviceClass, deviceCategory, deviceType, serviceEnvironment,
				deviceArea, stowedPosition);
		return new Response(page);
	}

	@RequestMapping(value = "/electr/equipment/equipments", method = RequestMethod.POST)
	@ResponseBody
	public Response save(@Valid @RequestBody Equipment equipment) {
		return new Response(equipmentService.save(equipment));
	}

	@RequestMapping(value = "/electr/equipment/equipments/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public Response update(@Valid @RequestBody Equipment equipment, @PathVariable long id) {
		return new Response(equipmentService.update(equipment));
	}

	@RequestMapping(value = "/electr/equipment/equipments/upload", method = RequestMethod.POST)
	public void upload(@RequestParam MultipartFile file, String callBackFunction, HttpSession httpSession,
			HttpServletResponse response, final String uploadPath) throws IOException {
		// 生成文件路径
		String filePath = generatePath(file);

		String defaultUploadPath = null;
		// 自定义上传目录
		if (!Strings.isNullOrEmpty(uploadPath)) {
			if (uploadPath.charAt(uploadPath.length() - 1) != '/') {
				defaultUploadPath = uploadPath + '/';
			} else {
				defaultUploadPath = uploadPath;
			}
		} else {
			defaultUploadPath = UploadController.DEFAULT_PATH;
		}

		// 获取上传目录的真实路径
		String uploadRealPath = httpSession.getServletContext().getRealPath(defaultUploadPath);
		filePath = replaceChars(filePath);
		// 写入文件
		final File newFile = new File(uploadRealPath + "/" + filePath);
		FileUtils.writeByteArrayToFile(newFile, file.getBytes());
		try {
			equipmentService.importFormExcel(newFile.getAbsolutePath());
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 设置响应
		response.setContentType("text/html");
		response.getWriter().write(
				"<script>parent."
						+ callBackFunction
						+ "("
						+ JSON.toJSONString(new Response(new String(
								(defaultUploadPath.replaceFirst("/WEB-INF", "") + filePath)))) + ")</script>");
		response.flushBuffer();
	}

	private String generatePath(MultipartFile file) {
		Timestamp now = new Timestamp(System.currentTimeMillis());

		// 根据年月生成文件夹
		String folder = new SimpleDateFormat("yyyy-MM").format(now);

		// 重新生成文件名，文件名+日期+时间+6位随机数
		String fullFileName = file.getOriginalFilename();

		String fileName, type = "";
		if (fullFileName.lastIndexOf(".") != -1) {
			fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
			type = fullFileName.substring(fullFileName.lastIndexOf("."));
		} else {
			fileName = fullFileName;
		}

		String newFullFileName = fileName + "-" + new SimpleDateFormat("ddHHmmss").format(now) + "-"
				+ RandomStringUtils.randomNumeric(6) + type.toLowerCase();

		return folder + "/" + newFullFileName;
	}

	private String replaceChars(String srcString) {
		return srcString.replace("[", "【").replace("]", "】");
	}
}