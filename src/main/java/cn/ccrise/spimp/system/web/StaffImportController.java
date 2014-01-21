/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.ccrise.ikjp.core.security.entity.GroupEntity;
import cn.ccrise.ikjp.core.security.service.impl.GroupEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.system.entity.Dictionary;
import cn.ccrise.spimp.system.entity.Staff;
import cn.ccrise.spimp.system.service.DictionaryService;
import cn.ccrise.spimp.system.service.StaffService;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class StaffImportController {
	private static final String DATA_IMPORT_PATH = "/WEB-INF/uploads/import/";
	private static final int RANDOM_NUMERIC_COUNT = 6;
	private static final String XLS_CONTENT_TYPE = "application/vnd.ms-excel";
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GroupEntityServiceImpl groupEntityServiceImpl;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private StaffService staffService;

	@RequestMapping(value = { "/download/staff-template" }, method = RequestMethod.GET)
	public void downloadStffTemplate(HttpServletResponse response, HttpSession httpSession) {
		String uploadRealPath = httpSession.getServletContext().getRealPath("/WEB-INF/downloads/StaffTemplate.xls");
		File file = new File(uploadRealPath);
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			response.setHeader(
					"Content-Disposition",
					"attachment; filename="
							+ URLEncoder.encode(
									"人员信息导入模板"
											+ DateFormat.getDateInstance(2, Locale.CHINA).format(
													new Date(System.currentTimeMillis())) + ".xls", "utf-8"));
			IOUtils.copy(fileInputStream, response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/system/import/staff", method = RequestMethod.POST)
	public void importData(@RequestParam MultipartFile file, HttpSession httpSession, HttpServletResponse response)
			throws IOException {
		// 文件格式必须为xls
		logger.debug("{}", file.getContentType());
		if (!file.getContentType().contains(XLS_CONTENT_TYPE)) {
			response(response, new Response(false));
			return;
		}

		Stopwatch stopwatch = new Stopwatch().start();
		logger.info("开始上传文件");
		// 生成文件路径
		String filePath = generatePath(file);

		// 获取上传目录的真实路径
		String uploadRealPath = httpSession.getServletContext().getRealPath(DATA_IMPORT_PATH);

		// 写入文件
		File newFile = new File(uploadRealPath + "/" + filePath);
		FileUtils.writeByteArrayToFile(newFile, file.getBytes());

		logger.info("上传文件耗时:" + stopwatch.toString());

		// 读取文件内容
		InputStream inp = new FileInputStream(newFile);
		Workbook wb = new HSSFWorkbook(inp);
		Sheet sheet = wb.getSheetAt(0);

		stopwatch.reset().start();
		logger.info("开始数据验证");
		// 数据验证
		Response validateResult = validator(sheet);
		logger.info("结束数据验证,耗时:{}", stopwatch);

		if (validateResult.isSuccess()) {
			stopwatch.reset().start();

			// 判断第一行是表头还是数据，根据表格A1的内容是否为“姓名”来判断
			int start = 0;
			int end = sheet.getLastRowNum();
			if (sheet.getRow(0).getCell(0).getStringCellValue().equals("姓名")) {
				start = 1;
			}
			int count = 0;
			logger.info("开始导入,第{}行到第{}行", start, end);

			// 导入
			boolean importResult = true; // 导入结果
			for (int i = start; i <= end; i++) {
				count++;
				Row row = sheet.getRow(i);

				// 获取每行数据
				String name = getStringCellValue(row, 0);
				String gender = getStringCellValue(row, 1);
				String category = getStringCellValue(row, 2);
				String education = getStringCellValue(row, 3);
				String duty = getStringCellValue(row, 4);
				String group = getStringCellValue(row, 5);
				String post = getStringCellValue(row, 6);
				String partTime = getStringCellValue(row, 7);
				Date postDate = getDateCellValue(row, 8);
				String qualification = getStringCellValue(row, 9);
				String identityCard = getStringCellValue(row, 10);
				String remark = getStringCellValue(row, 11);

				// 保存人员信息
				Staff staff = new Staff();
				staff.setName(name);
				staff.setGender(gender);
				staff.setCategory(category);
				staff.setEducation(education);
				staff.setDuty(duty);
				staff.setGroupEntity(groupEntityServiceImpl.findUniqueBy("name", group));
				staff.setPost(post);
				staff.setPartTime(partTime);
				staff.setPostDate(postDate);
				staff.setQualification(qualification);
				staff.setIdentityCard(identityCard);
				staff.setRemark(remark);

				importResult &= staffService.save(staff);
			}

			logger.info("结束导入,耗时:{},共导入:{}条记录", stopwatch, count);
			logger.info("导入完成");

			// 根据导入结果响应
			response(response, new Response(importResult));
		} else {
			response(response, validateResult);
		}
	}

	private String generatePath(MultipartFile file) {
		// 根据日期生成文件夹
		String folder = DateFormat.getDateInstance(2, Locale.CHINA).format(new Date(System.currentTimeMillis()));

		// 重新生成文件名，文件名加6位随机数
		String fullFileName = file.getOriginalFilename();

		String fileName, type = "";
		if (fullFileName.lastIndexOf(".") != -1) {
			fileName = fullFileName.substring(0, fullFileName.lastIndexOf("."));
			type = fullFileName.substring(fullFileName.lastIndexOf("."));
		} else {
			fileName = fullFileName;
		}

		String newFullFileName = fileName + "-" + RandomStringUtils.randomNumeric(RANDOM_NUMERIC_COUNT) + type;

		return folder + "/" + newFullFileName;
	}

	private Date getDateCellValue(Row row, int cellNum) {
		if (null != row.getCell(cellNum)) {
			return new Date(row.getCell(cellNum).getDateCellValue().getTime());
		} else {
			return null;
		}
	}

	private String getStringCellValue(Row row, int cellNum) {
		if (null != row.getCell(cellNum)) {
			row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
			return row.getCell(cellNum).getStringCellValue();
		} else {
			return "";
		}
	}

	/**
	 * 判断单元格内容是否为空。
	 * 
	 * @param row
	 * @param cellNum
	 * @param cellName
	 * @param errors
	 */
	private void isCellValueEmpty(Row row, int cellNum, String cellName, Map<String, String> errors) {
		if (null != row.getCell(cellNum)) {
			row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
			if (Strings.isNullOrEmpty(row.getCell(cellNum).getStringCellValue().trim())) {
				errors.put("第" + (row.getRowNum() + 1) + "行" + cellName, "为空");
			}
		} else {
			errors.put("第" + (row.getRowNum() + 1) + "行" + cellName, "为空");
		}
	}

	/**
	 * 判断单元格内容是否为指定数据。
	 * 
	 * @param row
	 * @param cellNum
	 * @param refer
	 * @param errors
	 * @param cellName
	 */
	private void isCellValueExist(Row row, int cellNum, String cellName, List<String> refer, Map<String, String> errors) {
		Cell cell = row.getCell(cellNum);
		if (null != cell) {
			cell.setCellType(Cell.CELL_TYPE_STRING);
			logger.debug("{}：{}", cellName, cell.getStringCellValue());
			if (!refer.contains(cell.getStringCellValue())) {
				errors.put("第" + (row.getRowNum() + 1) + "行" + cellName, cell.getStringCellValue() + "不存在");
			}
		}
	}

	private void response(HttpServletResponse httpServletResponse, Response response) throws IOException {
		httpServletResponse.setContentType("text/html");
		httpServletResponse.getWriter().write(JSON.toJSONString(response));
		httpServletResponse.flushBuffer();
	}

	private Response validator(Sheet sheet) {
		Map<String, String> errors = Maps.newLinkedHashMap();

		// 判断是否有内容
		logger.debug("总行数：{}", sheet.getPhysicalNumberOfRows());
		if (sheet.getPhysicalNumberOfRows() == 0) {
			errors.put("0", "表格内容为空");
			return new Response(errors);
		}

		// 判断第一行是表头还是数据，根据表格A1的内容是否为“姓名”来判断
		logger.debug("{}", sheet.getRow(0).getCell(0).getStringCellValue());
		int start = 0;
		int end = sheet.getLastRowNum();
		if (sheet.getRow(0).getCell(0).getStringCellValue().equals("姓名")) {
			start = 1;
		}
		logger.debug("{},{}", start, end);

		// 获取对比数据
		List<String> duties = Lists.newArrayList(); // 职务职称
		for (Dictionary dictionary : dictionaryService.findBy("typeCode", "system_duty")) {
			duties.add(dictionary.getItemName());
		}
		List<String> groups = Lists.newArrayList(); // 所属机构
		for (GroupEntity groupEntity : groupEntityServiceImpl.getAll()) {
			groups.add(groupEntity.getName());
		}
		List<String> posts = Lists.newArrayList(); // 确定岗位
		List<String> partTimes = Lists.newArrayList(); // 兼职岗位
		for (Dictionary dictionary : dictionaryService.findBy("typeCode", "system_post")) {
			posts.add(dictionary.getItemName());
			partTimes.add(dictionary.getItemName());
		}

		// 开始验证
		for (int i = start; i <= end; i++) {
			logger.debug("验证第{}行", i + 1);
			Row row = sheet.getRow(i);

			// 验证1：姓名、性别、所属机构、身份证号不能为空
			isCellValueEmpty(row, 0, "姓名", errors);
			isCellValueEmpty(row, 1, "性别", errors);
			isCellValueEmpty(row, 5, "所属机构", errors);
			isCellValueEmpty(row, 10, "身份证号", errors);

			// 验证2：身份证号必须为15位或18位
			Cell identityCardCell = row.getCell(10);
			if (identityCardCell != null) {
				int identityCardLength = identityCardCell.getStringCellValue().length();
				if (identityCardLength != 15 && identityCardLength != 18) {
					errors.put("第" + (i + 1) + "行", "身份证号格式不正确");
				}
			}

			// 验证3：用工类别为：正、协、临
			List<String> categories = Lists.newArrayList("正", "协", "临");
			isCellValueExist(row, 2, "用工类别", categories, errors);

			// 验证4：文化程度为：高中、中专、大专、本科、硕士、博士
			List<String> educations = Lists.newArrayList("高中", "中专", "大专", "本科", "硕士", "博士");
			isCellValueExist(row, 3, "文化程度", educations, errors);

			// 验证5：职务职称、所属机构、确定岗位、兼职岗位必须系统中已存在
			isCellValueExist(row, 4, "职务职称", duties, errors);
			isCellValueExist(row, 5, "所属机构", groups, errors);
			isCellValueExist(row, 6, "确定岗位", posts, errors);
			isCellValueExist(row, 7, "兼职岗位", partTimes, errors);

			// 验证6：定岗日期格式为：yyyy/mm/d
			Cell postDateCell = row.getCell(8);
			if (null != postDateCell) {
				try {
					postDateCell.getDateCellValue();
				} catch (IllegalStateException e) {
					errors.put("第" + (i + 1) + "行", "定岗日期格式不正确");
				}
			}
		}

		logger.debug("{},{}", errors, errors.size());
		return new Response(errors);
	}
}
