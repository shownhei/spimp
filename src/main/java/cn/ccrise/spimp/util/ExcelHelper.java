package cn.ccrise.spimp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel导出通用工具
 * 
 * <p>
 * 
 * @author David(david.kosoon@gmail.com)
 */
public class ExcelHelper<T> {
	/**
	 * 普通表格导出
	 * 
	 * @param title
	 *            Excel文件title
	 * @param headers
	 *            表头，eg.: String[] headers = {"日期","铁路运输车数","铁路运输吨数","铁路运输备注"};
	 * @param dataset
	 *            导出的数据集合，为Collection<T>的派生类，如常用的List<T>
	 * @param datePattern
	 *            表格中日期显示格式，eg.: "yyyy-MM-dd"
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HSSFWorkbook genExcel(String title, String[] headers, Collection<T> dataset, String datePattern) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(15);

		// 标题样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		HSSFFont headerFont = workbook.createFont();
		headerFont.setColor(HSSFColor.WHITE.index);
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);

		// 数据样式
		HSSFCellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		dataStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
		dataStyle.setBorderRight(CellStyle.BORDER_THIN);
		dataStyle.setBorderTop(CellStyle.BORDER_THIN);
		dataStyle.setAlignment(CellStyle.ALIGN_LEFT);
		dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		HSSFFont dataFont = workbook.createFont();
		dataFont.setColor(HSSFColor.GREY_80_PERCENT.index);
		dataFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		dataStyle.setFont(dataFont);

		// 数字样式
		HSSFCellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		numberStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		numberStyle.setBorderBottom(CellStyle.BORDER_THIN);
		numberStyle.setBorderLeft(CellStyle.BORDER_THIN);
		numberStyle.setBorderRight(CellStyle.BORDER_THIN);
		numberStyle.setBorderTop(CellStyle.BORDER_THIN);
		numberStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		numberStyle.setFont(dataFont);

		// 定义注释
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		/*
		 * HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
		 * 0, 0, 0, (short) 4, 2, (short) 6, 5)); comment.setString(new
		 * HSSFRichTextString("注释内容")); comment.setAuthor("煤炭科学研究总院");
		 */

		// 生成表格标题
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headerStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}

		// 生成数据
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = it.next();

			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataStyle);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				try {
					Class clazz = t.getClass();
					Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String textValue = null;
					if (value instanceof Timestamp) {
						textValue = String.valueOf(value);
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(StringUtils.isBlank(datePattern) ? "yyyy-MM-dd"
								: datePattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						row.setHeightInPoints(60);
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short) 6, index, (short) 6,
								index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(bsValue, Workbook.PICTURE_TYPE_JPEG));
					} else {
						if (value == null) {
							textValue = "";
						} else {
							textValue = value.toString();
						}
					}

					if (textValue != null) {
						Pattern p = Pattern.compile("^[+\\-]?\\d+(.\\d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellStyle(numberStyle);
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							cell.setCellValue(textValue);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}

		return workbook;
	}

	/**
	 * 根据模板生成excel
	 * 
	 * @param response
	 * @param templatePath
	 * @param root
	 */
	public void genExcelWithTel(HttpSession httpSession, HttpServletResponse response, String templatePath,
			HashMap<String, Object> root, String downloadFileName, String sheetNames[]) {
		String templateFoldPath = httpSession.getServletContext().getRealPath("/");
		XLSTransformer transformer = new XLSTransformer();
		InputStream ins = null;
		OutputStream os = null;
		try {
			ins = new FileInputStream(new File(templateFoldPath + "/WEB-INF/resources/template/" + templatePath));
			Workbook book = transformer.transformXLS(ins, root);
			response.setContentType("application/force-download");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(downloadFileName, "UTF-8") + ".xls");
			os = response.getOutputStream();
			if (sheetNames != null && sheetNames.length > 0) {

				int len = sheetNames.length;
				for (int i = 0; i < len; i++) {
					book.setSheetName(i, sheetNames[i]);
				}
			}
			book.write(os);
			os.flush();
			os.close();
			ins.close();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据模板生成excel
	 * 
	 * @param response
	 * @param templatePath
	 * @param root
	 */
	public void genExcelWithTel(HttpSession httpSession, HttpServletResponse response, String templatePath,
			HashMap<String, Object> root, String downloadFileName, String sheetNames[], ExcelCallBackInteface callback) {
		String templateFoldPath = httpSession.getServletContext().getRealPath("/");
		XLSTransformer transformer = new XLSTransformer();
		InputStream ins = null;
		OutputStream os = null;
		try {
			ins = new FileInputStream(new File(templateFoldPath + "/WEB-INF/resources/template/" + templatePath));
			Workbook book = transformer.transformXLS(ins, root);
			response.setContentType("application/force-download");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + URLEncoder.encode(downloadFileName, "UTF-8") + ".xls");
			os = response.getOutputStream();
			if (sheetNames != null && sheetNames.length > 0) {

				int len = sheetNames.length;
				for (int i = 0; i < len; i++) {
					book.setSheetName(i, sheetNames[i]);
				}
			}
			if (callback != null) {
				callback.process(book, root);
			}
			book.write(os);
			os.flush();
			os.close();
			ins.close();
		} catch (ParsePropertyException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
