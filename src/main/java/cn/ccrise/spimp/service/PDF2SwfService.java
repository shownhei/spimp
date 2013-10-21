package cn.ccrise.spimp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PDF2SwfService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * SWFTools的环境安装路径
	 */
	public String SWFTOOLS_PATH = "F:\\Program Files (x86)\\SWFTools\\";

	public int convertPDF2SWF(String sourcePath, String destPath, String fileName) throws IOException {

		File dest = new File(destPath);

		if (!dest.exists()) {

			dest.mkdirs();

		}

		// 源文件不存在则返回

		File source = new File(sourcePath);

		if (!source.exists()) {

			return -1;

		}

		String[] envp = new String[1];
		envp[0] = "PATH=" + SWFTOOLS_PATH;
		String command = "cmd /c \"" + SWFTOOLS_PATH + "pdf2swf\" -z -s flashversion=9 " + sourcePath + " -o "
				+ destPath + fileName;
		Process pro = Runtime.getRuntime().exec(command, envp);
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
		String temp = null;
		while ((temp = bufferedReader.readLine()) != null) {
			System.out.println(temp);
		}
		try {
			pro.waitFor();

		} catch (InterruptedException e) {

			e.printStackTrace();

		}
		return pro.exitValue();
	}
}