/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.web;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cn.ccrise.ikjp.core.security.service.impl.LogEntityServiceImpl;
import cn.ccrise.ikjp.core.util.Response;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;

/**
 * 文件上传Controller。
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class UploadController {
	public static final String DEFAULT_PATH = "/WEB-INF/uploads/public/";
	private static final int RANDOM_NUMERIC_COUNT = 6;
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private String defaultUploadPath = DEFAULT_PATH;

	@Autowired
	private LogEntityServiceImpl logEntityServiceImpl;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(@RequestParam MultipartFile file, HttpSession httpSession, HttpServletResponse response,
			final String uploadPath) throws IOException {
		// 生成文件路径
		String filePath = generatePath(file);

		// 自定义上传目录
		if (!Strings.isNullOrEmpty(uploadPath)) {
			if (uploadPath.charAt(uploadPath.length() - 1) != '/') {
				defaultUploadPath = uploadPath + '/';
			} else {
				defaultUploadPath = uploadPath;
			}
		} else {
			defaultUploadPath = DEFAULT_PATH;
		}

		// 获取上传目录的真实路径
		String uploadRealPath = httpSession.getServletContext().getRealPath(defaultUploadPath);

		// 写入文件
		final File newFile = new File(uploadRealPath + "/" + filePath);
		FileUtils.writeByteArrayToFile(newFile, file.getBytes());

		// 记录日志
		logEntityServiceImpl.info("上传文件：" + file.getOriginalFilename() + "，目录：" + defaultUploadPath + filePath);

		// 设置响应
		response.setContentType("text/html");
		response.getWriter()
				.write(JSON.toJSONString(new Response(new String(
						(defaultUploadPath.replaceFirst("/WEB-INF", "") + filePath)))));
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
				+ RandomStringUtils.randomNumeric(RANDOM_NUMERIC_COUNT) + type.toLowerCase();

		return folder + "/" + newFullFileName;
	}
}
