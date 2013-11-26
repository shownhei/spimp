/*
 * Copyright (C) CCRISE.
 */
package cn.ccrise.spimp.repackage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

import cn.ccrise.spimp.system.web.LoginController;
import cn.ccrise.spimp.util.AES;

import com.google.common.collect.Lists;

/**
 * 重新打包
 * 
 * @author Xiong Shuhong(shelltea@gmail.com)
 */
public class Repackage {
	public static void main(String[] args) {
		WarUtils.unzip("target/spimp-0.1.0-SNAPSHOT.war", "target/ROOT");

		String version = "0.1.0." + new SimpleDateFormat("yyyyMMddHHmm").format(new Date());

		webxml(version);
		applicationContextmvcxml();
		applicationserverproperties("root", "admin", "jdbc:mysql://localhost:3306/spimp", "2014-12-31",
				"D:\\\\Program Files\\\\OpenOffice 4\\\\");
		log4jxml("WARN");
		wroproperties();
		headjsp(version);

		WarUtils.zip("target/ROOT.war", "target/ROOT");
	}

	private static void applicationContextmvcxml() {
		String fileName = "target/ROOT/WEB-INF/classes/spring/applicationContext-mvc.xml";
		List<String> lines = readLines(fileName);

		lines.set(28 - 1, "		<property name=\"cacheSeconds\" value=\"86400\" />");

		writeLinesToFile(fileName, lines);
	}

	private static void applicationserverproperties(String username, String password, String url, String day,
			String openOfficePath) {
		String fileName = "target/ROOT/WEB-INF/classes/application.server.properties";
		List<String> lines = readLines(fileName);

		lines.set(4 - 1, "jdbc.username=" + username);
		lines.set(5 - 1, "jdbc.password=" + password);
		lines.set(9 - 1, "jdbc.url=" + url);

		String license = AES.encodeAes128(LoginController.KEY, day);
		lines.set(38 - 1, "app.license=" + license);

		lines.set(40 - 1, "document_convert_openoffice=" + openOfficePath);

		writeLinesToFile(fileName, lines);
	}

	private static void headjsp(String version) {
		String fileName = "target/ROOT/WEB-INF/views/common/head.jsp";
		List<String> lines = readLines(fileName);

		lines.set(34 - 1, "	var isDevelopment = false;");

		writeLinesToFile(fileName, lines);
	}

	private static void log4jxml(String level) {
		String fileName = "target/ROOT/WEB-INF/classes/log4j.xml";
		List<String> lines = readLines(fileName);

		lines.set(43 - 1, "		<level value=\"" + level + "\" />");
		lines.set(46 - 1, "		<level value=\"" + level + "\" />");

		writeLinesToFile(fileName, lines);
	}

	private static List<String> readLines(String fileName) {
		File file = FileUtils.getFile(fileName);
		List<String> lines = Lists.newArrayList();
		try {
			BufferedReader in = new BufferedReader(new FileReader(file));

			String line = "";
			while ((line = in.readLine()) != null) {
				lines.add(line);
			}
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("文件未找到");
		} catch (IOException e) {
			System.err.println("文件写入或读取发生错误");
		}
		return lines;
	}

	private static void webxml(String version) {
		String fileName = "target/ROOT/WEB-INF/web.xml";
		List<String> lines = readLines(fileName);

		lines.set(4 - 1, "	<display-name>SPIMP " + version + "</display-name>");
		lines.set(11 - 1, "	<filter>");
		lines.set(19 - 1, "	</filter-mapping>");

		writeLinesToFile(fileName, lines);
	}

	private static void writeLinesToFile(String fileName, List<String> lines) {
		File file = FileUtils.getFile(fileName);
		StringBuilder stringBuilder = new StringBuilder();
		for (String line : lines) {
			stringBuilder.append(line);
			stringBuilder.append(System.getProperty("line.separator"));
		}

		try {
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.write(stringBuilder.toString());
			printWriter.flush();
			printWriter.close();
		} catch (FileNotFoundException e) {
			System.err.println("文件写入时发生错误");
		}
	}

	private static void wroproperties() {
		String fileName = "target/ROOT/WEB-INF/wro.properties";
		List<String> lines = readLines(fileName);

		lines.set(3, lines.get(3).replaceFirst("#", ""));
		lines.set(4, lines.get(4).replaceFirst("#", ""));
		lines.set(5, lines.get(5).replaceFirst("#", ""));
		lines.set(6, lines.get(6).replaceFirst("#", ""));

		lines.set(9, "#" + lines.get(9));
		lines.set(10, "#" + lines.get(10));
		lines.set(11, "#" + lines.get(11));
		lines.set(12, "#" + lines.get(12));

		writeLinesToFile(fileName, lines);
	}
}
