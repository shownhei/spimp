package cn.ccrise.spimp.system.service;

import java.io.File;
import java.io.IOException;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.springframework.stereotype.Service;

import cn.ccrise.ikjp.core.util.PropertiesUtils;

/**
 * 基于 libreoffice的转化工具服务
 * 
 * @author lixia
 * 
 */
@Service
public class LibreOfficeConverterService {
	private String libreOfficePath;

	public LibreOfficeConverterService() {
		this.setLibreOfficePath(PropertiesUtils.getString("libreOfficePath"));
	}

	public void service(String sourceFile, String destFile) throws IOException {
		OfficeManager officeManager = null;

		try {
			// 1) Start LibreOffice in headless mode.
			officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(new File(this.getLibreOfficePath()))
					.buildOfficeManager();
			officeManager.start();

			// 2) Create JODConverter converter
			OfficeDocumentConverter converter = new OfficeDocumentConverter(
					officeManager);

			// 3) Convert to ...
			converter.convert(new File(sourceFile), new File(destFile));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			// 4) Stop LibreOffice in headless mode.
			if (officeManager != null) {
				officeManager.stop();
			}
		}
	}

	public String getLibreOfficePath() {
		return libreOfficePath;
	}

	public void setLibreOfficePath(String libreOfficePath) {
		this.libreOfficePath = libreOfficePath;
	}

}
