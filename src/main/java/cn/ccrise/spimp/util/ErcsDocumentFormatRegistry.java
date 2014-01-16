package cn.ccrise.spimp.util;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;

public class ErcsDocumentFormatRegistry extends DefaultDocumentFormatRegistry {
	public ErcsDocumentFormatRegistry() {
		super();
		/**
		 * 解决docx 不能转化的问题
		 */
		final DocumentFormat docx = new DocumentFormat("Microsoft Word", DocumentFamily.TEXT, "application/msword",
				"docx");
		docx.setExportFilter(DocumentFamily.TEXT, "MS Word 97");
		addDocumentFormat(docx);
		/**
		 * 解决xlsx 不能转化的问题
		 */
		final DocumentFormat xlsx = new DocumentFormat("Microsoft Excel", DocumentFamily.SPREADSHEET,
				"application/vnd.ms-excel", "xlsx");
		xlsx.setExportFilter(DocumentFamily.SPREADSHEET, "MS Excel 97");
		addDocumentFormat(xlsx);
	}

}
