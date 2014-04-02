/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.system.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ccrise.ikjp.core.util.Response;
import cn.ccrise.spimp.util.UpdateFileVisitor;

/**
 * 前端程序更新服务。
 * <p>
 * 更新文件格式需满足：prefix-version.suffix
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
@Controller
public class UpdateController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String ROOT_UPDATE_PATH = "/WEB-INF/resources/update/";
	public static final String RESOURCES_PATH = "/static-resources/update/";

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public Response update(String prefix, String suffix, HttpSession httpSession, HttpServletRequest httpServletRequest) {
		if (null == prefix || null == suffix) {
			return new Response(false, "IllegalArgumentException");
		}

		prefix = prefix.trim();
		suffix = suffix.trim();

		Path searchPath = Paths.get(httpSession.getServletContext().getRealPath(ROOT_UPDATE_PATH));

		logger.debug("search:{}", prefix + "-*." + suffix);

		UpdateFileVisitor visitor = new UpdateFileVisitor(prefix + "-*." + suffix);
		try {
			Files.walkFileTree(searchPath, visitor);

			long latestVersion = getLatestVersion(visitor.getList());
			if (latestVersion != 0) {
				StringBuilder stringBuilder = new StringBuilder("http://");
				stringBuilder.append(httpServletRequest.getLocalAddr());
				stringBuilder.append(":");
				stringBuilder.append(httpServletRequest.getLocalPort());
				stringBuilder.append(RESOURCES_PATH);
				stringBuilder.append(prefix);
				stringBuilder.append("-");
				stringBuilder.append(latestVersion);
				stringBuilder.append(".");
				stringBuilder.append(suffix);

				return new Response(stringBuilder.toString());
			} else {
				return new Response(false, "NoSuchFileException");
			}
		} catch (IOException e) {
			return new Response(false, "IOException");
		}
	}

	/**
	 * 获取最新版本号。
	 * 
	 * @param paths
	 * @return
	 */
	private long getLatestVersion(ArrayList<Path> paths) {
		long latestVersion = 0;
		for (Path file : paths) {
			String fileName = file.toFile().getName();

			if (fileName.matches(".*\\-{1}\\d+\\.{1}\\w+")) {
				long currentVersion = Long.parseLong(fileName.split("\\-")[1].split("\\.")[0]);

				if (currentVersion > latestVersion) {
					latestVersion = currentVersion;
				}
			}
		}
		logger.debug("version:{}", latestVersion);

		return latestVersion;
	}
}
