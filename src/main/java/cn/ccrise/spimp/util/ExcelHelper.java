package cn.ccrise.spimp.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.poi.ss.usermodel.IndexedColors;

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
	 * @param title
	 *         Excel文件title
	 * @param headers
	 * 		   表头，eg.: String[] headers = {"日期","铁路运输车数","铁路运输吨数","铁路运输备注"};
	 * @param dataset
	 * 		   导出的数据集合，为Collection<T>的派生类，如常用的List<T>
	 * @param datePattern
	 *         表格中日期显示格式，eg.: "yyyy-MM-dd"
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HSSFWorkbook genExcel(String title, String[] headers,
			Collection<T> dataset, String datePattern) {
		
		if(StringUtils.isBlank(datePattern)){
			datePattern = "yyyy-MM-dd";
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		sheet.setDefaultColumnWidth(15);
		
		// 标题样式
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont headerFont = workbook.createFont();
		headerFont.setColor(HSSFColor.WHITE.index);
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerStyle.setFont(headerFont);

		// 数据样式
		HSSFCellStyle dataStyle = workbook.createCellStyle();
		dataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont dataFont = workbook.createFont();
		dataFont.setColor(HSSFColor.GREY_80_PERCENT.index);
		dataFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		dataStyle.setFont(dataFont);
		
		// 数字样式
		HSSFCellStyle numberStyle = workbook.createCellStyle();
		numberStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		numberStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		numberStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		numberStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		numberStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		numberStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		numberStyle.setFont(dataFont);

		// 定义注释
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		/*HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		comment.setString(new HSSFRichTextString("注释内容"));
		comment.setAuthor("煤炭科学研究总院");
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
			T t = (T) it.next();

			Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataStyle);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class clazz = t.getClass();
					Method getMethod = clazz.getMethod(getMethodName,new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});

					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						row.setHeightInPoints(60);
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						textValue = value.toString();
					}

					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							cell.setCellStyle(numberStyle);
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							cell.setCellValue(textValue);
						}
					}
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}

		}

		return workbook;
	}
}
