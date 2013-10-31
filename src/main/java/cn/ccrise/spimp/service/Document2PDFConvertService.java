package cn.ccrise.spimp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.ccrise.spimp.util.ErcsDocumentFormatRegistry;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

@Service
public class Document2PDFConvertService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private OpenOfficeConnection connection;
	private Process process;

	private String OpenOffice_HOME = "E:\\Program Files (x86)\\OpenOffice 4";// 这里是OpenOffice的安装目录,

	/**
	 * 关闭服务
	 */
	public void closeService() {
		// close the connection
		getConnection().disconnect();
		// 关闭OpenOffice服务的进程
		getProcess().destroy();
	}

	public void convert(String sourceFile, String destFile) {
		File inputFile = new File(sourceFile);
		// 如果目标路径不存在, 则新建该路径
		File outputFile = new File(destFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		// convert
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection, new ErcsDocumentFormatRegistry());
		converter.convert(inputFile, outputFile);

	}

	public OpenOfficeConnection getConnection() {
		return connection;
	}

	public Process getProcess() {
		return process;
	}

	public void service(String sourceFile, String destFile) {
		startService();
		convert(sourceFile, destFile);
		closeService();
	}

	public void setConnection(OpenOfficeConnection connection) {
		this.connection = connection;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	/**
	 * 启动服务
	 */
	public void startService() {
		// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
		if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
			OpenOffice_HOME += "\\";
		}
		// 启动OpenOffice的服务
		String command = OpenOffice_HOME
				+ "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
		try {
			process = Runtime.getRuntime().exec(command);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String temp = null;
			while ((temp = bufferedReader.readLine()) != null) {
				logger.debug(temp);
			}
			connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
